package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

import javafx.application.Platform;

public class FileHandlerTest {
    
   @BeforeAll
static void setupJavaFx() {
    if (!Platform.isImplicitExit()) {
        Platform.startup(() -> {});
    }
}

    
    /**
     * Azért van előbb mert a readdir ezt a fv-t futtatja
     * és a pásztázott fileokat trackké varátsolja
     * @throws Exception
     */
    @Test
    void testCreateTrackFromPath() throws Exception {
        String song = "src/test/resources/mockData/Linkin-1.mp3";
        FileHandler fh = new FileHandler();
        Track track = fh.createTrackFromPath(song);
        String artist = track.getArtist();
        
        assertNotNull(track);
        assertEquals("Linkin Park", artist);
    }

    @Test
    void testReadDir() throws UnsupportedTagException, InvalidDataException, IOException {
        String dir = "src/test/resources/mockData/";
        FileHandler fh = new FileHandler();
        fh.readDir(dir);
    
        assertEquals(2, fh.trackList.size());
        assertTrue(fh.trackList.stream().anyMatch(track -> track.getPath().endsWith("Linkin-1.mp3")));
        assertTrue(fh.trackList.stream().anyMatch(track -> track.getPath().endsWith("Linkin-2.mp3")));
    }

    @Test
    void testSearchTracks() throws UnsupportedTagException, InvalidDataException, IOException {
        String pattern = "Linkin";
        String path = "src/test/resources/mockData/";
        FileHandler fh = new FileHandler();
        ArrayList<Track> list;
        list = fh.searchTracks(pattern, path);

        assertNotNull(list);
        assertEquals(2, list.size());
        assertTrue(list.stream().anyMatch(track -> track.getArtist().contains("Linkin")));
    }

    @Test
    void testWriteAndReadSerialization() throws UnsupportedTagException, InvalidDataException, IOException {
        FileHandler fileHandler = new FileHandler();
        String testFile = "serialTest.json";

        String songPath = "src/test/resources/mockData/Linkin-1.mp3";
        FileHandler fh = new FileHandler();
        Track track = fh.createTrackFromPath(songPath); 
        AudioHandler ah = new AudioHandler(track); 
        
        fileHandler.write(ah, testFile);

        ArrayList<Track> readTracks = fileHandler.read(testFile);

        assertNotNull(readTracks);
        assertEquals(1, readTracks.size());
        assertEquals("Linkin Park", readTracks.get(0).getArtist());
    }

}
