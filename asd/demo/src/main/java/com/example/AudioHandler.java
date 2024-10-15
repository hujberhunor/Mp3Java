package com.example;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioHandler {
    private MediaPlayer mediaPlayer;

    public AudioHandler(String pathToMp3) {
        Media media = new Media(new File(pathToMp3).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
    }

    public void play() {
        mediaPlayer.play();
        System.out.println("Playing...");
    }

    public void pause() {
        mediaPlayer.pause();
        System.out.println("Paused.");
    }

    public void resume() {
        mediaPlayer.play();
        System.out.println("Resumed.");
    }

} // END OF AUDIOHANDLER
