package CoronaCity.Kontroleri;

import CoronaCity.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class statistickiPodaciProzorController {
    public ChoiceBox izborStatistike;
    public Button preuzmiIzvjestajDugme;
    public HBox prostorGrafika;

    public void izborStatistikeClicked(MouseEvent mouseEvent) {
        izborStatistike.show();
    }

    @FXML
    public void initialize(){
        String[] vrsteStatistike = { "Statisticki podaci o trenutnom broju zarazenih",
                "Statisticki podaci o trenutnom broju oporavljenih"};
        izborStatistike.setItems(FXCollections.observableArrayList(vrsteStatistike));
        izborStatistike.setValue(vrsteStatistike[0]);

        int brojZarazenihMuskaraca = 0;
        int brojZarazenihZena = 0;
        int brojZarazenihDjece = 0;
        int brojZarazenihOdraslih = 0;
        int brojZarazenihStarijih = 0;
        prostorGrafika.getChildren().clear();
        final NumberAxis xOsa = new NumberAxis();
        final NumberAxis yOsa = new NumberAxis();
        LineChart<Number,Number> linijskiGrafikZarazenih =
                new LineChart<Number,Number>(xOsa,yOsa);
        linijskiGrafikZarazenih.setTitle("Broj zarazenih");
        XYChart.Series series = new XYChart.Series();;
        try{
            String path = new File("src/CoronaCity/FileWatcher").getAbsolutePath();
            BufferedReader statistika = new BufferedReader(new FileReader(new File(path + File.separator + "statistika.txt")));
            String linija;
            int x = 0;
            int y = 0;
            while((linija = statistika.readLine()) != null){
                if(linija.startsWith("+")){
                    x++;
                    y++;
                    if (linija.split(" ")[1].startsWith("dijete"))
                        brojZarazenihDjece++;
                    if (linija.split(" ")[1].startsWith("odrasli"))
                        brojZarazenihOdraslih++;
                    if (linija.split(" ")[1].startsWith("stari"))
                        brojZarazenihStarijih++;
                    if ("muski,".equals(linija.split(" ")[6]))
                        brojZarazenihMuskaraca++;
                    if ("zenski,".equals(linija.split(" ")[6]))
                        brojZarazenihZena++;

                }
                if(linija.startsWith("-")){
                    x++;
                    y--;
                    if (linija.split(" ")[1].startsWith("dijete"))
                        brojZarazenihDjece--;
                    if (linija.split(" ")[1].startsWith("odrasli"))
                        brojZarazenihOdraslih--;
                    if (linija.split(" ")[1].startsWith("stari"))
                        brojZarazenihStarijih--;
                    if ("muski,".equals(linija.split(" ")[6]))
                        brojZarazenihMuskaraca--;
                    if ("zenski,".equals(linija.split(" ")[6]))
                        brojZarazenihZena--;
                }
                //populating the series with data
                series.getData().add(new XYChart.Data(x, y));
            }
            linijskiGrafikZarazenih.getData().add(series);
            linijskiGrafikZarazenih.setTitle("Broj zarazenih");
            linijskiGrafikZarazenih.setLegendVisible(false);
            statistika.close();
        }
        catch(IOException e){
            Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
        }
        prostorGrafika.getChildren().add(linijskiGrafikZarazenih);

        ObservableList<PieChart.Data> podaciZaGrafikPoDobi =
                FXCollections.observableArrayList(
                        new PieChart.Data("Djeca", brojZarazenihDjece),
                        new PieChart.Data("Odrasli", brojZarazenihOdraslih),
                        new PieChart.Data("Stariji", brojZarazenihStarijih));
        PieChart statistikaPoDobiGrafik = new PieChart(podaciZaGrafikPoDobi);
        statistikaPoDobiGrafik.setTitle("Trenutan broj zarazenih u odnosu na dob");
        prostorGrafika.getChildren().add(statistikaPoDobiGrafik);

        ObservableList<PieChart.Data> podacuZaGrafikPoPolu =
                FXCollections.observableArrayList(
                        new PieChart.Data("Musko", brojZarazenihMuskaraca),
                        new PieChart.Data("Zensko", brojZarazenihZena));
        PieChart statistikaPoPoluGrafik = new PieChart(podacuZaGrafikPoPolu);
        statistikaPoPoluGrafik.setTitle("Trenutan broj zarazenih u odnosu na pol");
        prostorGrafika.getChildren().add(statistikaPoPoluGrafik);

        izborStatistike.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue ov, Number value, Number new_value)
            {
                if("Statisticki podaci o trenutnom broju zarazenih".equals(vrsteStatistike[new_value.intValue()])){
                    int brojZarazenihMuskaraca = 0;
                    int brojZarazenihZena = 0;
                    int brojZarazenihDjece = 0;
                    int brojZarazenihOdraslih = 0;
                    int brojZarazenihStarijih = 0;
                    prostorGrafika.getChildren().clear();
                    final NumberAxis xOsa = new NumberAxis();
                    final NumberAxis yOsa = new NumberAxis();
                    LineChart<Number,Number> linijskiGrafikZarazenih =
                            new LineChart<Number,Number>(xOsa,yOsa);
                    linijskiGrafikZarazenih.setTitle("Broj zarazenih");
                    linijskiGrafikZarazenih.setLegendVisible(false);
                    XYChart.Series series = new XYChart.Series();;
                    try{
                        String path = new File("src/CoronaCity/FileWatcher").getAbsolutePath();
                        BufferedReader  statistika = new BufferedReader(new FileReader(path + File.separator + "statistika.txt"));
                        String linija;
                        int x = 0;
                        int y = 0;
                        while((linija = statistika.readLine()) != null){
                            if(linija.startsWith("+")){
                                x++;
                                y++;
                                if (linija.split(" ")[1].startsWith("dijete"))
                                    brojZarazenihDjece++;
                                if (linija.split(" ")[1].startsWith("odrasli"))
                                    brojZarazenihOdraslih++;
                                if (linija.split(" ")[1].startsWith("stari"))
                                    brojZarazenihStarijih++;
                                if ("muski,".equals(linija.split(" ")[6]))
                                    brojZarazenihMuskaraca++;
                                if ("zenski,".equals(linija.split(" ")[6]))
                                    brojZarazenihZena++;

                            }
                            if(linija.startsWith("-")){
                                x++;
                                y--;
                                if (linija.split(" ")[1].startsWith("dijete"))
                                    brojZarazenihDjece--;
                                if (linija.split(" ")[1].startsWith("odrasli"))
                                    brojZarazenihOdraslih--;
                                if (linija.split(" ")[1].startsWith("stari"))
                                    brojZarazenihStarijih--;
                                if ("muski,".equals(linija.split(" ")[6]))
                                    brojZarazenihMuskaraca--;
                                if ("zenski,".equals(linija.split(" ")[6]))
                                    brojZarazenihZena--;
                            }
                            //populating the series with data
                            series.getData().add(new XYChart.Data(x, y));
                        }
                        linijskiGrafikZarazenih.getData().add(series);
                        linijskiGrafikZarazenih.setTitle("Broj zarazenih");
                        statistika.close();

                    }
                    catch(IOException e){
                        Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
                    }
                    prostorGrafika.getChildren().add(linijskiGrafikZarazenih);

                    ObservableList<PieChart.Data> podaciZaGrafikPoDobi =
                            FXCollections.observableArrayList(
                                    new PieChart.Data("Djeca", brojZarazenihDjece),
                                    new PieChart.Data("Odrasli", brojZarazenihOdraslih),
                                    new PieChart.Data("Stariji", brojZarazenihStarijih));
                    PieChart statistikaPoDobiGrafik = new PieChart(podaciZaGrafikPoDobi);
                    statistikaPoDobiGrafik.setTitle("Trenutan broj zarazenih u odnosu na dob");
                    prostorGrafika.getChildren().add(statistikaPoDobiGrafik);

                    ObservableList<PieChart.Data> podacuZaGrafikPoPolu =
                            FXCollections.observableArrayList(
                                    new PieChart.Data("Musko", brojZarazenihMuskaraca),
                                    new PieChart.Data("Zensko", brojZarazenihZena));
                    PieChart statistikaPoPoluGrafik = new PieChart(podacuZaGrafikPoPolu);
                    statistikaPoPoluGrafik.setTitle("Trenutan broj zarazenih u odnosu na pol");
                    prostorGrafika.getChildren().add(statistikaPoPoluGrafik);
                }
                if("Statisticki podaci o trenutnom broju oporavljenih".equals(vrsteStatistike[new_value.intValue()])){
                    int brojOporavljenihMuskaraca = 0;
                    int brojOporavljenihZena = 0;
                    int brojOporavljenihDjece = 0;
                    int brojOporavljenihOdraslih = 0;
                    int brojOporavljenihStarijih = 0;
                    prostorGrafika.getChildren().clear();
                    final NumberAxis xOsa = new NumberAxis();
                    final NumberAxis yOsa = new NumberAxis();
                    LineChart<Number,Number> linijskiGrafikOporavljenih =
                            new LineChart<Number,Number>(xOsa,yOsa);
                    linijskiGrafikOporavljenih.setTitle("Broj oporavljenih");
                    linijskiGrafikOporavljenih.setLegendVisible(false);
                    XYChart.Series series = new XYChart.Series();;
                    try{
                        String path = new File("src/CoronaCity/FileWatcher").getAbsolutePath();
                        BufferedReader  statistika = new BufferedReader(new FileReader(path + File.separator + "statistika.txt"));
                        String linija;
                        int x = 0;
                        int y = 0;
                        while((linija = statistika.readLine()) != null){
                            if(linija.startsWith("-")){
                                x++;
                                y++;
                                if (linija.split(" ")[1].startsWith("dijete"))
                                    brojOporavljenihDjece++;
                                if (linija.split(" ")[1].startsWith("odrasli"))
                                    brojOporavljenihOdraslih++;
                                if (linija.split(" ")[1].startsWith("stari"))
                                    brojOporavljenihStarijih++;
                                if ("muski,".equals(linija.split(" ")[6]))
                                    brojOporavljenihMuskaraca++;
                                if ("zenski,".equals(linija.split(" ")[6]))
                                    brojOporavljenihZena++;
                            }
                            //populating the series with data
                            series.getData().add(new XYChart.Data(x, y));
                        }
                        linijskiGrafikOporavljenih.getData().add(series);
                        statistika.close();

                    }
                    catch(IOException e){
                        Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
                    }
                    prostorGrafika.getChildren().add(linijskiGrafikOporavljenih);

                    ObservableList<PieChart.Data> podaciZaGrafikPoDobi =
                            FXCollections.observableArrayList(
                                    new PieChart.Data("Djeca", brojOporavljenihDjece),
                                    new PieChart.Data("Odrasli", brojOporavljenihOdraslih),
                                    new PieChart.Data("Stariji", brojOporavljenihStarijih));
                    PieChart statistikaPoDobiGrafik = new PieChart(podaciZaGrafikPoDobi);
                    statistikaPoDobiGrafik.setTitle("Trenutni broj oporavljenih u odnosu na dob");
                    prostorGrafika.getChildren().add(statistikaPoDobiGrafik);

                    ObservableList<PieChart.Data> podaciZaGrafikPoPolu =
                            FXCollections.observableArrayList(
                                    new PieChart.Data("Musko", brojOporavljenihMuskaraca),
                                    new PieChart.Data("Zensko", brojOporavljenihZena));
                    PieChart statistikaPoPoluGrafik = new PieChart(podaciZaGrafikPoPolu);
                    statistikaPoPoluGrafik.setTitle("Trenutan broj oporavljenih u odnosu na pol");
                    prostorGrafika.getChildren().add(statistikaPoPoluGrafik);
                }
            }
        });

    }

    public void preuzmiIzvjestajClicked(MouseEvent mouseEvent) {
        if("Statisticki podaci o trenutnom broju zarazenih".equals(izborStatistike.getValue())){
            if(prostorGrafika.getChildren().size()>2){
                LineChart<Number, Number> linijskiGrafikOporavljenih = (LineChart<Number, Number>)prostorGrafika.getChildren().get(0);
                PieChart statistikaPoDobi = (PieChart)prostorGrafika.getChildren().get(1);
                PieChart statistikaPoPolu = (PieChart)prostorGrafika.getChildren().get(2);
                try{
                    String path = new File("src/CoronaCity/Izvjestaji").getAbsolutePath();
                    PrintWriter brojZarazenihCSV = new PrintWriter(new File(path + File.separator + "brojZarazenih.csv"));
                    PrintWriter trenutanBrojZarazenihPoDobiCSV = new PrintWriter(new File(path + File.separator + "trenutanBrojZarazenihPoDobi.csv"));
                    PrintWriter trenutanBrojZarazenihPoPoluCSV = new PrintWriter(new File(path + File.separator + "trenutanBrojZarazenihPoPolu.csv"));

                    brojZarazenihCSV.write("broj zarazenih");
                    for(XYChart.Data trenutanBrojZarazenih : linijskiGrafikOporavljenih.getData().get(0).getData())
                        brojZarazenihCSV.write(", " + "(" + trenutanBrojZarazenih.getXValue() + "-" + trenutanBrojZarazenih.getYValue() + ")");
                    brojZarazenihCSV.close();

                    trenutanBrojZarazenihPoDobiCSV.write("broj zarazenih po dobi");
                    for(PieChart.Data podaci : statistikaPoDobi.getData())
                    {
                        trenutanBrojZarazenihPoDobiCSV.write(", " + podaci.getName() + ", " + (int)podaci.getPieValue());
                    }
                    trenutanBrojZarazenihPoDobiCSV.close();

                    trenutanBrojZarazenihPoPoluCSV.write("broj zarazenih po polu");
                    for(PieChart.Data podaci : statistikaPoPolu.getData()){
                        trenutanBrojZarazenihPoPoluCSV.write(", " + podaci.getName() + ", " + (int)podaci.getPieValue());
                    }
                    trenutanBrojZarazenihPoPoluCSV.close();

                }
                catch(IOException e){
                    Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
                }
            }
        }
        if("Statisticki podaci o trenutnom broju oporavljenih".equals(izborStatistike.getValue())){
            if(prostorGrafika.getChildren().size()>2){
                LineChart<Number, Number> linijskiGrafikOporavljenih = (LineChart<Number, Number>)prostorGrafika.getChildren().get(0);
                PieChart statistikaPoDobi = (PieChart)prostorGrafika.getChildren().get(1);
                PieChart statistikaPoPolu = (PieChart)prostorGrafika.getChildren().get(2);
                try{
                    String path = new File("src/CoronaCity/Izvjestaji").getAbsolutePath();
                    PrintWriter brojOporavljenihCSV = new PrintWriter(new File(path + File.separator + "brojOporavljenih.csv"));
                    PrintWriter trenutanBrojOporavljenihPoDobiCSV = new PrintWriter(new File(path + File.separator + "trenutanBrojOporavljenihPoDobi.csv"));
                    PrintWriter trenutanBrojOporavljenihPoPoluCSV = new PrintWriter(new File(path + File.separator + "trenutanBrojOporavljenihPoPolu.csv"));

                    brojOporavljenihCSV.write("broj oporavljenih");
                    for(XYChart.Data trenutanBrojZarazenih : linijskiGrafikOporavljenih.getData().get(0).getData())
                        brojOporavljenihCSV.write(", " + "(" + trenutanBrojZarazenih.getXValue() + "-" + trenutanBrojZarazenih.getYValue() + ")");
                    brojOporavljenihCSV.close();

                    trenutanBrojOporavljenihPoDobiCSV.write("broj oporavljenih po dobi");
                    for(PieChart.Data podaci : statistikaPoDobi.getData())
                    {
                        trenutanBrojOporavljenihPoDobiCSV.write(", " + podaci.getName() + ", " + (int)podaci.getPieValue());
                    }
                    trenutanBrojOporavljenihPoDobiCSV.close();

                    trenutanBrojOporavljenihPoPoluCSV.write("broj oporavljenih po polu");
                    for(PieChart.Data podaci : statistikaPoPolu.getData()){
                        trenutanBrojOporavljenihPoPoluCSV.write(", " + podaci.getName() + ", " + (int)podaci.getPieValue());
                    }
                    trenutanBrojOporavljenihPoPoluCSV.close();

                }
                catch(IOException e){
                   Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
                }
            }
        }
    }
}
