package CoronaCity.Objekti;

import java.io.Serializable;

public class KontrolniPunkt implements Serializable {

    public static int brojInstanci;
    public int pozicijaX;
    public int pozicijaY;

    public KontrolniPunkt(int pozicijaX,int pozicijaY){
        brojInstanci++;
        this.pozicijaX=pozicijaX;
        this.pozicijaY=pozicijaY;
    }

    @Override
    public String toString() {
        return "Kontrolni punkt";
    }
}
