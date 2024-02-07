package CoronaCity.Objekti;

import java.io.Serializable;

public class Kuca implements Serializable {
    private int jedinstveniIdentifikator;
    private int trenutniBrojUkucana;
    public static int brojInstanci;
    public int pozicijaX;
    public int pozicijaY;

    public int getJedinstveniIdentifikator() {
        return jedinstveniIdentifikator;
    }

    public void setTrenutniBrojUkucana(int trenutniBrojUkucana) {
        this.trenutniBrojUkucana = trenutniBrojUkucana;
    }

    public int getTrenutniBrojUkucana() {
        return trenutniBrojUkucana;
    }

    public int getPozicijaX() {
        return pozicijaX;
    }

    public int getPozicijaY() {
        return pozicijaY;
    }

    public boolean daLiJeKucaPrazna(){
        if(trenutniBrojUkucana==0){
            return true;

        }
        else return false;
    }

    public Kuca(int pozicijaX, int pozicijaY){
        brojInstanci++;
        this.jedinstveniIdentifikator=brojInstanci;
        trenutniBrojUkucana=0;
        this.pozicijaX=pozicijaX;
        this.pozicijaY=pozicijaY;
    }

    @Override
    public String toString() {
        return "Kuca " + "id:" + jedinstveniIdentifikator + " BrojUkucana: " + trenutniBrojUkucana;
    }
}
