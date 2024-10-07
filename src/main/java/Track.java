


public class Track {
    String path;
    String title;
    String artis;
    String album;
    int year;

    // Konstruktor
    Track(String path, String title, String artist, String album, int year){
        this.path = path;
        this.title = title;
        this.artis = artist;
        this.album = album;
        this.year = year;
    }

    // Paraméter nélküli konstruktor
    Track(){
        this.path = null;
        this.title = null;
        this.artis = null;
        this.album = null;
        this.year = 0;
    }


    public String getPath(){
        return path;
    }

    public String getTitle(){
        return title;
    }

    public String getArtist(){
        return artis;
    }

    public String album(){
        return album;
    }

    public int getYear(){
        return year;
    }



} // end of Track class
