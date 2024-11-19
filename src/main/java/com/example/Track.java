package com.example;

public class Track {
    String path;    /// Absolute path that is set by the FileHandler.readDir()
    String title;
    String artis;
    String album;
    long length;    /// Sec-ben megadott dal hossz 

    /// Konstruktor
    Track(String path, String title, String artist, String album, long length){
        this.path = path;
        this.title = title;
        this.artis = artist;
        this.album = album;
        this.length = length;
    }

    /// Paraméter nélküli konstruktor
    Track(){
        this.path = null;
        this.title = null;
        this.artis = null;
        this.album = null;
        this.length = 0;
    }

    // Getterek

    /**
     * The fileHandler class Readdi method sets this attribute
     * @return path absolute path to the song 
     */
    public String getPath(){
        return path;
    }

    public String getTitle(){
        return title;
    }

    public String getArtist(){
        return artis;
    }

    public String getAlbum(){
        return album;
    }

    public long getLength(){
        return length;
    }

    @Override
    public String toString() {
        return "title=" + title + "', artist='" + artis + " ";
    }



} // end of Track class


