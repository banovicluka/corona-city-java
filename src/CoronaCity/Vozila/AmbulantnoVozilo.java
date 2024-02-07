package CoronaCity.Vozila;

import java.io.Serializable;

public class AmbulantnoVozilo implements Serializable {
    public static int brojInstanci;
    private int identifikator;
    public int pozicijaX;
    public int pozicijaY;
    private boolean zauzeto;

    public AmbulantnoVozilo(){
        brojInstanci++;
        identifikator=brojInstanci;
        zauzeto=false;
    }

    public AmbulantnoVozilo(int pozicijaX,int pozicijaY){
        brojInstanci++;
        identifikator=brojInstanci;
        this.pozicijaX=pozicijaX;
        this.pozicijaY=pozicijaY;
        zauzeto=false;
    }

    public boolean isZauzeto() {
        return zauzeto;
    }

    public void setZauzeto(boolean zauzeto) {
        this.zauzeto = zauzeto;
    }

    public int getPozicijaX() {
        return pozicijaX;
    }

    public void setPozicijaX(int pozicijaX) {
        this.pozicijaX = pozicijaX;
    }

    public int getPozicijaY() {
        return pozicijaY;
    }

    public void setPozicijaY(int pozicijaY) {
        this.pozicijaY = pozicijaY;
    }
}
