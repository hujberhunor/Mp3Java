import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.UnsupportedTagException;
import com.mpatric.mp3agic.InvalidDataException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        System.out.println("main");
        //String songPath = "/home/i3hunor/Suli/Prog3/nagyHF/mp3project/Billy Joel Uptown Girl Official Video.mp3";
        String songPath = "/home/i3hunor/Suli/Prog3/nagyHF/mp3project/01. Linkin Park  Foreword.mp3";
        try{
            Mp3File mp3File = new Mp3File(songPath);
            System.out.println("Length of this mp3 is: " + mp3File.getLengthInSeconds() + " seconds");
            System.out.println("Has ID3v1 tag?: " + (mp3File.hasId3v2Tag() ? "YES" : "NO"));
            ID3v1 id3v1Tag = mp3File.getId3v2Tag();
            System.out.println("Track: " + id3v1Tag.getTrack());
            System.out.println("Artist: " + id3v1Tag.getArtist());

        } 
        catch(Exception e){
            
        }

    }
}
