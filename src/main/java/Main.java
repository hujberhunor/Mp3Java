import java.io.*;

public class Main {
    public static void main(String[] args) {
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            FileHandler fh = new FileHandler();
            fh.readDir("src/main/resources/");

            Track asd = fh.trackList.get(1);
            AudioHandler plr = new AudioHandler(); 
           
            String input;
            while(true){
                input = reader.readLine();
                System.out.println(input);
                if(input.equals("exit")) System.exit(0);
                if(input.equals("play")) plr.play(asd);
                if(input.equals("stop")) plr.stop();
                if(input.equals("pause")) plr.pause();
                if(input.equals("resume")) plr.resume();
            }
        } 
        catch(Exception e){}

    } // end of main method
} // end of Main class
