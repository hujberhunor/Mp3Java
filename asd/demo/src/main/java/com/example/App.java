package com.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class App extends Application {

    private MediaPlayer mediaPlayer;

    @Override
    public void start(Stage stage) {
        String pathToMp3 = "/home/i3hunor/Suli/Prog3/nagyHF/Fasz/asd/demo/src/main/resources/03-LinkinPark-SomewhereIBelong.mp3"; // Update with the correct path
        Media media = new Media(new File(pathToMp3).toURI().toString());
        mediaPlayer = new MediaPlayer(media);

        System.out.println("Type 'play', 'pause', 'resume', or 'exit' to control the MP3 player.");

        new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
                String input;
                while (!(input = reader.readLine()).equals("exit")) {
                    switch (input.toLowerCase()) {
                        case "play":
                            mediaPlayer.play();
                            System.out.println("Playing...");
                            break;
                        case "pause":
                            mediaPlayer.pause();
                            System.out.println("Paused.");
                            break;
                        case "resume":
                            mediaPlayer.play();
                            System.out.println("Resumed.");
                            break;
                        default:
                            System.out.println("Unknown command. Type 'play', 'pause', 'resume', or 'exit'.");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
