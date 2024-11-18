package com.example;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

import javafx.scene.media.MediaPlayer.Status;

public class GuiActions {
    private FileHandler fileHandler = new FileHandler();
    private AudioHandler audioHandler;
    boolean pressed = false;
    Track selected;


    public Track selectTrack(JFrame frame) throws UnsupportedTagException, InvalidDataException, IOException {
        JFileChooser fileChooser = new JFileChooser();

        // Set the current directory to the application's working directory
        String currentDirectory = System.getProperty("user.dir");
        fileChooser.setCurrentDirectory(new File(currentDirectory));

        // Set a file filter for MP3 files
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("MP3 Files", "mp3"));

        // Open the file chooser dialog and get the user's selection
        int result = fileChooser.showOpenDialog(frame);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            
            // Assuming 'fileHandler' is already initialized and used to create a Track object from the file path
            selected = fileHandler.createTrackFromPath(selectedFile.getAbsolutePath());

            // Init audiohandler so i can call pause and play
            audioHandler = new AudioHandler(selected);

            return selected;
        }
        
        return null;
    }

    public void playPressed(){
        if (!pressed) {
            audioHandler.play();
            pressed = true;
        }
        else {
            audioHandler.pause();
            pressed = false;
        }
    }

    public void fillArtist(JTextField field){
        if(selected != null){
             String artist = selected.getArtist();
             field.setText(artist);
             System.out.println("Artist filed filled");
        }
        else field.setText("NA");
   }

    public void fillTitle(JTextField field){
        if(selected != null){
             String artist = selected.getTitle();
             field.setText(artist);
             System.out.println("Titke filed filled");
        }
        else field.setText("NA");
   }

    public void fillStatus(JTextField field){
        if(selected != null){
             String status = audioHandler.getMediaPlayer().getStatus().toString(); 
             field.setText(status);
             System.out.println("Status field filled");
        }
        else field.setText("NA");
    }

    public void fillProgress(JTextField field){
        if(selected != null){
            long len = selected.getLength();
            int curr = (int) audioHandler.getMediaPlayer().getCurrentTime().toSeconds();
            field.setText(curr + "/" + len);
            System.out.println("Length of the track: " + len);
        }
        else field.setText("NA");
    }

    

}
