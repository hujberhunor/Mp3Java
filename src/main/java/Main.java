import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.UnsupportedTagException;
import com.mpatric.mp3agic.InvalidDataException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try{
            FileHandler fh = new FileHandler();
            fh.readDir("src/main/resources/");
            
            // for(Track track : fh.trackList){
            //     System.out.println(track.getTitle());
            // }

            String asd = fh.trackList.get(1).getPath();
            System.out.println(asd);
            
            Player pl = new Player();
            pl.play(asd);




        } 
        catch(Exception e){}

    } // end of main method
} // end of Main class
