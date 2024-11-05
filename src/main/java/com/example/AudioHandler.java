package com.example;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioHandler {
    private MediaPlayer mediaPlayer;

    public AudioHandler(Track track) {
        String path = track.getPath();
        Media media = new Media(new File(path).toURI().toString());
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

} // END OF AUDIOHANDLER
