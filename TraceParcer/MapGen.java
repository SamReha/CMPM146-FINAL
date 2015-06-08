import java.io.IOException;

public class MapGen {

  public static void main(String[] args) {
  
    String filename;
    try {
      filename = args[0];
      ReadFile file = new ReadFile(filename);
      String[] Trace = file.OpenFile();
      for (int i = 0; i < Trace.length; i++){
        file.Excecute(Trace, i);

      } 
    //} catch(ArrayIndexOutOfBoundsException e) {
     // System.err.println("No File");
    } catch(IOException e) {
      System.err.println(e.getMessage());
    }
  }
}