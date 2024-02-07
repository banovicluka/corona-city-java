package CoronaCity.Stanovnici;

import CoronaCity.*;
import CoronaCity.Kontroleri.pocetniProzorController;
import CoronaCity.Mapa.Mapa;
import CoronaCity.Objekti.Bolnica;
import CoronaCity.Objekti.KontrolniPunkt;
import CoronaCity.Objekti.Kuca;
import CoronaCity.SistemZaNadzor.Alarm;
import javafx.application.Platform;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Stanovnik extends Thread implements Serializable {

    private int jedinstveniIdentifikator;
    private String ime;
    private String prezime;
    private int godinaRodjenja;
    private String pol;
    private int identifikatorKuce;
    private double tjelesnaTemperatura;
    public static int brojInstanci;
    public int pozicijaX;
    public int pozicijaY;
    private int radijusLijevo;
    private int radijusDesno;
    private int radijusGore;
    private int radijusDole;
    private int trenutnoMoguceLijevo;
    private int trenutnoMoguceDesno;
    private int trenutnoMoguceGore;
    private int trenutnoMoguceDole;
    public boolean uKuci;
    public boolean uBolnici;
    private boolean zaustavljen;
    private boolean izolovan;
    private int tajmerZaTemperaturu = 0;
    int brojac=0;


    public List<Double> listaTriPoslednjeTemperature = new ArrayList<>();


    public Stanovnik(String ime, String prezime, int godinaRodjenja, String pol, int idKuce) {
        brojInstanci++;
        this.jedinstveniIdentifikator = brojInstanci;
        this.ime = ime;
        this.prezime = prezime;
        this.godinaRodjenja = godinaRodjenja;
        this.pol = pol;
        this.tjelesnaTemperatura = (double) Math.round((Mapa.rand.nextDouble() * 2 + 35) * 100) / 100;
        this.identifikatorKuce = idKuce;
        this.uKuci = true;
    }

    public Stanovnik() {

    }

    public int getJedinstveniIdentifikator() {
        return jedinstveniIdentifikator;
    }

    public void setJedinstveniIdentifikator(int jedinstveniIdentifikator) {
        this.jedinstveniIdentifikator = jedinstveniIdentifikator;
    }

    public abstract void odrediRadijuse(int dimenzijaMape, List<Kuca> lista);

    public void setRadijusDesno(int radijusDesno) {
        this.radijusDesno = radijusDesno;
    }

    public void setRadijusDole(int radijusDole) {
        this.radijusDole = radijusDole;
    }

    public void setRadijusGore(int radijusGore) {
        this.radijusGore = radijusGore;
    }

    public void setRadijusLijevo(int radijusLijevo) {
        this.radijusLijevo = radijusLijevo;
    }

    public int getRadijusDesno() {
        return radijusDesno;
    }

    public int getRadijusDole() {
        return radijusDole;
    }

    public int getRadijusGore() {
        return radijusGore;
    }

    public int getRadijusLijevo() {
        return radijusLijevo;
    }

    public int getIdentifikatorKuce() {
        return identifikatorKuce;
    }

    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public int getGodinaRodjenja() {
        return godinaRodjenja;
    }

    public String getPol() {
        return pol;
    }

    public void setIzolovan(boolean izolovan) {
        this.izolovan = izolovan;
    }

    public boolean isIzolovan() {
        return izolovan;
    }

    public boolean isZaustavljen() {
        return zaustavljen;
    }

    public void setZaustavljen(boolean zaustavljen) {
        this.zaustavljen = zaustavljen;
    }

    public void setuKuci(boolean uKuci) {
        this.uKuci = uKuci;
    }

    public void setBrojac(int brojac) {
        this.brojac = brojac;
    }

    public double getTjelesnaTemperatura() {
        return tjelesnaTemperatura;
    }

    public abstract boolean provjeriRastojanje(int x, int y);

    public abstract boolean provjeriStarosnuDob();

    public void promjeniTemperaturu() {
        tjelesnaTemperatura = (double) Math.round((Mapa.rand.nextDouble() * 5 + 35) * 100) / 100;
    }

    public void dodajUPoslednjeTriTemperature(double temperatura) {
        if (listaTriPoslednjeTemperature.size() < 3) {
            listaTriPoslednjeTemperature.add(temperatura);
        } else {
            listaTriPoslednjeTemperature.set(brojac, temperatura);
            brojac++;
            if (brojac == 3) brojac = 0;
        }
    }

    public double getPoslednjeTriTemperatureProsjek(){
        Double sumaTemperatura=0.0;
        if(listaTriPoslednjeTemperature.size()<3) return 50;
        for(Double temperatura: listaTriPoslednjeTemperature){
            sumaTemperatura+=temperatura;
        }
        return sumaTemperatura/listaTriPoslednjeTemperature.size();
    }

    public boolean graniciLi(KontrolniPunkt objekat) {
        if (Math.abs(objekat.pozicijaX - pozicijaX) == 1 && Math.abs(objekat.pozicijaY - pozicijaY) == 0) {
            return true;
        } else if (Math.abs(objekat.pozicijaX - pozicijaX) == 0 && Math.abs(objekat.pozicijaY - pozicijaY) == 1) {
            return true;
        } else return false;
    }

    public void vratiRadijusKretanja(){
        trenutnoMoguceDesno=radijusDesno;
        trenutnoMoguceLijevo=radijusLijevo;
        trenutnoMoguceGore=radijusGore;
        trenutnoMoguceDole=radijusDole;
    }

    public void izolujUkucane(){
        for(Stanovnik stanovnik: Main.mapa.listaStanovnika){
            if(stanovnik.jedinstveniIdentifikator!=this.jedinstveniIdentifikator && stanovnik.getIdentifikatorKuce()==this.identifikatorKuce /*&& !stanovnik.isZaustavljen()*/ && !stanovnik.isIzolovan()){
                stanovnik.setIzolovan(true);
            }
        }
    }

    @Override
    public String toString() {
        return ime + " " + prezime + " ,godiste " + godinaRodjenja + ", rod " + pol + ", koordinate " + "[" + pozicijaX +
                "," + pozicijaY + "]";
    }

    public void idiDole() {
        if (trenutnoMoguceDole > 0 && provjeriRastojanje(pozicijaX + 1, pozicijaY)) {
            boolean ideKuci = false;
            List<Object> listaObjekata = Main.mapa.mapa[pozicijaX + 1][pozicijaY].lista;
            for (Object objekat : listaObjekata) {
                if (objekat instanceof Kuca) {
                    if (((Kuca) objekat).getJedinstveniIdentifikator() == this.identifikatorKuce && this.provjeriStarosnuDob()) {
                        ideKuci = true;
                        ((Kuca) objekat).setTrenutniBrojUkucana(((Kuca) objekat).getTrenutniBrojUkucana() + 1);
                        //System.out.println(this + " se vratio kuci.");
                        Platform.runLater(() -> {
                            pocetniProzorController.glavniProzorKontroler.terminal.appendText(this + " se vratio kuci.\n");});
                    } else return;
                }
            }
            if (uKuci) {
                Main.mapa.listaKuca.get(this.identifikatorKuce - 1).setTrenutniBrojUkucana(
                        Main.mapa.listaKuca.get(this.identifikatorKuce - 1).getTrenutniBrojUkucana() - 1);
                uKuci = false;
            }
            if (ideKuci) {
                uKuci = true;
            }
            Main.mapa.mapa[pozicijaX][pozicijaY].lista.remove(this);
            pozicijaX = pozicijaX + 1;
            Main.mapa.mapa[pozicijaX][pozicijaY].lista.add(this);
            trenutnoMoguceDole = trenutnoMoguceDole - 1;
            trenutnoMoguceGore = trenutnoMoguceGore + 1;
            Platform.runLater(() -> {
                pocetniProzorController.glavniProzorKontroler.terminal.appendText(this + " je otisao dole na polje "
                + "[" + pozicijaX + "," + pozicijaY + "] .\n");});
        }
    }

    public void idiGore() {
        if (trenutnoMoguceGore > 0 && provjeriRastojanje(pozicijaX - 1, pozicijaY)) {
            boolean ideKuci = false;
            List<Object> listaObjekata = Main.mapa.mapa[pozicijaX - 1][pozicijaY].lista;
            for (Object objekat : listaObjekata) {
                if (objekat instanceof Kuca) {
                    if (((Kuca) objekat).getJedinstveniIdentifikator() == this.identifikatorKuce && this.provjeriStarosnuDob()) {
                        ideKuci = true;
                        ((Kuca) objekat).setTrenutniBrojUkucana(((Kuca) objekat).getTrenutniBrojUkucana() + 1);
                        //System.out.println(this + " se vratio kuci.");
                        Platform.runLater(() -> {
                            pocetniProzorController.glavniProzorKontroler.terminal.appendText(this + " se vratio kuci.\n");});
                    } else return;
                }
            }
            if (uKuci) {
                Main.mapa.listaKuca.get(this.identifikatorKuce - 1).setTrenutniBrojUkucana(
                        Main.mapa.listaKuca.get(this.identifikatorKuce - 1).getTrenutniBrojUkucana() - 1);
                uKuci = false;
            }
            if (ideKuci) {
                uKuci = true;
            }
            Main.mapa.mapa[pozicijaX][pozicijaY].lista.remove(this);
            pozicijaX = pozicijaX - 1;
            Main.mapa.mapa[pozicijaX][pozicijaY].lista.add(this);
            trenutnoMoguceGore = trenutnoMoguceGore - 1;
            trenutnoMoguceDole = trenutnoMoguceDole + 1;
            Platform.runLater(() -> {
                pocetniProzorController.glavniProzorKontroler.terminal.appendText(this + " je otisao gore na polje "
                        + "[" + pozicijaX + "," + pozicijaY + "] .\n");});
        }
    }

    public void idiDesno() {
        if (trenutnoMoguceDesno > 0 && provjeriRastojanje(pozicijaX, pozicijaY + 1)) {
            boolean ideKuci = false;
            List<Object> listaObjekata = Main.mapa.mapa[pozicijaX][pozicijaY + 1].lista;
            for (Object objekat : listaObjekata) {
                if (objekat instanceof Kuca) {
                    if (((Kuca) objekat).getJedinstveniIdentifikator() == this.identifikatorKuce && this.provjeriStarosnuDob()) {
                        ideKuci = true;
                        ((Kuca) objekat).setTrenutniBrojUkucana(((Kuca) objekat).getTrenutniBrojUkucana() + 1);
                        //System.out.println(this + " se vratio kuci.");
                        Platform.runLater(() -> {
                            pocetniProzorController.glavniProzorKontroler.terminal.appendText(this + " se vratio kuci.\n");});
                    } else return;
                }
            }
            if (uKuci) {
                Main.mapa.listaKuca.get(this.identifikatorKuce - 1).setTrenutniBrojUkucana(
                        Main.mapa.listaKuca.get(this.identifikatorKuce - 1).getTrenutniBrojUkucana() - 1);
                uKuci = false;
            }
            if (ideKuci) {
                uKuci = true;
            }
            Main.mapa.mapa[pozicijaX][pozicijaY].lista.remove(this);
            pozicijaY = pozicijaY + 1;
            Main.mapa.mapa[pozicijaX][pozicijaY].lista.add(this);
            trenutnoMoguceLijevo = trenutnoMoguceLijevo + 1;
            trenutnoMoguceDesno = trenutnoMoguceDesno - 1;
            Platform.runLater(() -> {
                pocetniProzorController.glavniProzorKontroler.terminal.appendText(this + " je otisao desno na polje "
                        + "[" + pozicijaX + "," + pozicijaY + "] .\n");});
        }
    }

    public void idiLijevo() {
        if (trenutnoMoguceLijevo > 0 && provjeriRastojanje(pozicijaX, pozicijaY - 1)) {
            boolean ideKuci = false;
            List<Object> listaObjekata = Main.mapa.mapa[pozicijaX][pozicijaY - 1].lista;
            for (Object objekat : listaObjekata) {
                if (objekat instanceof Kuca) {
                    if (((Kuca) objekat).getJedinstveniIdentifikator() == this.identifikatorKuce && this.provjeriStarosnuDob()) {
                        ideKuci = true;
                        ((Kuca) objekat).setTrenutniBrojUkucana(((Kuca) objekat).getTrenutniBrojUkucana() + 1);
                        //System.out.println(this + " se vratio kuci.");
                        Platform.runLater(() -> {
                            pocetniProzorController.glavniProzorKontroler.terminal.appendText(this + " se vratio kuci.\n");});
                    } else return;
                }
            }
            if (uKuci) {
                Main.mapa.listaKuca.get(this.identifikatorKuce - 1).setTrenutniBrojUkucana(
                        Main.mapa.listaKuca.get(this.identifikatorKuce - 1).getTrenutniBrojUkucana() - 1);
                uKuci = false;
            }
            if (ideKuci) {
                uKuci = true;
            }
            Main.mapa.mapa[pozicijaX][pozicijaY].lista.remove(this);
            pozicijaY = pozicijaY - 1;
            Main.mapa.mapa[pozicijaX][pozicijaY].lista.add(this);
            trenutnoMoguceLijevo = trenutnoMoguceLijevo - 1;
            trenutnoMoguceDesno = trenutnoMoguceDesno + 1;
            Platform.runLater(() -> {
                pocetniProzorController.glavniProzorKontroler.terminal.appendText(this + " je otisao lijevo na polje "
                        + "[" + pozicijaX + "," + pozicijaY + "] . \n");});
        }
    }


    @Override
    public void run() {
        while (true) {
            if (Main.simulacijaZavrsena) break;
            if (zaustavljen || izolovan) {
                synchronized (Mapa.monitor) {
                    if (izolovan) {
                        Main.mapa.mapa[this.pozicijaX][this.pozicijaY].lista.remove(this);
                        Main.mapa.mapa[Main.mapa.listaKuca.get(this.getIdentifikatorKuce() - 1).pozicijaX]
                                [Main.mapa.listaKuca.get(this.getIdentifikatorKuce() - 1).pozicijaY].lista.add(this);
                        this.pozicijaX = Main.mapa.listaKuca.get(this.getIdentifikatorKuce() - 1).getPozicijaX();
                        this.pozicijaY = Main.mapa.listaKuca.get(this.getIdentifikatorKuce() - 1).getPozicijaY();
                        this.vratiRadijusKretanja();
                        this.setuKuci(true);
                        Main.mapa.listaKuca.get(this.getIdentifikatorKuce() - 1).setTrenutniBrojUkucana(
                                Main.mapa.listaKuca.get(this.getIdentifikatorKuce() - 1).getTrenutniBrojUkucana() + 1);
                        break;

                    }
                    if (zaustavljen) {
                        if (!izolovan && !Main.mapa.kapacitetiBolnicaPopunjeni) {
                            this.izolujUkucane();
                            Platform.runLater(() -> {
                                pocetniProzorController.glavniProzorKontroler.terminal.appendText(this +
                                        " se zarazio i zaistavljen je na poziciji [" + pozicijaX + "," + pozicijaY + "] .\n" );});
                            for (Bolnica bolnica : Main.mapa.listaBolnica) {
                                if (bolnica.getBrojZarazenih() < bolnica.kapacitetBolnice) {
                                    bolnica.uvecajBrojZarazenih();
                                    //System.out.println("Broj zarazenih u bolnici " + bolnica.getIdentifikator() + " je " + bolnica.getBrojZarazenih() + ".");
                                    Alarm alarm = new Alarm(pozicijaX, pozicijaY, identifikatorKuce, jedinstveniIdentifikator,
                                            bolnica.getIdentifikator());
                                    Main.nadzornik.dodajAlarmNaStek(alarm);
                                    break;
                                }
                            }
                            break;
                        }
                    }
                }
                }
                else{
                Random rand = Mapa.rand;
                if (!uKuci || !Main.mapa.provjeriStanjeUKuci(this)) {
                    synchronized (Mapa.monitor) {//izbor smjera kretanja kroz random velicinu,0=lijevo,1=desno,2=gore,3=dole;
                        int pravac = rand.nextInt(4);
                        if (pravac == 0) {
                            idiLijevo();
                            //System.out.println(this + " je otisao lijevo na poziciju [" + pozicijaY + "][" + pozicijaX + "]");
                        } else if (pravac == 1) {
                            idiDesno();
                            //System.out.println(this + " je otisao desno na poziciju [" + pozicijaY + "][" + pozicijaX + "]");
                        } else if (pravac == 2) {
                            idiGore();
                            //System.out.println(this + " je otisao gore na poziciju [" + pozicijaY + "][" + pozicijaX + "]");
                        } else {
                            idiDole();
                            //System.out.println(this + " je otisao dolje na poziciju [" + pozicijaY + "][" + pozicijaX + "]");
                        }
                        List<KontrolniPunkt> listaKPunktova = Main.mapa.listaKontrolnihPunktova;
                        for (KontrolniPunkt kontrolniPunkt : listaKPunktova) {
                            if (this.graniciLi(kontrolniPunkt) && tjelesnaTemperatura >= 37) {
                                if(!izolovan) {
                                    zaustavljen = true;
                                    //System.out.println(this + " se zarazio i zaustavljen je.");
                                }
                            }
                        }
                    }
                    this.yield();
                    try {
                        sleep(1500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
                    }
                }
            }

        }
    }
}
