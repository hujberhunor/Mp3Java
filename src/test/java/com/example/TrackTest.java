package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

public class TrackTest {
    @Test
    void testGetAlbum() throws UnsupportedTagException, InvalidDataException, IOException {
        String song = "src/test/resources/mockData/Linkin-1.mp3";
        FileHandler fh = new FileHandler();
        Track track = fh.createTrackFromPath(song);
        String album = track.getAlbum();

        assertEquals("Meteora", album);

    }
}
