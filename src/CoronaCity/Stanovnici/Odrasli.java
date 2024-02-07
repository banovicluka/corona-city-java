package CoronaCity.Stanovnici;

import CoronaCity.Objekti.Kuca;
import CoronaCity.Main;

import java.io.Serializable;
import java.util.List;

public class Odrasli extends Stanovnik implements Serializable {

    public static int brojOdraslih;

    public Odrasli(){
        super();
    }

    public Odrasli(String ime,String prezime,int godinaRodjenja,String pol,int idKuce){
        super(ime,prezime,godinaRodjenja,pol,idKuce);
        brojOdraslih++;
    }

    @Override
    public void odrediRadijuse(int dimenzijaMape,List<Kuca> lista) {
        int idKuce = this.getIdentifikatorKuce();
        int mjera = Math.round(((float) 25*dimenzijaMape)/100);
        //System.out.println("*" + mjera);
        for(Kuca kuca: lista){
            if(kuca.getJedinstveniIdentifikator() == idKuce){
                int nadoknadiLijevo,nadoknadiDesno,nadoknadiGore,nadoknadiDole;
                if(kuca.pozicijaY-mjera>0){
                    setRadijusLijevo(mjera);
                    nadoknadiDesno=0;
                }
                else{
                    setRadijusLijevo(kuca.pozicijaY-1);
                    nadoknadiDesno=mjera-(kuca.pozicijaY-1);
                }
                if(kuca.pozicijaY+mjera<dimenzijaMape-1){
                    setRadijusDesno(mjera);
                    nadoknadiLijevo=0;
                }
                else{
                    setRadijusDesno(dimenzijaMape-2-kuca.pozicijaY);
                    nadoknadiLijevo=mjera-(dimenzijaMape-2-kuca.pozicijaY);
                }
                if(kuca.pozicijaX-mjera>0){
                    setRadijusGore(mjera);
                    nadoknadiDole=0;
                }
                else{
                    setRadijusGore(kuca.pozicijaX-1);
                    nadoknadiDole=mjera-(kuca.pozicijaX-1);
                }
                if(kuca.pozicijaX+mjera<dimenzijaMape-1){
                    setRadijusDole(mjera);
                    nadoknadiGore=0;
                }
                else{
                    setRadijusDole(dimenzijaMape-2-kuca.pozicijaX);
                    nadoknadiGore=mjera-(dimenzijaMape-2-kuca.pozicijaX);
                }
                if(nadoknadiDesno!=0 || nadoknadiLijevo!=0 || nadoknadiDole!=0 || nadoknadiGore!=0 ){
                    if(nadoknadiDesno!=0){
                        setRadijusDesno(getRadijusDesno()+nadoknadiDesno);
                    }
                    if(nadoknadiLijevo!=0){
                        setRadijusLijevo(getRadijusLijevo()+nadoknadiLijevo);
                    }
                    if(nadoknadiDole!=0){
                        setRadijusDole(getRadijusDole()+nadoknadiDole);
                    }
                    if(nadoknadiGore!=0){
                        setRadijusGore(getRadijusGore()+nadoknadiGore);
                    }
                }
            }
        }
    }

    @Override
    public boolean provjeriStarosnuDob() {
        return true;
    }

    @Override
    public boolean provjeriRastojanje(int x, int y) {
        for(Stanovnik stanovnik : Main.mapa.listaStanovnika){
            if(!stanovnik.uKuci && !(stanovnik instanceof Dijete) && Math.abs(stanovnik.pozicijaX-x)<3 && Math.abs(stanovnik.pozicijaY-y)<3 && this.getIdentifikatorKuce()!=stanovnik.getIdentifikatorKuce()) {
                return false;
            }
        }
        return true;
    }
}
