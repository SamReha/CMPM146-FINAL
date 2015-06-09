import java.io.IOException;

public class MapGen {

  public static void main(String[] args) {
  
    String filename;
    try {
      filename = args[0];
      ReadFile file = new ReadFile(filename);
      String[] Trace = file.OpenFile();
      String delims = "[ ]";
      for (int i = 0; i < Trace.length; i++){
        //file.Excecute(Trace, i);
      String[] coms = Trace[i].split(delims);
    
    
      if(coms[0].equals("start") && coms[1].equals("no_obstacle")){  //No obs start
        //build no obstacle start
        System.out.println("start, no obs");
        if(coms[2].contains(":")){
          coms[2] = coms[2].replaceAll("[^0-9]+","");
          int num = Integer.parseInt(coms[2]);
          //place "num" enemies
        }
        for (int j=3;j<coms.length;j++){
          if(coms[j].contains(":")){
            if(coms[j].contains("treasure:")){
              coms[j] = coms[j].replaceAll("[^0-9]+","");
              int num = Integer.parseInt(coms[j]);
              //place "num" treasure
              System.out.println("t"+num);
            }else if(coms[j].contains("armor:")){
              coms[j] = coms[j].replaceAll("[^0-9]+","");
              int num = Integer.parseInt(coms[j]);
              //place "num" armor
              System.out.println("a"+num);
            }else if(coms[j].contains("weapon:")){
              coms[j] = coms[j].replaceAll("[^0-9]+","");
              int num = Integer.parseInt(coms[j]);
              //place "num" weapon
              System.out.println("w"+num);          
            }
          }
        }
                
        
      }else if(coms[0].equals("start") && coms[1].equals("keylock")){ //keylock start
        //build keylock start
        System.out.println("start, keylock");
        if(coms[2].contains(":")){
          coms[2] = coms[2].replaceAll("[^0-9]+","");
          int num = Integer.parseInt(coms[2]);
          //place "num" enemies
        }
        for (int j=3;j<coms.length;j++){
          if(coms[j].contains(":")){
            if(coms[j].contains("treasure:")){
              coms[j] = coms[j].replaceAll("[^0-9]+","");
              int num = Integer.parseInt(coms[j]);
              //place "num" treasure
              System.out.println("t"+num);
            }else if(coms[j].contains("armor:")){
              coms[j] = coms[j].replaceAll("[^0-9]+","");
              int num = Integer.parseInt(coms[j]);
              //place "num" armor
              System.out.println("a"+num);
            }else if(coms[j].contains("weapon:")){
              coms[j] = coms[j].replaceAll("[^0-9]+","");
              int num = Integer.parseInt(coms[j]);
              //place "num" weapon
              System.out.println("w"+num);          
            }
          }
        }
                
      }else if(coms[0].equals("start") && coms[1].equals("maze")){ //maze start
        //build maze start
        System.out.println("start, maze");
        if(coms[2].contains(":")){
          coms[2] = coms[2].replaceAll("[^0-9]+","");
          int num = Integer.parseInt(coms[2]);
          System.out.println("e"+num);
          //place "num" enemies
        }
        for (int j=3;j<coms.length;j++){
          if(coms[j].contains(":")){
            if(coms[j].contains("treasure:")){
              coms[j] = coms[j].replaceAll("[^0-9]+","");
              int num = Integer.parseInt(coms[j]);
              //place "num" treasure
              System.out.println("t"+num);
            }else if(coms[j].contains("armor:")){
              coms[j] = coms[j].replaceAll("[^0-9]+","");
              int num = Integer.parseInt(coms[j]);
              //place "num" armor
              System.out.println("a"+num);
            }else if(coms[j].contains("weapon:")){
              coms[j] = coms[j].replaceAll("[^0-9]+","");
              int num = Integer.parseInt(coms[j]);
              //place "num" weapon
              System.out.println("w"+num);          
            }
          }
        }
        
      }
      
      
      
      
      else if(coms[0].equals("room") && coms[1].equals("no_obstacle")){ //no obs middle
        //build no obstacle room
        System.out.println("room, no obs");
        if(coms[2].contains(":")){
          coms[2] = coms[2].replaceAll("[^0-9]+","");
          int num = Integer.parseInt(coms[2]);
          //place "num" enemies
        }
        for (int j=3;j<coms.length;j++){
          if(coms[j].contains(":")){
            if(coms[j].contains("treasure:")){
              coms[j] = coms[j].replaceAll("[^0-9]+","");
              int num = Integer.parseInt(coms[j]);
              //place "num" treasure
              System.out.println("t"+num);
            }else if(coms[j].contains("armor:")){
              coms[j] = coms[j].replaceAll("[^0-9]+","");
              int num = Integer.parseInt(coms[j]);
              //place "num" armor
              System.out.println("a"+num);
            }else if(coms[j].contains("weapon:")){
              coms[j] = coms[j].replaceAll("[^0-9]+","");
              int num = Integer.parseInt(coms[j]);
              //place "num" weapon
              System.out.println("w"+num);          
            }
          }
        }        
 
      }else if(coms[0].equals("room") && coms[1].equals("keylock")){  //keylock midde
        //build keylock room
        System.out.println("room, keylock");
        if(coms[2].contains(":")){
          coms[2] = coms[2].replaceAll("[^0-9]+","");
          int num = Integer.parseInt(coms[2]);
          //place "num" enemies
        }
        for (int j=3;j<coms.length;j++){
          if(coms[j].contains(":")){
            if(coms[j].contains("treasure:")){
              coms[j] = coms[j].replaceAll("[^0-9]+","");
              int num = Integer.parseInt(coms[j]);
              //place "num" treasure
              System.out.println("t"+num);
            }else if(coms[j].contains("armor:")){
              coms[j] = coms[j].replaceAll("[^0-9]+","");
              int num = Integer.parseInt(coms[j]);
              //place "num" armor
              System.out.println("a"+num);
            }else if(coms[j].contains("weapon:")){
              coms[j] = coms[j].replaceAll("[^0-9]+","");
              int num = Integer.parseInt(coms[j]);
              //place "num" weapon
              System.out.println("w"+num);          
            }
          }
        }        
        
      }else if(coms[0].equals("room") && coms[1].equals("maze")){  //maze middle
        //build maze room
        System.out.println("room, maze");
        if(coms[2].contains(":")){
          coms[2] = coms[2].replaceAll("[^0-9]+","");
          int num = Integer.parseInt(coms[2]);
          //place "num" enemies
        }
        for (int j=3;j<coms.length;j++){
          if(coms[j].contains(":")){
            if(coms[j].contains("treasure:")){
              coms[j] = coms[j].replaceAll("[^0-9]+","");
              int num = Integer.parseInt(coms[j]);
              //place "num" treasure
              System.out.println("t"+num);
            }else if(coms[j].contains("armor:")){
              coms[j] = coms[j].replaceAll("[^0-9]+","");
              int num = Integer.parseInt(coms[j]);
              //place "num" armor
              System.out.println("a"+num);
            }else if(coms[j].contains("weapon:")){
              coms[j] = coms[j].replaceAll("[^0-9]+","");
              int num = Integer.parseInt(coms[j]);
              //place "num" weapon
              System.out.println("w"+num);          
            }
          }
        }        
      }

      else if(coms[0].equals("end") && coms[1].equals("no_obstacle")){ //no obs end
        //build no obstacle end
        System.out.println("end, no obs");
        if(coms[2].contains(":")){
          coms[2] = coms[2].replaceAll("[^0-9]+","");
          int num = Integer.parseInt(coms[2]);
          System.out.println("e"+num);
          //place "num" enemies
        }
        for (int j=3;j<coms.length;j++){
          if(coms[j].contains(":")){
            if(coms[j].contains("treasure:")){
              coms[j] = coms[j].replaceAll("[^0-9]+","");
              int num = Integer.parseInt(coms[j]);
              //place "num" treasure
              System.out.println("t"+num);
            }else if(coms[j].contains("armor:")){
              coms[j] = coms[j].replaceAll("[^0-9]+","");
              int num = Integer.parseInt(coms[j]);
              //place "num" armor
              System.out.println("a"+num);
            }else if(coms[j].contains("weapon:")){
              coms[j] = coms[j].replaceAll("[^0-9]+","");
              int num = Integer.parseInt(coms[j]);
              //place "num" weapon
              System.out.println("w"+num);          
            }
          }
        }        
        
      }else if(coms[0].equals("end") && coms[1].equals("keylock")){   //keylock end
        //build keylock end
        System.out.println("end, keylock");
        if(coms[2].contains(":")){
          coms[2] = coms[2].replaceAll("[^0-9]+","");
          int num = Integer.parseInt(coms[2]);
          //place "num" enemies
        }
        for (int j=3;j<coms.length;j++){
          if(coms[j].contains(":")){
            if(coms[j].contains("treasure:")){
              coms[j] = coms[j].replaceAll("[^0-9]+","");
              int num = Integer.parseInt(coms[j]);
              //place "num" treasure
              System.out.println("t"+num);
            }else if(coms[j].contains("armor:")){
              coms[j] = coms[j].replaceAll("[^0-9]+","");
              int num = Integer.parseInt(coms[j]);
              //place "num" armor
              System.out.println("a"+num);
            }else if(coms[j].contains("weapon:")){
              coms[j] = coms[j].replaceAll("[^0-9]+","");
              int num = Integer.parseInt(coms[j]);
              //place "num" weapon
              System.out.println("w"+num);          
            }
          }
        }        
        
      }else if(coms[0].equals("end") && coms[1].equals("maze")){     //maze end
        //build maze room
        System.out.println("end, maze");
        if(coms[2].contains(":")){
          coms[2] = coms[2].replaceAll("[^0-9]+","");
          int num = Integer.parseInt(coms[2]);
          System.out.println("e"+num);
          //place "num" enemies
        }
        for (int j=3;j<coms.length;j++){
          if(coms[j].contains(":")){
            if(coms[j].contains("treasure:")){
              coms[j] = coms[j].replaceAll("[^0-9]+","");
              int num = Integer.parseInt(coms[j]);
              //place "num" treasure
              System.out.println("t"+num);
            }else if(coms[j].contains("armor:")){
              coms[j] = coms[j].replaceAll("[^0-9]+","");
              int num = Integer.parseInt(coms[j]);
              //place "num" armor
              System.out.println("a"+num);
            }else if(coms[j].contains("weapon:")){
              coms[j] = coms[j].replaceAll("[^0-9]+","");
              int num = Integer.parseInt(coms[j]);
              //place "num" weapon
              System.out.println("w"+num);          
            }
          }
        }        
      }  
      } 
    //} catch(ArrayIndexOutOfBoundsException e) {
     // System.err.println("No File");
    } catch(IOException e) {
      System.err.println(e.getMessage());
    }
  }
}