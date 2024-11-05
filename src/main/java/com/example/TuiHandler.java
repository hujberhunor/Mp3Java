package com.example;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class TuiHandler {
    /// AudioHandler instance for calling the play/pause methods on the current track
    private AudioHandler audioHandler;

    // GUI section
    TuiHandler(AudioHandler audioHandler){
        this.audioHandler = audioHandler;
    }

    public void start(){ // init()
        JFrame frame = new JFrame();
        frame.setTitle("Mp3Java");  
        frame.setSize(400, 200);      
        frame.setResizable(false);  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
        // Top panel for displaying artist, title etc
        JPanel topPanel = new JPanel();
        JLabel titleLabel = new JLabel("Title: ");
        JLabel artistLabel = new JLabel("Artist: ");
        JTextField titleField = new JTextField();
        JTextField artistField = new JTextField();

        // Artist
        topPanel.add(artistLabel);
        topPanel.add(artistField);
        // Title
        topPanel.add(titleLabel);
        topPanel.add(titleField);

        // Bottom panel for play/pause and progresBar
        JPanel botPanel = new JPanel();
        JButton butPlay = new JButton("Play/Pause"); // TODO váltson nevet ha meg van nyomva

        botPanel.add(butPlay);

        // Progress bar
        // TODO  + add to frame 

        frame.setLayout(new BorderLayout());
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(botPanel, BorderLayout.SOUTH);
        frame.setVisible(true);  
    }





    // TUI section
    // public TuiHandler(AudioHandler audioHandler) {
    //     this.audioHandler = audioHandler;
    // }

    // public void start() {
    //     System.out.println("Type 'play', 'pause' or 'exit'.");

    //     try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
    //         String input;
    //         while (!(input = reader.readLine()).equals("exit")) {
    //             switch (input.toLowerCase()) {
    //                 case "play":
    //                     audioHandler.play();
    //                     break;
    //                 case "pause":
    //                     audioHandler.pause();
    //                     break;
    //                 default:
    //                     System.out.println("Unknown command. Type 'play', 'pause' or 'exit'.");
    //             }
    //         }
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }
} 
