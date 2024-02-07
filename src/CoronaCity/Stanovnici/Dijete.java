package CoronaCity.Stanovnici;

import CoronaCity.Objekti.Kuca;
import CoronaCity.Main;

import java.io.Serializable;
import java.util.List;

public class Dijete extends Stanovnik implements Serializable {

    public static int brojDjece;

    public  Dijete(){
        super();
    }

    public Dijete(String ime,String prezime,int godinaRodjenja,String pol,int idKuce){
        super(ime,prezime,godinaRodjenja,pol,idKuce);
        brojDjece++;
    }

    @Override
    public void odrediRadijuse(int dimenzijaMape,List<Kuca> lista) {
        int idKuce = this.getIdentifikatorKuce();
        for(Kuca kuca: lista){
            if(kuca.getJedinstveniIdentifikator() == idKuce){
                setRadijusDesno(dimenzijaMape-2-kuca.pozicijaY);
                setRadijusLijevo(kuca.pozicijaY-1);
                setRadijusGore(kuca.pozicijaX-1);
                setRadijusDole(dimenzijaMape-2-kuca.pozicijaX);
            }
        }
    }

    @Override
    public boolean provjeriStarosnuDob() {
        for(Kuca kuca:Main.mapa.listaKuca){
            if(kuca.getJedinstveniIdentifikator()==this.getIdentifikatorKuce()){
                if(kuca.daLiJeKucaPrazna()){
                    return false;
                }
                else return true;
            }
        }
        return false;
    }

    @Override
    public boolean provjeriRastojanje(int x, int y) {
            return true;
    }
}
