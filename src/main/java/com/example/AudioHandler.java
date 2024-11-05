package com.example;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Class that handle every audio related method
 */
public class AudioHandler {
    private MediaPlayer mediaPlayer;

    /**
     * Contructor that initializes the mediaPlayer with a track
     * @param track
     */
    public AudioHandler(Track track) {
        String path = track.getPath();
        Media media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
    }

    /**
     * Calls the play() method on the initialized media player
     * Works as a resume() too
     */
    public void play() {
        mediaPlayer.play();
        System.out.println("Playing...");
    }

    /**
     * Calls pause() methond on the mediaPlayer
     */
    public void pause() {
        mediaPlayer.pause();
        System.out.println("Paused.");
    }

} // END OF AUDIOHANDLER
