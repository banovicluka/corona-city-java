package CoronaCity.Mapa;

import CoronaCity.MjenjacTemperature.MjenjacTemperature;
import CoronaCity.Objekti.Bolnica;
import CoronaCity.Objekti.KontrolniPunkt;
import CoronaCity.Objekti.Kuca;
import CoronaCity.Stanovnici.Dijete;
import CoronaCity.Stanovnici.Odrasli;
import CoronaCity.Stanovnici.Stanovnik;
import CoronaCity.Stanovnici.Stari;
import CoronaCity.Vozila.AmbulantnoVozilo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mapa implements Serializable {

    public ElementMape[][] mapa;
    public static int dimenzijaMape;
    private int brojDjece;
    private int brojOdraslih;
    private int brojStarih;
    private int brojKontrolnihPunktova;
    private int brojAmbulantnihVozila;
    private int brojKuca;
    private int brojBolnica =4;
    public int brojStanovnika;
    public List<Bolnica> listaBolnica = new ArrayList<Bolnica>();
    public List<Kuca> listaKuca = new ArrayList<Kuca>();
    public List<Stanovnik> listaStanovnika = new ArrayList<Stanovnik>();
    public List<KontrolniPunkt> listaKontrolnihPunktova = new ArrayList<KontrolniPunkt>();
    public List<AmbulantnoVozilo> listaAmbulantnihVozila = new ArrayList<AmbulantnoVozilo>();
    public static Random rand = new Random();
    public static Object monitor = new Object();
    public static Object fileMonitor = new Object();
    public boolean kapacitetiBolnicaPopunjeni;
    public int brojZarazenih=0;
    public int brojOporavljenih=0;
    public int brojSlobodnihVozila;
    public int brojAmbulantiZaDodati=0;
    public MjenjacTemperature mjenjajTemperature = new MjenjacTemperature();
    public int i=1;
    public int j=0;


    public Mapa(int dimenzijaMape, int brojDjece,int brojOdraslih,int brojStarih, int brojKontrolnihPunktova,
        int brojKuca, int brojAmbulantnihVozila){
        this.brojStanovnika=brojDjece+brojOdraslih+brojStarih;
        this.dimenzijaMape = dimenzijaMape;
        this.brojDjece=brojDjece;
        this.brojOdraslih=brojOdraslih;
        this.brojStarih=brojStarih;
        this.brojKontrolnihPunktova=brojKontrolnihPunktova;
        this.brojKuca=brojKuca;
        this.brojAmbulantnihVozila=brojAmbulantnihVozila;
        this.kapacitetiBolnicaPopunjeni=false;
        this.brojSlobodnihVozila=brojAmbulantnihVozila;
        mapa = new ElementMape[dimenzijaMape][dimenzijaMape];
        for(int i=0; i <dimenzijaMape;i++)
            for(int j=0;j<dimenzijaMape;j++){
            mapa[i][j] = new ElementMape();
        }
        postaviBolnice();
        postaviKuce();
        postaviKontrolnePunktove();
        postaviAmbulantnaVozila();
        postaviStanovnike();
        ispisiMapu();
    }

    public void postaviBolnice(){
      int kapacitet;

      if((5*brojStanovnika)/100 !=0)
          kapacitet=Math.round((float)rand.nextInt((5*brojStanovnika)/100)+(float)(10*brojStanovnika)/100);
      else kapacitet=Math.round((float)(10*brojStanovnika)/100);
      if(kapacitet==0) kapacitet=1;
      Bolnica bolnica1 = new Bolnica(0,0,kapacitet);

      if((5*brojStanovnika)/100 !=0)
          kapacitet=Math.round((float)rand.nextInt((5*brojStanovnika)/100)+(float)(10*brojStanovnika)/100);
      else kapacitet=Math.round((float)(10*brojStanovnika)/100);
      if(kapacitet==0) kapacitet=1;
      Bolnica bolnica2 = new Bolnica(0,dimenzijaMape-1,kapacitet);

      if((5*brojStanovnika)/100 !=0)
          kapacitet=Math.round((float)rand.nextInt((5*brojStanovnika)/100)+(float)(10*brojStanovnika)/100);
      else kapacitet=Math.round((float)(10*brojStanovnika)/100);
      if(kapacitet==0) kapacitet=1;
      Bolnica bolnica3 = new Bolnica(dimenzijaMape-1,0,kapacitet);

      if((5*brojStanovnika)/100 !=0)
          kapacitet=Math.round((float)rand.nextInt((5*brojStanovnika)/100)+(float)(10*brojStanovnika)/100);
      else kapacitet=Math.round((float)(10*brojStanovnika)/100);
      if(kapacitet==0) kapacitet=1;
      Bolnica bolnica4 = new Bolnica(dimenzijaMape-1,dimenzijaMape-1,kapacitet);

      mapa[0][0].lista.add(bolnica1);
      mapa[0][dimenzijaMape-1].lista.add(bolnica2);
      mapa[dimenzijaMape-1][0].lista.add(bolnica3);
      mapa[dimenzijaMape-1][dimenzijaMape-1].lista.add(bolnica4);
      listaBolnica.add(bolnica1);
      listaBolnica.add(bolnica2);
      listaBolnica.add(bolnica3);
      listaBolnica.add(bolnica4);
    }

    public void postaviKuce(){
        int broj= brojKuca;
        while(broj!=0){
            int x = rand.nextInt(dimenzijaMape-4) + 2;
            int y = rand.nextInt(dimenzijaMape-4) + 2;
            if(mapa[x][y].lista.isEmpty() && mapa[x-1][y].lista.isEmpty() && mapa[x+1][y].lista.isEmpty() && mapa[x][y-1].lista.isEmpty() &&
                mapa[x][y+1].lista.isEmpty() && mapa[x+1][y+1].lista.isEmpty() && mapa[x-1][y+1].lista.isEmpty() &&
                mapa[x-1][y-1].lista.isEmpty() && mapa[x+1][y-1].lista.isEmpty() && mapa[x][y+2].lista.isEmpty() &&
                mapa[x][y-2].lista.isEmpty() && mapa[x-2][y].lista.isEmpty() && mapa[x+2][y].lista.isEmpty()){
                Kuca kuca = new Kuca(x,y);
                mapa[x][y].lista.add(kuca);
                broj--;
                listaKuca.add(kuca);
            }
        }
    }

    public void postaviKontrolnePunktove(){
        int broj = brojKontrolnihPunktova;
        while(broj!=0){
            int x = rand.nextInt(dimenzijaMape-2) +1;
            int y = rand.nextInt(dimenzijaMape-2) +1;
            if(mapa[x][y].lista.isEmpty() && mapa[x-1][y].lista.isEmpty() && mapa[x+1][y].lista.isEmpty() && mapa[x][y-1].lista.isEmpty() &&
                    mapa[x][y+1].lista.isEmpty()){
                KontrolniPunkt kontrolniPunkt = new KontrolniPunkt(x,y);
                mapa[x][y].lista.add(kontrolniPunkt);
                broj--;
                listaKontrolnihPunktova.add(kontrolniPunkt);
            }
        }
    }

    public void postaviAmbulantnaVozila(){
        int broj = brojAmbulantnihVozila;
        while(broj!=0){
            AmbulantnoVozilo ambulantnoVozilo = new AmbulantnoVozilo();
            listaAmbulantnihVozila.add(ambulantnoVozilo);
            broj--;
        }
    }

    public void postaviStanovnike() {
        int broj = brojStarih;
        int j = 0;
        while (broj != 0) {
            for (Kuca i : listaKuca) {
                Stari stari = new Stari("stari" + j + "ime", "stari" + j + "prezime",
                        rand.nextInt(40) + 1916, rand.nextBoolean() ? "muski" : "zenski",
                        i.getJedinstveniIdentifikator());
                mapa[i.pozicijaX][i.pozicijaY].lista.add(stari);
                stari.pozicijaX = i.pozicijaX;
                stari.pozicijaY = i.pozicijaY;
                i.setTrenutniBrojUkucana(i.getTrenutniBrojUkucana() + 1);
                listaStanovnika.add(stari);
                broj--;
                j++;
                if (broj == 0) break;
            }
        }
        broj = brojOdraslih;
        j = 0;
        while (broj != 0) {
            for (Kuca i : listaKuca) {
                Odrasli odrasli = new Odrasli("odrasli" + j + "ime", "odrasli" + j + "prezime",
                        rand.nextInt(46) + 1956, rand.nextBoolean() ? "muski" : "zenski",
                        i.getJedinstveniIdentifikator());
                mapa[i.pozicijaX][i.pozicijaY].lista.add(odrasli);
                odrasli.pozicijaX = i.pozicijaX;
                odrasli.pozicijaY = i.pozicijaY;
                i.setTrenutniBrojUkucana(i.getTrenutniBrojUkucana() + 1);
                listaStanovnika.add(odrasli);
                broj--;
                j++;
                if(broj == 0) break;
            }
        }
        broj = brojDjece;
        j=0;
        boolean napravljenoDijeteUKuci;
        while(broj!=0){
            for(Kuca i :listaKuca){
                napravljenoDijeteUKuci=false;
                for(Stanovnik stanovnik:listaStanovnika){
                    if(i.getJedinstveniIdentifikator() == stanovnik.getIdentifikatorKuce()){
                        Dijete dijete = new Dijete("dijete" +j + "ime","dijete" + j+ "prezime",
                                rand.nextInt(18)+2002,rand.nextBoolean()? "muski":"zenski",
                                i.getJedinstveniIdentifikator());
                        mapa[i.pozicijaX][i.pozicijaY].lista.add(dijete);
                        dijete.pozicijaX=i.pozicijaX;
                        dijete.pozicijaY=i.pozicijaY;
                        i.setTrenutniBrojUkucana(i.getTrenutniBrojUkucana()+1);
                        listaStanovnika.add(dijete);
                        broj--;
                        j++;
                        napravljenoDijeteUKuci=true;
                        }
                    if(broj==0 || napravljenoDijeteUKuci) break;
                }
                if(broj==0) break;
            }
        }
        for(Stanovnik stanovnik:listaStanovnika){
            stanovnik.odrediRadijuse(dimenzijaMape,listaKuca);
            stanovnik.vratiRadijusKretanja();
        }
    }

    public void ispisiMapu(){
        for(int i = 0 ;i<dimenzijaMape;i++){
            for(int j = 0 ;j<dimenzijaMape;j++){
                System.out.print("[");
                for(Object object:mapa[i][j].lista){
                    System.out.print(object);
                    System.out.print(",");
                }
                System.out.print("]");
            }
            System.out.println();
        }
    }

     public boolean provjeriStanjeUKuci(Stanovnik stanovnik){
        if(stanovnik instanceof Dijete) return false;
        List<Stanovnik> listaUkucana = new ArrayList<>();
        synchronized (Mapa.monitor) {
            for (Stanovnik osoba : listaStanovnika) {
                if (osoba.getIdentifikatorKuce() == stanovnik.getIdentifikatorKuce() && osoba.uKuci) {
                    listaUkucana.add(osoba);
                }
            }
        }
        int brojDijeceUKuci=0;
        int brojOstalihUKuci= 0;
        for(Stanovnik ukucanin: listaUkucana){
            if(ukucanin instanceof Dijete){
                brojDijeceUKuci++;
            }
            else{
                brojOstalihUKuci++;
            }
        }
        if(brojOstalihUKuci == 1 && brojDijeceUKuci != 0) return true;
        else return false;
    }

}
