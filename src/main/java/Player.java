import java.io.FileInputStream;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
public class Player {
    /*
     * TODO
     * Stop()
     * Next()
     * Start/Stop toggle
     * currently in this time of this time
     * setvol up 
     * setvol down
     * forward 5 sec 
     */

    private AdvancedPlayer player;
    private Thread playerThread; 
    /*
     * MP3 fáj lejátszásért felelős method
     * Mainben hívva. 
     */
   public void play(String filepath) {
        try {
            FileInputStream fis = new FileInputStream(filepath);
            player = new AdvancedPlayer(fis);
            playerThread = new Thread(() -> {
                try {
                    player.play();
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
            });
            playerThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop(){
        if(player != null){
            player.close();
        }
    }

} // end of Player Class


