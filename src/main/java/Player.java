import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Player {

    // public void playMp3(String songPath) {
    //     // Ensure that the file path is correct and the file exists
    //     File file = new File(songPath);
    //     if (!file.exists()) {
    //         System.err.println("File not found: " + songPath);
    //         return;
    //     }

    //     // Create a Media object
    //     Media hit = new Media(file.toURI().toString()); // Ensure the file URI is correct

    //     // Create a MediaPlayer object
    //     MediaPlayer mediaPlayer = new MediaPlayer(hit);
    //     mediaPlayer.play(); // Start playing the media
    // }

    public void play(String path){
        String bip = path;
Media hit = new Media(new File(bip).toURI().toString());
MediaPlayer mediaPlayer = new MediaPlayer(hit);
mediaPlayer.play();
    }
}

