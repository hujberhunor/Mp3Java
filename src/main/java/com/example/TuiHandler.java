package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.*;
import java.awt.*;


public class TuiHandler {
    private AudioHandler audioHandler;

    public TuiHandler(AudioHandler audioHandler) {
        this.audioHandler = audioHandler;
    }

    public void start() {
        System.out.println("Type 'play', 'pause', 'resume', or 'exit' to control the MP3 player.");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String input;
            while (!(input = reader.readLine()).equals("exit")) {
                switch (input.toLowerCase()) {
                    case "play":
                        audioHandler.play();
                        break;
                    case "pause":
                        audioHandler.pause();
                        break;
                    default:
                        System.out.println("Unknown command. Type 'play', 'pause' or 'exit'.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
