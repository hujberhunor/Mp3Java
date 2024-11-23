package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JTextField;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

import javafx.application.Platform;

public class GuiActionsTest {
    String song1Path = "src/test/resources/mockData/Linkin-1.mp3";
    String songArtis = "Linkin Park";
    String songAlbum = "Meteora";
    FileHandler fh = new FileHandler();
    GuiActions ga = new GuiActions();

    // Fogalam sincs mi akar ez lenni de működik
    @BeforeAll
    static void setupJavaFx() {
        Platform.startup(() -> {});
    }

    @Test
    void testFillAlbum() throws UnsupportedTagException, InvalidDataException, IOException {
        Track track = fh.createTrackFromPath(song1Path);
        ga.selectedFromSearch(track);

        JTextField albumField = new JTextField();
        ga.fillAlbum(albumField);

        assertEquals(songAlbum, albumField.getText());

    }

    @Test
    void testFillArtist() throws UnsupportedTagException, InvalidDataException, IOException {
        Track track = fh.createTrackFromPath(song1Path);
        ga.selectedFromSearch(track);

        JTextField artistField = new JTextField();
        ga.fillArtist(artistField);

        assertEquals(songArtis, artistField.getText());
    }

    @Test
    void testPlayPressed() throws UnsupportedTagException, InvalidDataException, IOException {
        Track track = fh.createTrackFromPath(song1Path);
        ga.selectedFromSearch(track);

        ga.playPressed();
        assertTrue(ga.pressed);

        ga.playPressed();
        assertFalse(ga.pressed);
    }


    @Test
    void testSearchTrack() throws Exception {
        ArrayList<Track> tracks = ga.searchTrack(songArtis);

        assertFalse(tracks.isEmpty());
        assertTrue(tracks.stream().anyMatch(track -> track.getArtist().equalsIgnoreCase(songArtis)));
    }

    @Test
    void testSelectedFromSearch() throws UnsupportedTagException, InvalidDataException, IOException {
        Track track = fh.createTrackFromPath("src/test/resources/mockData/Linkin-1.mp3");

        ga.selectedFromSearch(track);

        assertEquals(songArtis, track.getArtist());
        assertEquals(songAlbum, track.getAlbum());

    }
}
