package CoronaCity.SistemZaNadzor;

public class Alarm {
    private int pozicijaX;
    private int pozicijaY;
    private int idKuce;
    private int idZarazenog;
    private int idAmbulante;

    public Alarm(int pozicijaX,int pozicijaY,int idKuce,int idZarazenog,int idAmbulante){
        this.pozicijaX=pozicijaX;
        this.pozicijaY=pozicijaY;
        this.idKuce=idKuce;
        this.idZarazenog=idZarazenog;
        this.idAmbulante=idAmbulante;
    }

    public int getIdAmbulante() {
        return idAmbulante;
    }

    public void setIdAmbulante(int idAmbulante) {
        this.idAmbulante = idAmbulante;
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

    public int getIdKuce() {
        return idKuce;
    }

    public void setIdKuce(int idKuce) {
        this.idKuce = idKuce;
    }

    public int getIdZarazenog() {
        return idZarazenog;
    }

    public void setIdZarazenog(int idZarazenog) {
        this.idZarazenog = idZarazenog;
    }

}
