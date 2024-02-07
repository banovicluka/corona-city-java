package CoronaCity;

import CoronaCity.Mapa.Mapa;
import CoronaCity.SistemZaNadzor.Nadzornik;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.*;


public class Main extends Application {

    public static Mapa mapa;
    public static long vrijemePokretanjaAplikacije = System.currentTimeMillis();
    public static boolean simulacijaZavrsena=false;
    public static Nadzornik nadzornik= new Nadzornik();
    public static Handler handler;
    public static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    {
        try {
            handler = new FileHandler("Main.log");
            LOGGER.addHandler(handler);
            LOGGER.setUseParentHandlers(false);
            handler.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    SimpleDateFormat logTime = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
                    Calendar cal = new GregorianCalendar();
                    cal.setTimeInMillis(record.getMillis());
                    return record.getLevel()
                            + logTime.format(cal.getTime())
                            + " || "
                            + record.getSourceClassName().substring(
                            record.getSourceClassName().lastIndexOf(".")+1,
                            record.getSourceClassName().length())
                            + "."
                            + record.getSourceMethodName()
                            + "() : "
                            + record.getMessage() + "\n";
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void start(Stage pocetniProzor) throws Exception{
        try{
            FileOutputStream logFile = new FileOutputStream(new File("main.log"));
            logFile.flush();
            logFile.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        Parent root = FXMLLoader.load(getClass().getResource("Resursi/pocetniProzor.fxml"));
        pocetniProzor.setTitle("CoronaCity application");
        pocetniProzor.setScene(new Scene(root));
        pocetniProzor.setResizable(false);
        pocetniProzor.show();
    }


    public static void main(String[] args) {

        launch(args);

    }
}
