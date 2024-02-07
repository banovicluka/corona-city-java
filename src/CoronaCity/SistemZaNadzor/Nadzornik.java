package CoronaCity.SistemZaNadzor;

import CoronaCity.Objekti.Bolnica;
import CoronaCity.Kontroleri.pocetniProzorController;
import CoronaCity.Main;
import CoronaCity.Stanovnici.Stanovnik;
import javafx.application.Platform;

import java.util.Stack;

public class Nadzornik {
    public Stack<Alarm> stekAlarma = new Stack<Alarm>();

    public void dodajAlarmNaStek(Alarm alarm) {
        stekAlarma.push(alarm);
    }

    public void skiniAlarmeSaSteka() {
        while (!stekAlarma.isEmpty() && Main.mapa.brojSlobodnihVozila > 0) {
            Alarm alarm = stekAlarma.pop();
            Bolnica bolnica = Main.mapa.listaBolnica.get(alarm.getIdAmbulante() - 1);
            Stanovnik zarazeni = null;
            for (Stanovnik stanovnik : Main.mapa.listaStanovnika) {
                if (stanovnik.getJedinstveniIdentifikator() == alarm.getIdZarazenog()) {
                    zarazeni = stanovnik;
                    break;
                }
            }
            bolnica.smjestiPacijentaUBolnicu(zarazeni);
            if (!stekAlarma.empty() && Main.mapa.brojSlobodnihVozila == 0) {
                Platform.runLater(() -> {
                    pocetniProzorController.glavniProzorKontroler.terminal.appendText("Sva vozila su zauzeta, nije moguce hospitalizovati sve zarazene osobe.\n");
                });
            }
        }
    }
}
