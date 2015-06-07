import java.io.IOException;

public class MapGen {

  public static void main(String[] args) {
  
    String filename;
    try {
      filename = args[0];
      ReadFile file = new ReadFile(filename);
      String[] Trace = file.OpenFile();
      for (int i = 0; i < Trace.length; i++){
        //System.out.println(Trace[i]);
        String type = Trace[i];
        switch (type) {
          case "room start" : // build room, place player
                              System.out.println("Start");
                              break;
          case "room keylock" : //build room, place door, place key
                                System.out.println("keylock");
                                break;
          case "room maze" : //build room, place maze
                             System.out.println("maze");
                             break;
          case "room no_obstacle" : //build room
                                    System.out.println("no obstacle");
                                    break;
          case "room end" : //build room
                            System.out.println("End");
                            break;
        }
      } 
    } catch(ArrayIndexOutOfBoundsException e) {
      System.err.println("No File");
    } catch(IOException e) {
      System.err.println(e.getMessage());
    }
  }
}