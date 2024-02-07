package CoronaCity.Kontroleri;

import CoronaCity.*;
import CoronaCity.Izuzeci.NulaKucaException;
import CoronaCity.Izuzeci.NedozvoljenBrojException;
import CoronaCity.Mapa.Mapa;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class pocetniProzorController {

    public Random rand = new Random();
    @FXML
    public Button startDugme;
    @FXML
    public TextField brojDjecePolje;
    @FXML
    public TextField brojOdraslihPolje;
    @FXML
    public TextField brojStarihPolje;
    @FXML
    public TextField brojAmbulantnihVozilaPolje;
    @FXML
    public TextField brojKucaPolje;
    @FXML
    public TextField brojKontrolnihPunktovaPolje;
    @FXML
    public Label greskaTekst;
    public static glavniProzorController glavniProzorKontroler;


    public void startnoDugmePritisnuto(MouseEvent mouseEvent) throws IOException {
        boolean greskaFormat = false,greskaBroj = false,greskaKuca=false;
        int brojDjece = 0,brojOdraslih = 0,brojStarih = 0;
        int brojKontrolnihPunktova = 0,brojAmbulantnihVozila=0, brojKuca = 0;
        // MANIPULACIJA UNESENIM BROJKAMA U POCETNOM PROZORU
        try
        {
            brojDjece = Integer.parseInt(brojDjecePolje.getText());
            if(brojDjece>20)
                throw new NedozvoljenBrojException();
        }
        catch (NumberFormatException ex){
            brojDjecePolje.clear();
            greskaFormat=true;
            Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
        }
        catch (NedozvoljenBrojException ex){
            brojDjecePolje.clear();
            greskaBroj=true;
            Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
        }
        try
        {
            brojOdraslih = Integer.parseInt(brojOdraslihPolje.getText());
            if(brojOdraslih>20)
                throw new NedozvoljenBrojException();
        }
        catch (NumberFormatException ex){
            brojOdraslihPolje.clear();
            greskaFormat=true;
            Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());

        }
        catch (NedozvoljenBrojException ex){
            brojOdraslihPolje.clear();
            greskaBroj=true;
            Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
        }
        try
        {
            brojStarih = Integer.parseInt(brojStarihPolje.getText());
            if(brojStarih>20)
                throw new NedozvoljenBrojException();
        }
        catch (NumberFormatException ex){
            brojStarihPolje.clear();
            greskaFormat=true;
            Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());

        }
        catch (NedozvoljenBrojException ex){
            brojStarihPolje.clear();
            greskaBroj=true;
            Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
        }
        try
        {
            brojKontrolnihPunktova = Integer.parseInt(brojKontrolnihPunktovaPolje.getText());
            if(brojKontrolnihPunktova>20)
                throw new NedozvoljenBrojException();
        }
        catch (NumberFormatException ex){
            brojKontrolnihPunktovaPolje.clear();
            greskaFormat=true;
            Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
        }
        catch (NedozvoljenBrojException ex){
            brojKontrolnihPunktovaPolje.clear();
            greskaBroj=true;
            Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
        }
        try
        {
            brojAmbulantnihVozila = Integer.parseInt(brojAmbulantnihVozilaPolje.getText());
            if(brojAmbulantnihVozila>20)
                throw new NedozvoljenBrojException();
        }
        catch (NumberFormatException ex){
            brojAmbulantnihVozilaPolje.clear();
            greskaFormat=true;
            Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
        }
        catch (NedozvoljenBrojException ex){
            brojAmbulantnihVozilaPolje.clear();
            greskaBroj=true;
            Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
        }
        try
        {
            brojKuca = Integer.parseInt(brojKucaPolje.getText());
            if(brojKuca>20)
                throw new NedozvoljenBrojException();
            if(brojKuca==0)
                throw new NulaKucaException();
        }
        catch (NumberFormatException ex){
            brojKucaPolje.clear();
            greskaFormat=true;
            Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
        }
        catch (NedozvoljenBrojException ex){
            brojKucaPolje.clear();
            greskaBroj=true;
            Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
        }
        catch(NulaKucaException ex){
            brojKucaPolje.clear();
            greskaKuca=true;
            Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
        }
        if(greskaBroj || greskaFormat || greskaKuca){
            if(greskaBroj)
                greskaTekst.setText("Greska! Moguce je unositi samo brojeve koji su manji od 20.");
            if(greskaFormat)
                greskaTekst.setText("Greska! Nije moguce ostavljati prazna polja ili unositi slova u njih.");
            if(greskaKuca)
                greskaTekst.setText("Greska! Nije moguce postaviti 0 kuca.");
        }
        else {
            Stage stage = (Stage) startDugme.getScene().getWindow();
            stage.close();
            //KREIRANJE MAPE

            int dimenzijaMatrice = rand.nextInt(15) + 15;
            Main.mapa = new Mapa(dimenzijaMatrice, brojDjece, brojOdraslih, brojStarih, brojKontrolnihPunktova, brojKuca, brojAmbulantnihVozila);

            //KREIRANJE GLAVNOG PROZORA
            try {
                Stage glavniProzor = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/CoronaCity/Resursi/glavniProzor.fxml"));
                Parent root = loader.load();
                glavniProzorKontroler=loader.getController();
                glavniProzor.setTitle("CoronaCity application");
                glavniProzor.setResizable(false);
                Scene scene = new Scene(root);
                try{
                    String path = new File("src/CoronaCity/FileWatcher").getAbsolutePath();
                    PrintWriter statistika = new PrintWriter(new FileOutputStream(path + File.separator + "statistika.txt"));
                    statistika.flush();
                    statistika.close();
                }
                catch (IOException ex){
                    Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
                }
                GridPane gridPane;
                gridPane = (GridPane) root.getChildrenUnmodifiable().get(0);
                gridPane.setGridLinesVisible(true);
                Platform.runLater(() -> {
                    glavniProzorKontroler.terminal.appendText("Dimenzija mape je " + dimenzijaMatrice + ".\n");
                });
                for (int i = 0; i < dimenzijaMatrice; i++) {
                    RowConstraints row = new RowConstraints();
                    row.setPercentHeight(gridPane.getHeight() / (double) Main.mapa.dimenzijaMape + 5.5D);
                    gridPane.getRowConstraints().add(row);
                    ColumnConstraints column = new ColumnConstraints();
                    column.setPercentWidth(gridPane.getWidth() / (double) Main.mapa.dimenzijaMape + 5.5D);
                    gridPane.getColumnConstraints().add(column);
                }
                glavniProzor.setScene(scene);
                glavniProzor.show();
            }
            catch ( IOException ex){
                Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
            }
        }
    }

    public void startDugmeEnter(KeyEvent keyEvent) throws IOException{
        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            boolean greskaFormat = false,greskaBroj = false,greskaKuca=false;
            int brojDjece = 0,brojOdraslih = 0,brojStarih = 0;
            int brojKontrolnihPunktova = 0,brojAmbulantnihVozila=0, brojKuca = 0;
            // MANIPULACIJA UNESENIM BROJKAMA U POCETNOM PROZORU
            try
            {
                brojDjece = Integer.parseInt(brojDjecePolje.getText());
                if(brojDjece>20)
                    throw new NedozvoljenBrojException();
            }
            catch (NumberFormatException ex){
                brojDjecePolje.clear();
                greskaFormat=true;
                Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
            }
            catch (NedozvoljenBrojException ex){
                brojDjecePolje.clear();
                greskaBroj=true;
                Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
            }
            try
            {
                brojOdraslih = Integer.parseInt(brojOdraslihPolje.getText());
                if(brojOdraslih>20)
                    throw new NedozvoljenBrojException();
            }
            catch (NumberFormatException ex){
                brojOdraslihPolje.clear();
                greskaFormat=true;
                Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());

            }
            catch (NedozvoljenBrojException ex){
                brojOdraslihPolje.clear();
                greskaBroj=true;
                Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
            }
            try
            {
                brojStarih = Integer.parseInt(brojStarihPolje.getText());
                if(brojStarih>20)
                    throw new NedozvoljenBrojException();
            }
            catch (NumberFormatException ex){
                brojStarihPolje.clear();
                greskaFormat=true;
                Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());

            }
            catch (NedozvoljenBrojException ex){
                brojStarihPolje.clear();
                greskaBroj=true;
                Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
            }
            try
            {
                brojKontrolnihPunktova = Integer.parseInt(brojKontrolnihPunktovaPolje.getText());
                if(brojKontrolnihPunktova>20)
                    throw new NedozvoljenBrojException();
            }
            catch (NumberFormatException ex){
                brojKontrolnihPunktovaPolje.clear();
                greskaFormat=true;
                Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
            }
            catch (NedozvoljenBrojException ex){
                brojKontrolnihPunktovaPolje.clear();
                greskaBroj=true;
                Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
            }
            try
            {
                brojAmbulantnihVozila = Integer.parseInt(brojAmbulantnihVozilaPolje.getText());
                if(brojAmbulantnihVozila>20)
                    throw new NedozvoljenBrojException();
            }
            catch (NumberFormatException ex){
                brojAmbulantnihVozilaPolje.clear();
                greskaFormat=true;
                Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
            }
            catch (NedozvoljenBrojException ex){
                brojAmbulantnihVozilaPolje.clear();
                greskaBroj=true;
                Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
            }
            try
            {
                brojKuca = Integer.parseInt(brojKucaPolje.getText());
                if(brojKuca>20)
                    throw new NedozvoljenBrojException();
                if(brojKuca==0)
                    throw new NulaKucaException();
            }
            catch (NumberFormatException ex){
                brojKucaPolje.clear();
                greskaFormat=true;
                Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
            }
            catch (NedozvoljenBrojException ex){
                brojKucaPolje.clear();
                greskaBroj=true;
                Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
            }
            catch(NulaKucaException ex){
                brojKucaPolje.clear();
                greskaKuca=true;
                Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
            }
            if(greskaBroj || greskaFormat || greskaKuca){
                if(greskaBroj)
                    greskaTekst.setText("Greska! Moguce je unositi samo brojeve koji su manji od 20.");
                if(greskaFormat)
                    greskaTekst.setText("Greska! Nije moguce ostavljati prazna polja ili unositi slova u njih.");
                if(greskaKuca)
                    greskaTekst.setText("Greska! Nije moguce postaviti 0 kuca.");
            }
            else {
                Stage stage = (Stage) startDugme.getScene().getWindow();
                stage.close();
                //KREIRANJE MAPE

                int dimenzijaMatrice = rand.nextInt(15) + 15;
                Main.mapa = new Mapa(dimenzijaMatrice, brojDjece, brojOdraslih, brojStarih, brojKontrolnihPunktova, brojKuca, brojAmbulantnihVozila);

                //KREIRANJE GLAVNOG PROZORA
                try {
                    Stage glavniProzor = new Stage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/CoronaCity/Resursi/glavniProzor.fxml"));
                    Parent root = loader.load();
                    glavniProzorKontroler=loader.getController();
                    glavniProzor.setTitle("CoronaCity application");
                    glavniProzor.setResizable(false);
                    Scene scene = new Scene(root);
                    try{
                        String path = new File("src/CoronaCity/FileWatcher").getAbsolutePath();
                        PrintWriter statistika = new PrintWriter(new FileOutputStream(path + File.separator + "statistika.txt"));
                        statistika.flush();
                        statistika.close();
                    }
                    catch (IOException ex){
                        Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
                    }
                    GridPane gridPane;
                    gridPane = (GridPane) root.getChildrenUnmodifiable().get(0);
                    gridPane.setGridLinesVisible(true);
                    Platform.runLater(() -> {
                        glavniProzorKontroler.terminal.appendText("Dimenzija mape je " + dimenzijaMatrice + ".\n");
                            });
                for (int i = 0; i < dimenzijaMatrice; i++) {
                    RowConstraints row = new RowConstraints();
                    row.setPercentHeight(gridPane.getHeight() / (double) Main.mapa.dimenzijaMape + 5.5D);
                    gridPane.getRowConstraints().add(row);
                    ColumnConstraints column = new ColumnConstraints();
                    column.setPercentWidth(gridPane.getWidth() / (double) Main.mapa.dimenzijaMape + 5.5D);
                    gridPane.getColumnConstraints().add(column);
                }
                glavniProzor.setScene(scene);
                glavniProzor.show();
                }
                catch ( IOException ex){
                    Logger.getLogger(Main.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
                }
            }
        }
    }
}
