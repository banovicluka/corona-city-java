package CoronaCity.Objekti;

import CoronaCity.Vozila.AmbulantnoVozilo;
import CoronaCity.Kontroleri.pocetniProzorController;
import CoronaCity.Main;
import CoronaCity.Mapa.Mapa;
import CoronaCity.Stanovnici.Stanovnik;
import javafx.application.Platform;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Bolnica extends Thread implements Serializable{

    public static int brojInstanci;
    private int identifikator;
    public int pozicijaX;
    public int pozicijaY;
    public int kapacitetBolnice;
    private int brojZarazenih=0;
    private boolean popunjena;
    private boolean bilaPopunjena;
    private boolean novaBolnicaDodana=false;

    List<Stanovnik> listaPacijenata = new ArrayList<>();
    List<Stanovnik> listaZaUkloniti = new ArrayList<>();

    public Bolnica(int pozicijaX,int pozicijaY,int kapacitetBolnice){
        brojInstanci++;
        this.identifikator=brojInstanci;
        this.pozicijaX=pozicijaX;
        this.pozicijaY=pozicijaY;
        this.kapacitetBolnice=kapacitetBolnice;
    }

    public int getBrojZarazenih() {
        return brojZarazenih;
    }

    public void setBrojZarazenih(int brojZarazenih) {
        this.brojZarazenih = brojZarazenih;
    }

    public int getIdentifikator() {
        return identifikator;
    }

    public void uvecajBrojZarazenih(){
        brojZarazenih++;
    }

    public void smjestiPacijentaUBolnicu(Stanovnik stanovnik){
        stanovnik.listaTriPoslednjeTemperature.clear();
        synchronized (listaPacijenata){
            listaPacijenata.add(stanovnik);
        }
        synchronized (Mapa.monitor) {
            Main.mapa.brojZarazenih++;
            AmbulantnoVozilo vozilo = null;
            for(AmbulantnoVozilo ambulantnoVozilo:Main.mapa.listaAmbulantnihVozila){
                if(!ambulantnoVozilo.isZauzeto()){
                    vozilo=ambulantnoVozilo;
                    break;
                }
            }
            if(vozilo != null){
                Main.mapa.brojSlobodnihVozila--;
                Main.mapa.mapa[stanovnik.pozicijaX][stanovnik.pozicijaY].lista.remove(stanovnik);
                vozilo.pozicijaX=stanovnik.pozicijaX;
                vozilo.pozicijaY=stanovnik.pozicijaY;
                Main.mapa.mapa[stanovnik.pozicijaX][stanovnik.pozicijaY].lista.add(vozilo);
                vozilo.setZauzeto(true);
                stanovnik.pozicijaX=pozicijaX;
                stanovnik.pozicijaY=pozicijaY;
                Main.mapa.mapa[pozicijaX][pozicijaY].lista.add(stanovnik);
                if(brojZarazenih>=kapacitetBolnice && !bilaPopunjena){
                    popunjena=true;
                }
                if(popunjena && !bilaPopunjena){
                    Platform.runLater(() -> {
                        pocetniProzorController.glavniProzorKontroler.terminal.appendText(this + " je popunila svoje kapacitete, potrebno je dodati novu ambulantu.\n");
                            }
                    );
                    Main.mapa.brojAmbulantiZaDodati++;
                    bilaPopunjena=true;
                }
                stanovnik.uBolnici=true;
                Platform.runLater(() -> {
                            pocetniProzorController.glavniProzorKontroler.terminal.appendText(
                                    stanovnik + " je smjesten u bolnicu.\n"); }
                );
                try{
                    synchronized (Mapa.fileMonitor){
                        String path = new File("src/CoronaCity/FileWatcher").getAbsolutePath();
                        PrintWriter statistika = new PrintWriter(new FileOutputStream(path + File.separator + "statistika.txt",true));
                        statistika.println("+ " + stanovnik);
                        statistika.close();
                    }
                }
                catch (IOException ex){
                    Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
                }
            }
            else{
                Platform.runLater(() -> {
                    pocetniProzorController.glavniProzorKontroler.terminal.appendText(
                            "Sva ambulantna vozila su trenutno popunjena.\n"); }
                );
                //System.out.println("Sva ambulantna vozila su trenutno popunjena.");
            }

        }

    }

    public void otpustiPacijenta(Stanovnik izlijeceni){
        synchronized (listaPacijenata) {
            listaPacijenata.remove(izlijeceni);
        }
        synchronized (Mapa.monitor){
            Main.mapa.brojOporavljenih++;
            Main.mapa.brojZarazenih--;
            Main.mapa.mapa[pozicijaX][pozicijaY].lista.remove(izlijeceni);
            if(popunjena){
                popunjena=false;
                //Main.mapa.brojAmbulantiZaDodati--;
            }
            for(Kuca kuca: Main.mapa.listaKuca) {
                if (kuca.getJedinstveniIdentifikator() == izlijeceni.getIdentifikatorKuce()) {
                    for (Stanovnik stanovnik : Main.mapa.listaStanovnika) {
                        if (stanovnik.getIdentifikatorKuce() == kuca.getJedinstveniIdentifikator() && !stanovnik.uBolnici /*&& !stanovnik.isZaustavljen()*/ && stanovnik.isIzolovan()) {
                            stanovnik.setIzolovan(false);
                            stanovnik.setZaustavljen(false);
                            Main.mapa.mapa[stanovnik.pozicijaX][stanovnik.pozicijaY].lista.remove(stanovnik);
                            try{
                                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File("izolovani.ser")));
                                out.writeObject(stanovnik);
                            }
                            catch (IOException ex){
                                Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
                            }
                            Stanovnik izolovani=null;
                            try{
                                ObjectInputStream in = new ObjectInputStream(new FileInputStream("izolovani.ser"));
                                izolovani= (Stanovnik) in.readObject();
                            }
                            catch (IOException ex){
                                Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
                            }
                            catch (ClassNotFoundException ex){
                                Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
                            }
                            Main.mapa.mapa[izolovani.pozicijaX][izolovani.pozicijaY].lista.add(izolovani);
                            Main.mapa.listaStanovnika.set(Main.mapa.listaStanovnika.indexOf(stanovnik),izolovani);
                            izolovani.start();
                        }
                    }

                    izlijeceni.uKuci=true;
                    Main.mapa.listaKuca.get(izlijeceni.getIdentifikatorKuce()-1).setTrenutniBrojUkucana(
                            Main.mapa.listaKuca.get(izlijeceni.getIdentifikatorKuce()-1).getTrenutniBrojUkucana()+1);
                    Main.mapa.mapa[kuca.pozicijaX][kuca.pozicijaY].lista.add(izlijeceni);
                    izlijeceni.pozicijaX=kuca.pozicijaX;
                    izlijeceni.pozicijaY=kuca.pozicijaY;
                    izlijeceni.vratiRadijusKretanja();
                    izlijeceni.uBolnici=false;
                    izlijeceni.setZaustavljen(false);
                    izlijeceni.setIzolovan(false);
                    izlijeceni.setBrojac(0);
                    izlijeceni.listaTriPoslednjeTemperature.clear();
                    Main.mapa.mapa[kuca.pozicijaX][kuca.pozicijaY].lista.remove(izlijeceni);
                    Main.mapa.listaStanovnika.remove(izlijeceni);
                    try {
                        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File("izlijeceni.ser")));
                        out.writeObject(izlijeceni);
                    }
                    catch (IOException ex){
                        Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
                    }
                    Stanovnik oporavljeni=null;
                    try{
                        ObjectInputStream in = new ObjectInputStream(new FileInputStream("izlijeceni.ser"));
                        oporavljeni= (Stanovnik) in.readObject();
                    }
                    catch (IOException ex){
                        Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
                    }
                    catch (ClassNotFoundException ex){
                        Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
                    }
                    Main.mapa.mapa[kuca.pozicijaX][kuca.pozicijaY].lista.add(oporavljeni);
                    Main.mapa.listaStanovnika.add(oporavljeni);
                    try{
                        synchronized (Mapa.fileMonitor){
                            String path = new File("src/CoronaCity/FileWatcher").getAbsolutePath();
                            PrintWriter statistika = new PrintWriter(new FileOutputStream(path + File.separator + "statistika.txt",true));
                            statistika.println("- " + oporavljeni);
                            statistika.close();
                        }
                    }
                    catch (IOException ex){
                        Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
                    }
                    String string = oporavljeni.toString();
                    Platform.runLater(() -> {
                        pocetniProzorController.glavniProzorKontroler.terminal.appendText(
                                 string + " je izlijecen i vracen kuci.\n"); }
                    );
                    oporavljeni.start();
                }
            }

        }
    }

    @Override
    public String toString() {
        return "bolnica " + " identifikator:" + identifikator + "[" + pozicijaX + "," + pozicijaY + "]" + "Broj pacijenata:" + brojZarazenih + " Kapacitet:" + kapacitetBolnice;
    }

    @Override
    public void run() {
        while(!Main.simulacijaZavrsena){
                synchronized (Mapa.monitor){
                    synchronized (listaPacijenata){
                    for(Stanovnik pacijent: listaPacijenata) {
                        if (pacijent.getPoslednjeTriTemperatureProsjek() < 37.0) {
                            //System.out.println(pacijent + " se izlijecio.");
                            brojZarazenih--;
                            listaZaUkloniti.add(pacijent);
                        }
                    }
                    for(Stanovnik pacijent: listaZaUkloniti){
                        otpustiPacijenta(pacijent);
                    }
                    listaZaUkloniti.clear();
                    }
                }
        }
    }
}
