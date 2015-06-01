import java.util.ArrayList;

public class Level {
    ArrayList<String> terrain;
    int height;
    int width;
    
    public Level(){
        terrain = new ArrayList<String>();
    }
    
    public Level(ArrayList<String> t){
        terrain = t;
        height = 0;
        width = 0;
        for (String line : t){
            if (line.length() > width){
                width = line.length();
            }
            height++;
        }
    }
    
    public ArrayList<String> getTerrain(){
        return terrain;
    }
    
    public void add(String l){
        terrain.add(l);
    }
    
    public String get(int i){
        return terrain.get(i);
    }
    
    public int findFirstOfCharX(char x){
        for (int i = 0; i < terrain.size(); i++){
            String line = terrain.get(i);
            for (int j = 0; j < line.length(); j++){
                if (line.charAt(j) == x){
                    return i;
                }
            }
        }
        
        return -1;
    }
    
    public int findFirstOfCharY(char x){
        for (int i = 0; i < terrain.size(); i++){
            String line = terrain.get(i);
            for (int j = 0; j < line.length(); j++){
                if (line.charAt(j) == x){
                    return j;
                }
            }
        }
        
        return -1;
    }
    
    public int findNearestMiddleX(char a){
        int x = width/2;
        int y = height/2;
        
        if (terrain.get(x).charAt(y) == a){
            return x;
        } else {
            if (x > 0){
                return findNearestX(a, x-1, y);
            } else if (x < width - 1){
                return findNearestX(a, x+1, y);
            } else if (y > 0){
                return findNearestX (a, x, y - 1);
            } else if (y < height) {
                return findNearestX(a, x, y+1);
            }
        }
        
        return -1;
    }
    
    public int findNearestX(char a, int x, int y){
        if (terrain.get(x).charAt(y) == a){
            return x;
        } else {
            if (x > 0){
                return findNearestX(a, x-1, y);
            } else if (x < width - 1){
                return findNearestX(a, x+1, y);
            } else if (y > 0){
                return findNearestX (a, x, y - 1);
            } else if (y < height) {
                return findNearestX(a, x, y+1);
            }
        }
        
        return -1;
    }
    
    public int findNearestMiddleY(char a){
        int x = width/2;
        int y = height/2;
        
        if (terrain.get(x).charAt(y) == a){
            return y;
        } else {
            if (x > 0){
                return findNearestY(a, x-1, y);
            } else if (x < width - 1){
                return findNearestY(a, x+1, y);
            } else if (y > 0){
                return findNearestY (a, x, y - 1);
            } else if (y < height) {
                return findNearestY(a, x, y+1);
            }
        }
        
        return -1;
    }
    
    public int findNearestY(char a, int x, int y){
        if (terrain.get(x).charAt(y) == a){
            return y;
        } else {
            if (x > 0){
                return findNearestY(a, x-1, y);
            } else if (x < width - 1){
                return findNearestY(a, x+1, y);
            } else if (y > 0){
                return findNearestY (a, x, y - 1);
            } else if (y < height) {
                return findNearestY(a, x, y+1);
            }
        }
        
        return -1;
    }
}