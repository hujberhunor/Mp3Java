package com.example;

import java.io.IOException;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

import javafx.application.Application;
import javafx.stage.Stage;
/*
 * MAIN
 * This class conatins the main method
 * 
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws UnsupportedTagException, InvalidDataException, IOException {
        // INICIALIZÁLÁS 
        // String pathToMp3 = "/home/i3hunor/Suli/Prog3/nagyHF/Fasz/asd/demo/src/main/resources/03-LinkinPark-SomewhereIBelong.mp3";
        FileHandler fileHandler = new FileHandler();

        // Beolvassa a megadott dir össze `.mp3` fájlját és kollekcióba rakja őket.
        fileHandler.readDir("/home/i3hunor/Suli/Prog3/nagyHF/Fasz/mp3java/src/main/resources");
        // A kiválasztott lejátszandó track
        Track track = fileHandler.trackList.get(2);

        AudioHandler audioHandler = new AudioHandler(track);
        TuiHandler tuiHandler = new TuiHandler(audioHandler);

        // TUI handler kezeli az inputut és onnan hívja meg a megfelelő play/pause metódusokat
        // Creates a new thread that wll run the tuiHandler
        new Thread(tuiHandler::start).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
