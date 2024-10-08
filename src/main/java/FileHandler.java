import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

/**
 * FileHandler
 */
public class FileHandler {
    // Ebben a kollekcióban  tárolom a dalokat
    public ArrayList<Track> trackList = new ArrayList<>();
  
    // A megadott dir végigpásztázása mp3 fájlok után kutatva
    // Majd track létrehozása mindegyikből 
    // A megtalált mp3 fájlokból egyesével lértehoz egy Track objektumot 
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
        } // end of if
        else System.out.println("Hiba a mappa pásztázásakor");
    } // end of readDir() 


    // @param path - a dal elérési útvonala
    // Ebből létrehozza track objektumot
    public Track createTrackFromPath(String path) throws IOException, UnsupportedTagException,
     InvalidDataException {
        //  
        Mp3File mp3File = new Mp3File(path);
        long length = 0;
        String artist = null;
        String title = null;
        String album = null; 
        
        if(mp3File.hasId3v1Tag()){
            ID3v1 v1Tag = mp3File.getId3v1Tag();
            length = mp3File.getLengthInSeconds();
            artist = v1Tag.getArtist();
            title = v1Tag.getTitle();
            album = v1Tag.getAlbum();
        }
        else if(mp3File.hasId3v2Tag()){
            System.out.println("Has v2tag");
            ID3v2 v2Tag = mp3File.getId3v2Tag();
            length = mp3File.getLengthInSeconds();
            artist = v2Tag.getArtist();
            title = v2Tag.getTitle();
            album = v2Tag.getAlbum();
        }
        else System.err.println("Jaj");
        
        // Trakc konstruálás
        Track track = new Track(path, title, artist, album, length);
        return track;
    } // end of createTrackFromPath()
    
} // end of fileHandler class