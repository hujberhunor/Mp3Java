package com.example;

import java.io.IOException;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

import javafx.application.Application;
import javafx.stage.Stage;
/**
 * This class conatins the main method
 */
public class App extends Application {

    /**
     * javaFx requeres this start() method to be overwritten
     * This start() called in main.
     * @param stage No idea what is this and how it is works
     */
    @Override
    public void start(Stage stage) throws UnsupportedTagException, InvalidDataException, IOException {
        // INICIALIZÁLÁS 
        // FileHandler fileHandler = new FileHandler();

        // // Beolvassa a megadott dir össze `.mp3` fájlját és kollekcióba rakja őket.
        // fileHandler.readDir("/home/i3hunor/Suli/Prog3/nagyHF/Fasz/mp3java/src/main/resources");
        // // A kiválasztott lejátszandó track
        // Track track = fileHandler.trackList.get(2);

        // AudioHandler audioHandler = new AudioHandler(track);
        // Itt hozom létre a tui handlert !!
        GuiHandler tuiHandler = new GuiHandler();

        // TUI handler kezeli az inputut és onnan hívja meg a megfelelő play/pause metódusokat
        // Creates a new thread that wll run the tuiHandler
        new Thread(tuiHandler::init).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
