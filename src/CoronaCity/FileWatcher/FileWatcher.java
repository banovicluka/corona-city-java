package CoronaCity.FileWatcher;

import CoronaCity.Kontroleri.pocetniProzorController;
import CoronaCity.Main;
import javafx.application.Platform;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

public class FileWatcher extends Thread {

    @Override
    public void run() {
        try{
            WatchService watcher = FileSystems.getDefault().newWatchService();
            String path = new File("src/CoronaCity/FileWatcher").getAbsolutePath();
            Path direktorijum = Paths.get(path);
            direktorijum.register(watcher,ENTRY_MODIFY);
            while (true){
                WatchKey key;
                try{
                    key=watcher.take();
                }
                catch (InterruptedException ex){
                    return;
                }
                for(WatchEvent<?> event : key.pollEvents()){
                    WatchEvent.Kind<?> kind=event.kind();
                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    Path fileName = ev.context();
                    if(fileName.toString().trim().endsWith(".txt") && kind.equals(ENTRY_MODIFY)){
                        List<String> content= Files.readAllLines(direktorijum.resolve(fileName));
                        if(content.size()>0 && content.get(content.size()-1).startsWith("+"))
                            Platform.runLater(() ->{
                               pocetniProzorController.glavniProzorKontroler.brojZarazenih.setText(Integer.toString(Main.mapa.brojZarazenih));
                            });
                        if(content.size()>0 && content.get(content.size()-1).startsWith("-")){
                            Platform.runLater(() ->{
                                pocetniProzorController.glavniProzorKontroler.brojZarazenih.setText(Integer.toString(Main.mapa.brojZarazenih));
                            });
                            Platform.runLater(() ->{
                                pocetniProzorController.glavniProzorKontroler.brojOporavljenih.setText(Integer.toString(Main.mapa.brojOporavljenih));
                            });
                        }

                    }
                }
                boolean valid = key.reset();
                if(!valid){
                    break;
                }
            }
        } catch(IOException ex){
            ex.printStackTrace();
        }

    }
}
