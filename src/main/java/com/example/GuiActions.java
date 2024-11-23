package com.example;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

/**
 * Minden guiHandlerben helyet foglaló 
 * gombnak és fieldnek itt vannak definiálva 
 * a listenerjei és azok metódusai
 */
public class GuiActions {
    private FileHandler fileHandler = new FileHandler();
    private AudioHandler audioHandler;
    boolean pressed = false;
    Track selected;

    /**
     * A dal/file kiválasztásáért felel
     * @param frame
     * @return selected track
     * @throws UnsupportedTagException
     * @throws InvalidDataException
     * @throws IOException
     */
    public Track selectTrack(JFrame frame) throws UnsupportedTagException, InvalidDataException, IOException {
        JFileChooser fileChooser = new JFileChooser();

        String currentDirectory = System.getProperty("user.dir"); /// curr directoryt nyitja meg
        fileChooser.setCurrentDirectory(new File(currentDirectory));

        /// csak mp3 fájlokat látsz
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("MP3 Files", "mp3"));

        int result = fileChooser.showOpenDialog(frame);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            
            selected = fileHandler.createTrackFromPath(selectedFile.getAbsolutePath());

            audioHandler = new AudioHandler(selected);

            fileHandler.write(audioHandler); // szerializáció
           
            return selected;
        }
        
        return null;
    }

    public void selectedFromSearch(Track track){
        selected = track;
        audioHandler = new AudioHandler(track);
        fileHandler.write(audioHandler); // szerializáció
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

    public void fillAlbum(JTextField field){
        if(selected != null){
             String album = selected.getAlbum();
             field.setText(album);
             System.out.println("Album filed filled");
        }
        else field.setText("NA");
   }

    public void fillProgress(JTextField field){
        if(selected != null){
            long len = selected.getLength();
            int curr = (int) audioHandler.getMediaPlayer().getCurrentTime().toSeconds();
            field.setText(curr + "/" + len);
            // System.out.println("Length of the track: " + len);
        }
        else field.setText("NA");
    }

     public String fillStatus(){
        if(selected != null){
             String status = audioHandler.getMediaPlayer().getStatus().toString(); 
             System.out.println("Status filled");
             return status;
        }
        return "Not selected";
    }

    public ArrayList<Track> searchTrack(String pattern) throws UnsupportedTagException, InvalidDataException, IOException {
        return fileHandler.searchTracks(pattern, "/home/i3hunor/Suli/Prog3/nagyHF/Fasz/mp3java/src/main/resources");
    }


}
