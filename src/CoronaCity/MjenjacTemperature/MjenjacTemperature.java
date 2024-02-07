package CoronaCity.MjenjacTemperature;

import CoronaCity.Main;
import CoronaCity.Stanovnici.Stanovnik;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MjenjacTemperature extends Thread implements Serializable {

    @Override
    public void run() {
        while (!Main.simulacijaZavrsena) {
            try {
                sleep(7000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
            }

            for (Stanovnik stanovnik : Main.mapa.listaStanovnika) {
                stanovnik.dodajUPoslednjeTriTemperature(stanovnik.getTjelesnaTemperatura());
                stanovnik.promjeniTemperaturu();
            }
        }
    }
}
