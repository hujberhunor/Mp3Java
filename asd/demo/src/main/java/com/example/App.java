package com.example;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        String pathToMp3 = "/home/i3hunor/Suli/Prog3/nagyHF/Fasz/asd/demo/src/main/resources/03-LinkinPark-SomewhereIBelong.mp3";

        AudioHandler audioHandler = new AudioHandler(pathToMp3);
        TuiHandler tuiHandler = new TuiHandler(audioHandler);

        // Run TUI handler in a separate thread
        new Thread(tuiHandler::start).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
