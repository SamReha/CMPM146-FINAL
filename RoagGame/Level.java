import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.Comparator;

public class Level {
    ArrayList<String> terrain;
    int height;
    int width;
    int floorspace;
    int startx, starty, endx, endy;
    int zlev;
    
    Comparator<PriorTuple> comparator = new Comparator<PriorTuple>(){

    @Override
    public int compare(PriorTuple o1, PriorTuple o2){
		int x = o1.getDist();
		int y = o2.getDist();
		if (x < y) return -1;
		if (x > y) return 1;
		return 0;
    }
};
    
    public Level(){
        terrain = new ArrayList<String>();
        zlev = Dungeon.world.size();
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
        zlev = Dungeon.world.size();
    }
    
    //Returns a rectangular empty dungeon with walls around
    public Level(int x, int y){
        width = x;
        height = y;
        floorspace = (x - 2) * (y - 2);
        terrain = new ArrayList<String>();
        String topBottomWall = "";
        for (int i = 0; i < width; i++){
            topBottomWall += "#";
        }
        String middles = "#";
        for (int i = 1; i < width - 1; i++){
            middles += ".";
        }
        middles += "#";
        
        terrain.add(topBottomWall);
        for (int i = 1; i < height - 1; i++){
            terrain.add(middles);
        }
        terrain.add(topBottomWall);
        
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
    
    public String toString(){
        String s = "";
        for (String l : terrain){
            s += l + "\n";
        }
        
        return s;
    }
    
    public void generatePlayerAndStairs(){
        int playerx = (int) (Math.random() * (width - 1)) + 1;
        int playery = (int) (Math.random() * (height - 1)) + 1;
        int stairx = (int) (Math.random() * (width - 1)) + 1;
        int stairy = (int) (Math.random() * (height - 1)) + 1;
        
        while (Math.sqrt(Math.pow((playerx-stairx), 2) + Math.pow((playery-stairy), 2)) < Math.sqrt(Math.pow(width - 2, 2) + Math.pow(height - 2, 2))/2 || (playerx == 0 || playery == 0 || stairx == 0 || stairy == 0 || playerx >= width - 1 || playery >= height - 1 || stairx >= width -1 || stairy >= height - 1)){
            playerx = (int) (Math.random() * (width - 1)) + 1;
            playery = (int) (Math.random() * (height - 1)) + 1;
            stairx = (int) (Math.random() * (width - 1)) + 1;
            stairy = (int) (Math.random() * (height - 1)) + 1;
        }
        
        String str = terrain.get(playery);
        terrain.set(playery, replaceIndex(str, playerx, '@'));
        startx = playery;
        starty = playerx;
        
        str = terrain.get(stairy);
        terrain.set(stairy, replaceIndex(str, stairx, 'v'));
        endx = stairy;
        endy = stairx;
        
    }
    
    public void generateStairs(){
        int stairupx = (int) (Math.random() * (width - 1)) + 1;
        int stairupy = (int) (Math.random() * (height - 1)) + 1;
        int stairdownx = (int) (Math.random() * (width - 1)) + 1;
        int stairdowny = (int) (Math.random() * (height - 1)) + 1;
        
        while (Math.sqrt(Math.pow((stairupx-stairdownx), 2) + Math.pow((stairupy-stairdowny), 2)) < Math.sqrt(Math.pow(width - 2, 2) + Math.pow(height - 2, 2))/2 || (stairupx == 0 || stairupy == 0 || stairdownx == 0 || stairdowny == 0 || stairupx >= width - 1 || stairupy >= height - 1 || stairdownx >= width -1 || stairdowny >= height - 1)){
            stairupx = (int) (Math.random() * (width - 1)) + 1;
            stairupx = (int) (Math.random() * (height - 1)) + 1;
            stairdownx = (int) (Math.random() * (width - 1)) + 1;
            stairdowny = (int) (Math.random() * (height - 1)) + 1;
        }
        
        String str = terrain.get(stairupy);
        terrain.set(stairupy, replaceIndex(str, stairupx, '^'));
        startx = stairupy;
        starty = stairupx;
        
        str = terrain.get(stairdowny);
        terrain.set(stairdowny, replaceIndex(str, stairdownx, 'v'));
        endx = stairdowny;
        endy = stairdownx;
    }
    
    public void generateWalls(){
        int area = floorspace;
        int numWalls = (int) ((Math.random() * (area/3)) + area/4);
        int wallsMade = 0;
        
        ArrayList<String> oldTerrain = terrain;
        
        while (wallsMade < numWalls){
            int randx = (int) (Math.random() * (width - 1)) + 1;
            int randy = (int) (Math.random() * (height - 1)) + 1;
            
            String str = oldTerrain.get(randy);
            if (str.charAt(randx) != '#' && str.charAt(randx) != '@' && str.charAt(randx) != 'v' && str.charAt(randx) != '^'){
                oldTerrain.set(randy, replaceIndex(str, randx, '#'));
                wallsMade++;
            }
        }
        
        while (!pathFromTo(startx, starty, endx, endy)){
            for (int i = 0; i < terrain.size(); i++){
                System.out.println(terrain.get(i));
            }
            System.out.println("no path found");
            removeWalls(terrain);
            wallsMade = 0;
            
            while (wallsMade < numWalls){
                numWalls = (int) ((Math.random() * (area/3)) + area/4);
                int randx = (int) (Math.random() * (width - 1)) + 1;
                int randy = (int) (Math.random() * (height - 1)) + 1;
                
                String str = terrain.get(randy);
                if (str.charAt(randx) != '#' && str.charAt(randx) != '@' && str.charAt(randx) != 'v' && str.charAt(randx) != '^'){
                    terrain.set(randy, replaceIndex(str, randx, '#'));
                    wallsMade++;
                }
            }
        }
    }
    
    public String replaceIndex(String s, int i, char w){
        String str = s;
        String newstr = str.substring(0, i);
        newstr += w;
        newstr += str.substring(i+1);
        return newstr;        
    }
    
    public int getStartX(){
        return startx;
    }
    
    public int getStartY(){
        return starty;
    }
    
    public int getEndX(){
        return endx;
    }
    
    public int getEndY(){
        return endy;
    }
    
    public void setStartX(int x){
        startx = x;
    }
    
    public void setStartY(int y){
        starty = y;
    }
    
    public void setEndX(int x){
        endx = x;
    }
    
    public void setEndY(int y){
        endy = y;
    }
    
    public boolean pathFromTo(int x, int y, int x2, int y2){
		TreeMap<Point, Integer> distance = new TreeMap<Point, Integer>();
		TreeMap<Point, Point> prev = new TreeMap<Point, Point>();
		Point source = new Point(x, y);
		Point dest = new Point(x2, y2);
		prev.put(source, null);
		distance.put(source, 0);
		PriorTuple start = new PriorTuple(0, source);
		PriorityQueue<PriorTuple> pq = new PriorityQueue<PriorTuple>(comparator);
        ArrayList<PriorTuple> neighbors = null;
		
		pq.add(start);
		
		while (pq.size() != 0){
			PriorTuple curr = pq.poll();
			
			if (curr.getPoint().equals(dest)){
				return true;
			}
			
			neighbors = getAdj(curr.getPoint());
			
			for (PriorTuple neigh : neighbors){
				int alt = distance.get(curr.getPoint()) + neigh.getDist();
				if (!distance.containsKey(neigh.getPoint()) /*|| alt < distance.get(neigh.getPoint())*/){
					distance.put(neigh.getPoint(), alt);
					PriorTuple newpt = new PriorTuple(alt, neigh.getPoint());
					prev.put(neigh.getPoint(), curr.getPoint());
                    pq.add(newpt);
				}
			}
		}
		
		return false;
	}
    
    public ArrayList<PriorTuple> getAdj(Point p){
		ArrayList<PriorTuple> parr = new ArrayList<PriorTuple>();
		int x = p.getX();
		int y = p.getY();
            if (x > 0 && x < height - 1 && emptySpace(terrain.get(x - 1).charAt(y))){
                    Point newp = new Point(x-1, y);
                    parr.add(new PriorTuple(1, newp));
                
            }
            if (x < height - 1 && x > 0 && emptySpace(terrain.get(x + 1).charAt(y))){
                    Point newp = new Point(x+1, y);
                    parr.add(new PriorTuple(1, newp));
            }
            if (y > 0 && y < width - 1 && emptySpace(terrain.get(x).charAt(y-1))){
                Point newp = new Point(x, y-1);
                parr.add(new PriorTuple(1, newp));
            }
            if (y < width - 1 && y > 0 && emptySpace(terrain.get(x).charAt(y+1))){
                Point newp = new Point(x, y+1);
                parr.add(new PriorTuple(1, newp));
            }
		
		return parr;
	}
    
    public boolean emptySpace(char a){
		if (a == Dungeon.FLOOR || a == Dungeon.STAIRS_DOWN || a == Dungeon.STAIRS_UP || a == Dungeon.PLAYER || a == Dungeon.SKELETRON || a == Dungeon.ARMOR || a == Dungeon.WEAPON){
			return true;
		}
		
		return false;
	}
    
    public void removeWalls(ArrayList<String> a){
        for (int i = 1; i < a.size() - 1; i++){
            String substr = a.get(i).substring(1, a.get(i).length() - 1);
            substr = substr.replace('#', '.');
            a.set(i, "#" + substr + "#");
        }
    }
}