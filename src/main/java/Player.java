import java.io.FileInputStream;
import java.io.IOException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class Player {

    /*
     * MP3 fáj lejátszásért felelős method
     * Mainben hívva. 
     */
    public void play(String path) {
        try {
            FileInputStream fis = new FileInputStream(path);
            AdvancedPlayer player = new AdvancedPlayer(fis);
            player.play();
            fis.close();
        } 
        // Error handling
        catch (JavaLayerException | IOException e) {
            e.printStackTrace();
        }
    }

} // end of Player Class


