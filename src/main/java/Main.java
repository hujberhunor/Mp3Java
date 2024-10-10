
public class Main {
    public static void main(String[] args) {
        try{
            FileHandler fh = new FileHandler();
            fh.readDir("src/main/resources/");

            String asd = fh.trackList.get(1).getPath();
            System.out.println(asd);
            
            Player player = new Player(); 
            player.play(asd);


            Thread.sleep(3000);
            player.stop();

        } 
        catch(Exception e){}

    } // end of main method
} // end of Main class
