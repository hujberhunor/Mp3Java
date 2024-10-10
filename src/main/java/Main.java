import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.io.File;
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
            
            Player player = new Player(); 
            player.play(asd);



        } 
        catch(Exception e){}

    } // end of main method
} // end of Main class
