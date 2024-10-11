import java.io.FileInputStream;
import java.io.IOException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;


public class AudioHandler {
    private Player player;
    private Thread playThread;
    private boolean playing = false;
    private boolean paused;
    private int currFrame;
    private Track currTrack;

    /*
     * Handling the playing of an mp3 instance
     * @param track Track instance that carries the path that the player can play
     */
    public void play(Track track) {
        String path = track.getPath(); 
        currTrack = track;
        if (!playing) {
            playing = true; 
            paused = false; 
            currFrame = 0; // Initialize currFrame when starting

            playThread = new Thread(() -> {
                try (FileInputStream fis = new FileInputStream(path)) {
                    player = new Player(fis);
                    // ITT LENNE A CURRFRAME, DE ADVANCED PLAYER KELL HOZZÁ!! Comitolva backupként 
                    player.play(); 
                } catch (JavaLayerException | IOException e) {
                    e.printStackTrace(); 
                } finally {
                    playing = false; 
                    currFrame = 0; // Reset on completion
                }
            });
            playThread.start();
        } else {
            System.out.println("A file is already playing."); 
        }
    } // end of play()

    /*
     * Stops the thread and the player
     * Sets the playing to false
     */
    public void stop() {
        if (playing) {
            if (player != null) {
                player.close(); 
                playThread.interrupt(); 
                player = null;
                playing = false; 
                currFrame = 0; 
            } else {
                System.out.println("Currently nothing is playing."); 
            }
        } else {
            System.out.println("No MP3 file is currently playing."); 
        }
    } // end of stop()

    /*
     * Sets the paused to true
     * Closes the player == pauses the player at the frame
     */
    public void pause() {
        if (playing && !paused) {
            player.close(); 
            paused = true;
        }
    }

    /*
     * Resumes playback from where it was paused
     */
    public void resume() {
        if (paused) {
            paused = false; 
            play(currTrack); // Start playing again from the saved path
        }
    }
} // end of class
