


public class Track {
    String path;    // Elérési útvonal. Csak ezt tárolom, nem a fájlt!!
    String title;
    String artis;
    String album;
    long length;    // Sec-ben megadott dal hossz 

    // Konstruktor
    Track(String path, String title, String artist, String album, long length){
        this.path = path;
        this.title = title;
        this.artis = artist;
        this.album = album;
        this.length = length;
    }

    // Paraméter nélküli konstruktor
    Track(){
        this.path = null;
        this.title = null;
        this.artis = null;
        this.album = null;
        this.length = 0;
    }

    // Getterek
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

    public long getLength(){
        return length;
    }


} // end of Track class
