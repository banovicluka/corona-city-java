package CoronaCity.Kontroleri;

import CoronaCity.Objekti.Bolnica;
import CoronaCity.Main;
import CoronaCity.Mapa.Mapa;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class pregledajStanjeAmbulantiProzorController {
    public Button dugmeDodajAmbulantu;
    public VBox vBoxBolnice;
    public Label LabelaInformacijeODodavanju;

    @FXML
    private void initialize(){
        synchronized (Mapa.monitor){
            for(Bolnica bolnica: Main.mapa.listaBolnica){
                vBoxBolnice.getChildren().add(new javafx.scene.control.Label(bolnica.toString()));
            }
        }
    }

    public void dodajAmbulantuClicked(MouseEvent mouseEvent) {
        synchronized (Mapa.monitor){
            if(Main.mapa.brojAmbulantiZaDodati>0){
                int i=Main.mapa.i;
                int j= Main.mapa.j;
                int kapacitet=0;
                if((5*Main.mapa.brojStanovnika)/100 !=0)
                    kapacitet=Math.round((float)Main.mapa.rand.nextInt((5*Main.mapa.brojStanovnika)/100)+(float)(10*Main.mapa.brojStanovnika)/100);
                else kapacitet=Math.round((float)(10*Main.mapa.brojStanovnika)/100);
                if(kapacitet==0) kapacitet=1;
                Bolnica bolnica = new Bolnica(i,j,kapacitet);
                Main.mapa.mapa[i][j].lista.add(bolnica);
                Main.mapa.listaBolnica.add(bolnica);
                vBoxBolnice.getChildren().add(new Label(bolnica.toString()));
                Main.mapa.brojAmbulantiZaDodati--;
                Main.mapa.i++;
                Platform.runLater(()->{
                    LabelaInformacijeODodavanju.setText(bolnica + " uspjesno dodana.");
                });
            }
            else{
                Platform.runLater(()->{
                    LabelaInformacijeODodavanju.setText("Nije moguce dodati novu ambulantu.");
                });
            }
        }
    }
}
