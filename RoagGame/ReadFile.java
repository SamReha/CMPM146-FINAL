//takes in trace, builds map
import java.io.*;
import java.io.FileReader;
import java.io.BufferedReader;

public class ReadFile {
  String path;
  public ReadFile(String file_path){
    path = file_path;
  }
  public String[] OpenFile() throws IOException{
    FileReader read = new FileReader(path);
    BufferedReader textReader = new BufferedReader(read);
    String textData;
    String delim = "[,]";
    
    textData = textReader.readLine();
    textReader.close();
    String[] rawTrace = textData.split(delim);
    String[] strip = new String[rawTrace.length]; 
    String[] trace = new String[rawTrace.length]; 
    for (int i = 0; i < strip.length; i++){
      strip[i] = rawTrace[i].replaceAll("[^a-zA-Z_ ]","");
      trace[i] = strip[i].trim();
    }    
    return trace;
  }
  
}