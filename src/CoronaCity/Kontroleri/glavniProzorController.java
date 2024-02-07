package CoronaCity.Kontroleri;

import CoronaCity.*;
import CoronaCity.FileWatcher.FileWatcher;
import CoronaCity.Mapa.Mapa;
import CoronaCity.Objekti.Bolnica;
import CoronaCity.Objekti.KontrolniPunkt;
import CoronaCity.Objekti.Kuca;
import CoronaCity.Stanovnici.Dijete;
import CoronaCity.Stanovnici.Odrasli;
import CoronaCity.Stanovnici.Stanovnik;
import CoronaCity.Stanovnici.Stari;
import CoronaCity.Vozila.AmbulantnoVozilo;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;


public class glavniProzorController {

    @FXML
    public GridPane matrica;
    public Button dugmePosaljiAmbulantnoVozilo;
    public Button dugmeZaustaviSimulaciju;
    public Button dugmePokreniSimulacijuPonovo;
    public Button dugmePregledajStanjeAmbulanti;
    public Button dugmePregledajStatistickePodatke;
    public Button dugmeOmoguciKretanje;

    public static Image slikaDjecaka = null;
    public static Image slikaDjevojcica = null;
    public static Image slikaOdrasliMusko = null;
    public static Image slikaOdrasliZensko = null;
    public static Image slikaDeda = null;
    public static Image slikaBaba = null;
    public static Image slikaAmbulantnoVozilo = null;
    public static Image slikaBolnica = null;
    public static Image slikaKuca = null;
    public static Image slikaKontrolniPunkt = null;
    public TextField brojOporavljenih;
    public TextField brojZarazenih;
    public TextArea terminal;

    public void ucitajSlike() {
        try {

            String path = new File("src/CoronaCity/Resursi/slike").getAbsolutePath();
            slikaDjecaka = new Image(new FileInputStream(path + File.separator + "son.png"));
            slikaDjevojcica = new Image(new FileInputStream(path+ File.separator + "daughter.png"));
            slikaOdrasliMusko = new Image(new FileInputStream( path+ File.separator + "father.png"));
            slikaOdrasliZensko = new Image(new FileInputStream( path+ File.separator + "mother.png"));
            slikaBaba = new Image(new FileInputStream( path+ File.separator + "grandmother.png"));
            slikaDeda = new Image(new FileInputStream( path+ File.separator + "grandfather.png"));
            slikaKuca = new Image(new FileInputStream( path+ File.separator + "house.png"));
            slikaAmbulantnoVozilo = new Image(new FileInputStream( path+ File.separator + "ambulance.png"));
            slikaBolnica = new Image(new FileInputStream( path + File.separator + "hospital.png"));
            slikaKontrolniPunkt = new Image(new FileInputStream( path+ File.separator + "thermometer.png"));
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
        }
    }


    @FXML
    public void initialize(){
        dugmePokreniSimulacijuPonovo.setDisable(true);
        ucitajSlike();
        Runnable crtacGlavnogProzora = () ->{

            while(true) {
                List<Node> lista = matrica.getChildrenUnmodifiable().stream().filter(node -> node instanceof ImageView)
                        .collect(Collectors.toList());
                Platform.runLater(() -> {
                    matrica.getChildren().removeAll(lista);
                });
                if(!Main.nadzornik.stekAlarma.empty()){
                    dugmePosaljiAmbulantnoVozilo.setStyle("-fx-background-color: Tomato");
                }
                if(Main.mapa.brojAmbulantiZaDodati>0){
                    dugmePregledajStanjeAmbulanti.setStyle("-fx-background-color: Tomato");
                }
                else{
                    dugmePregledajStanjeAmbulanti.setStyle(null);
                }
                for (int i = 1; i <= Main.mapa.dimenzijaMape; i++) {
                    for (int j = 1; j <= Main.mapa.dimenzijaMape; j++) {
                        final int x = i;
                        final int y = j;
                        List<Object> elementiNaPolju = Main.mapa.mapa[x-1][y-1].lista;
                        int brojElemenataNaPolju = elementiNaPolju.size();
                        for (int k = 0; k < brojElemenataNaPolju; k++) {
                            if (elementiNaPolju.get(k) instanceof Kuca) {
                                ImageView slika = new ImageView(slikaKuca);
                                slika.setFitHeight(matrica.getPrefHeight() / (double) Main.mapa.dimenzijaMape + 3.0D);
                                slika.setFitWidth(matrica.getPrefWidth() / (double) Main.mapa.dimenzijaMape + 3.0D);
                                Platform.runLater(() -> {
                                    matrica.add(slika, y, x);
                                });
                                break;
                            }
                            if (elementiNaPolju.get(k) instanceof Bolnica) {
                                ImageView slika = new ImageView(slikaBolnica);
                                slika.setFitHeight(matrica.getPrefHeight() / (double) Main.mapa.dimenzijaMape + 3.0D);
                                slika.setFitWidth(matrica.getPrefWidth() / (double) Main.mapa.dimenzijaMape + 3.0D);
                                Platform.runLater(() -> {
                                    matrica.add(slika, y, x);
                                });
                                break;
                            }
                            if (elementiNaPolju.get(k) instanceof KontrolniPunkt) {
                                ImageView slika = new ImageView(slikaKontrolniPunkt);
                                slika.setFitHeight(matrica.getPrefHeight() / (double) Main.mapa.dimenzijaMape + 3.0D);
                                slika.setFitWidth(matrica.getPrefWidth() / (double) Main.mapa.dimenzijaMape + 3.0D);
                                Platform.runLater(() -> {
                                    matrica.add(slika, y, x);
                                });
                                break;
                            }
                            if (elementiNaPolju.get(k) instanceof AmbulantnoVozilo) {
                                ImageView slika = new ImageView(slikaAmbulantnoVozilo);
                                slika.setFitHeight(matrica.getPrefHeight() / (double) Main.mapa.dimenzijaMape + 3.0D);
                                slika.setFitWidth(matrica.getPrefWidth() / (double) Main.mapa.dimenzijaMape + 3.0D);
                                Platform.runLater(() -> {
                                    matrica.add(slika, y, x);
                                });
                                synchronized (Mapa.monitor){
                                    Main.mapa.brojSlobodnihVozila++;
                                    ((AmbulantnoVozilo) elementiNaPolju.get(k)).setZauzeto(false);
                                    Main.mapa.mapa[((AmbulantnoVozilo) elementiNaPolju.get(k)).getPozicijaX()]
                                            [((AmbulantnoVozilo) elementiNaPolju.get(k)).getPozicijaY()].lista
                                            .remove(elementiNaPolju.get(k));

                                }
                                break;
                            }
                            if (elementiNaPolju.get(k) instanceof Odrasli) {
                                ImageView slika;
                                if ("muski".equals(((Odrasli) elementiNaPolju.get(k)).getPol())) {
                                    slika = new ImageView(slikaOdrasliMusko);
                                } else {
                                    slika = new ImageView(slikaOdrasliZensko);
                                }
                                slika.setFitHeight(matrica.getPrefHeight() / (double) Main.mapa.dimenzijaMape + 3.0D);
                                slika.setFitWidth(matrica.getPrefWidth() / (double) Main.mapa.dimenzijaMape + 3.0D);
                                Platform.runLater(() -> {
                                    matrica.add(slika, y, x);
                                });
                                break;
                            }
                            if (elementiNaPolju.get(k) instanceof Stari) {
                                ImageView slika;
                                if ("muski".equals(((Stari) elementiNaPolju.get(k)).getPol())) {
                                    slika = new ImageView(slikaDeda);
                                } else {
                                    slika = new ImageView(slikaBaba);
                                }
                                slika.setFitHeight(matrica.getPrefHeight() / (double) Main.mapa.dimenzijaMape + 3.0D);
                                slika.setFitWidth(matrica.getPrefWidth() / (double) Main.mapa.dimenzijaMape + 3.0D);
                                Platform.runLater(() -> {
                                    matrica.add(slika, y, x);
                                });
                                break;
                            }
                            if (elementiNaPolju.get(k) instanceof Dijete) {
                                ImageView slika;
                                if ("muski".equals(((Dijete) elementiNaPolju.get(k)).getPol())) {
                                    slika = new ImageView(slikaDjecaka);
                                } else
                                    slika = new ImageView(slikaDjevojcica);
                                slika.setFitHeight(matrica.getPrefHeight() / (double) Main.mapa.dimenzijaMape + 3.0D);
                                slika.setFitWidth(matrica.getPrefWidth() / (double) Main.mapa.dimenzijaMape + 3.0D);
                                Platform.runLater(() -> {
                                    matrica.add(slika, y, x);
                                });
                                break;
                            }
                        }
                    }
                }
                try{
                    sleep(1500);
                }
                catch(InterruptedException ex){
                    Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
                }
            }

        };
        Thread thread = new Thread(crtacGlavnogProzora);
        thread.start();
    }


    public void omoguciKretanjeDugmeKliknuto(MouseEvent mouseEvent) {
        for(Stanovnik stanovnik:Main.mapa.listaStanovnika){
            stanovnik.start();
        }
        Main.mapa.mjenjajTemperature.start();
        for(Bolnica bolnica:Main.mapa.listaBolnica){
            //System.out.println(bolnica.kapacitetBolnice);
            bolnica.start();

        }
        FileWatcher watcher = new FileWatcher();
        watcher.start();
        dugmeOmoguciKretanje.setDisable(true);
    }

    public void zaustaviSimulacijuDugmeKliknuto(MouseEvent mouseEvent) {
        Main.simulacijaZavrsena=true;
        Platform.runLater(() -> {
            terminal.appendText("Simulacija zaustavljena.\n");

        });
        try{
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File("mapa.ser")));
            out.writeObject(Main.mapa);
        }
        catch (IOException ex){
            Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
        }
        dugmePregledajStanjeAmbulanti.setDisable(true);
        dugmePosaljiAmbulantnoVozilo.setDisable(true);
        dugmeZaustaviSimulaciju.setDisable(true);
        dugmePokreniSimulacijuPonovo.setDisable(false);

    }

    public void posaljiAmbulantnoVoziloDugmeKliknuto(MouseEvent mouseEvent) {
        if(!Main.nadzornik.stekAlarma.empty()){
            Main.nadzornik.skiniAlarmeSaSteka();
        }
        if(Main.nadzornik.stekAlarma.empty()){
            dugmePosaljiAmbulantnoVozilo.setStyle(null);
        }
    }

    public void pokreniSimulacijuPonovoDugmeKliknuto(MouseEvent mouseEvent) {
        try{
            synchronized (Mapa.monitor) {
                Main.simulacijaZavrsena = false;
                ObjectInputStream in = new ObjectInputStream(new FileInputStream("mapa.ser"));
                Main.mapa = (Mapa) in.readObject();
                Platform.runLater(() -> {
                    terminal.appendText("Simulacija ponovo pokrenuta.\n");
                });
                for (Stanovnik stanovnik : Main.mapa.listaStanovnika) {
                    if (!stanovnik.isIzolovan() && !stanovnik.isZaustavljen())
                        stanovnik.start();
                }
                for (Bolnica bolnica : Main.mapa.listaBolnica) {
                    bolnica.start();
                }
            }
            Main.mapa.mjenjajTemperature.start();
            dugmePregledajStanjeAmbulanti.setDisable(false);
            dugmeZaustaviSimulaciju.setDisable(false);
            dugmePosaljiAmbulantnoVozilo.setDisable(false);
            dugmePokreniSimulacijuPonovo.setDisable(true);
        }
        catch (IOException ex){
            Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
        }
        catch (ClassNotFoundException ex){
            Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
        }
    }

    public void pregledStanjaAmbulantiDugmeKliknuto(MouseEvent mouseEvent) {
        try{
            Stage pregledAmbulantiProzor = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/CoronaCity/Resursi/pregledajStanjeAmbulantiProzor.fxml"));
            pregledAmbulantiProzor.setTitle("Pregled stanja u ambulantama");
            pregledAmbulantiProzor.setResizable(false);
            Scene scene = new Scene(root);
            pregledAmbulantiProzor.setScene(scene);
            pregledAmbulantiProzor.show();
        }
        catch (IOException ex){
            Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
        }
    }

    public void pregledStatistickihPodatakaDugmeKliknuto(MouseEvent mouseEvent) {
        try{
            Stage statistickiPodaciProzor = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/CoronaCity/Resursi/statistickiPodaciProzor.fxml"));
            statistickiPodaciProzor.setTitle("Pregled statistickih podataka");
            statistickiPodaciProzor.setResizable(false);
            Scene scene = new Scene(root);
            statistickiPodaciProzor.setScene(scene);
            statistickiPodaciProzor.show();
        }
        catch (IOException ex){
            Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
        }
    }

    public void zavrsiSimulacijuDugmeKliknuto(MouseEvent mouseEvent) {
        long vrijemeZavrsavanjaAplikacije = System.currentTimeMillis();
        int trajanjeIzvrsavanjaUSekundama = (int) (vrijemeZavrsavanjaAplikacije-Main.vrijemePokretanjaAplikacije)/1000;
        int trajanjeIzvrsavanjaUMinutama = trajanjeIzvrsavanjaUSekundama/60;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
            Date date = new Date();
            String nazivSimulacije = "SIM-JavaKov-20-" + formatter.format(date) + ".txt";
            String path = new File("src/CoronaCity/simulacije").getAbsolutePath();
            PrintWriter out = new PrintWriter(new FileOutputStream(path + File.separator + "SIM-JavaKov-20-" + formatter.format(date) + ".txt"));
            out.println("Trajanje simulacije je " + trajanjeIzvrsavanjaUMinutama + " minuta i " + trajanjeIzvrsavanjaUSekundama % 60 + "sekundi.");
            synchronized (Mapa.monitor) {
                out.println("Dimenzija mape je " + Main.mapa.dimenzijaMape + ".");
            }
            out.println("Broj kreiranih kuca je " + Kuca.brojInstanci + ".");
            out.println("Broj kreiranih bolnica je " + Bolnica.brojInstanci + ".");
            out.println("Broj kreiranih kontrolnih punkteva je " + KontrolniPunkt.brojInstanci + ".");
            out.println("Broj kreirane djece je " + Dijete.brojDjece + ".");
            out.println("Broj kreiranih odraslih je " + Odrasli.brojOdraslih + ".");
            out.println("Broj kreiranih starijih je " + Stari.brojStarih + ".");
            out.println("============================================================");
            out.println("Statisticki podaci o stanju stanovnika zarazenih virusom JavaKov-20:");
            try{
                String path1 = new File("src/CoronaCity/FileWatcher").getAbsolutePath();
                BufferedReader in = new BufferedReader(new FileReader(path1 + File.separator + "statistika.txt"));
                String sadrzajStatistike;
                while((sadrzajStatistike = in.readLine()) != null){
                    out.println(sadrzajStatistike);
                }
                in.close();
            }
            catch(IOException ex){
                Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
            }
            out.close();
            System.exit(0);
        }catch (IOException ex){
            Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
        }
    }
}
