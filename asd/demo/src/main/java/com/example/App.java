package com.example;

import java.io.File;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class App extends Application {

    private MediaPlayer mediaPlayer;

    @Override
    public void start(Stage stage) {
        String pathToMp3 = "/home/i3hunor/Suli/Prog3/nagyHF/Fasz/asd/demo/src/main/resources/03. Linkin Park  Somewhere I Belong.mp3"; // Specify your MP3 file path here
        Media media = new Media(new File(pathToMp3).toURI().toString());
        mediaPlayer = new MediaPlayer(media);

        // StackPane root = new StackPane();
        // Scene scene = new Scene(root, 640, 480);
        // stage.setScene(scene);
        // stage.show();

        // Example controls
        mediaPlayer.play(); // Play the audio
    }

    public static void main(String[] args) {
        launch();
    }
}
