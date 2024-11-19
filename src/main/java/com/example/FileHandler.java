package com.example;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.swing.plaf.basic.BasicSliderUI.TrackListener;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

/**
 * FileHandler - Minden aminek fileokhoz van köze itt van kezelve
 */
public class FileHandler {

    // Ebben a kollekcióban  tárolom a dalokat
    public ArrayList<Track> trackList = new ArrayList<>();
  
   /**
   * @param path dal elérési útvonala
   * A megadott dir végigpásztázása mp3 fájlok után kutatva 
   * A megtalált mp3 fájlokból egyesével lértehoz egy T //rack objektumot 
   */ 
    public void readDir(String path) throws IOException, UnsupportedTagException,
     InvalidDataException{
        File dir = new File(path);
        File[] listOfFiles = dir.listFiles();
        String name;
        
        if(listOfFiles != null){
            for(int i = 0; i < listOfFiles.length; i++){
                name = listOfFiles[i].getName();

                if(listOfFiles[i].isFile() && name.endsWith(".mp3")){
                    Track track = createTrackFromPath(listOfFiles[i].getAbsolutePath());
                    trackList.add(track);
                }
            } // end of for loop
        } 
        // Error handling
        else System.out.println("Hiba a mappa pásztázásakor");
    } // end of readDir() 


    /**
     * Megadott útvonalból létrehozza a Track objektumot amit utána el tudok 
     * tárolni egy kollekcióban. 
     * Csak azért kell, hogy a readDir()-ben konstruálni tudjak metadatából Tracket 
     * amit pedig kollekcióba tudok elhelyezni
     * @param path - a dal elérési útvonala 
     */
     public Track createTrackFromPath(String path) throws IOException, UnsupportedTagException,
     InvalidDataException {
        // Inicializálás 
        Mp3File mp3File = new Mp3File(path);
        long length = 0;
        String artist = null;
        String title = null;
        String album = null; 
       
        // Megnézem, hogy milyen típusú tagjei vannak
        if(mp3File.hasId3v1Tag()){
            ID3v1 v1Tag = mp3File.getId3v1Tag();
            length = mp3File.getLengthInSeconds();
            artist = v1Tag.getArtist();
            title = v1Tag.getTitle();
            album = v1Tag.getAlbum();
        }
        else if(mp3File.hasId3v2Tag()){
            ID3v2 v2Tag = mp3File.getId3v2Tag();
            length = mp3File.getLengthInSeconds();
            artist = v2Tag.getArtist();
            title = v2Tag.getTitle();
            album = v2Tag.getAlbum();
        }
        else System.err.println("Se v1 se v2 tagjei nincsenek a fájlnak.");
        
        // Trakc konstruálás, lényegi rész
        return new Track(path, title, artist, album, length);
    } // end of createTrackFromPath()

    // /**
    //  * @param pattern keresett cím stringben megadva
    //  * @return megtalált dal/dalok kollekciója
    //  * TODO toLoweCase() 
    //  * Nem teljesen értem mi a retek ez a kód, de lambda
    //  */
    // public ArrayList<Track> search(String pattern) {
    //     return trackList.stream()
    //             .filter(track -> 
    //                 track.getTitle().contains(pattern) 
    //                 || track.getArtist().contains(pattern))
    //             .collect(Collectors.toCollection(ArrayList::new)); 
    // } // end of search()


   /**
    * Search method 
    * @param pattern amit keresek, guiban megadtam
    * @param path az absolute path-ja a vizsgált dir-nek.
    * @return matching trackek
    * @throws IOException
    * @throws UnsupportedTagException
    * @throws InvalidDataException
    */ 
    public ArrayList<Track> searchTracks(String pattern, String path) throws IOException, UnsupportedTagException, InvalidDataException {
        trackList.clear();  
        readDir(path);  

        String lowerCasepattern = pattern.toLowerCase();

        ArrayList<Track> matchingTracks = new ArrayList<>();

        for (Track t : trackList) {
            if (t.getTitle().toLowerCase().contains(lowerCasepattern) || 
                t.getArtist().toLowerCase().contains(lowerCasepattern)) {
                matchingTracks.add(t);
            }
        }

        return matchingTracks; 
    }


    
} // end of fileHandler class