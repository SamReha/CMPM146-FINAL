import java.util.TreeMap;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Enemy{
    String name;
    int health;
    int strength;
    float speed;
    int ex, ey, ez;
    Image img;
    boolean dead;
    float numMoves;
    
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
    
    public Enemy(String n, int h, int s, float speed, int xpos, int ypos, int zpos, Image image){
        name = n;
        health = h;
        strength = s;
        this.speed = speed;
        ex = xpos;
        ey = ypos;
        ez = zpos;
        img = image;
        dead = false;
        numMoves = 0;
    }
    
    public String getName(){
        return name;
    }
    
    public int getHealth(){
        return health;
    }
    
    public int getStrength(){
        return strength;
    }
    
    public Image getImage(){
        return img;
    }
    
    public int getX(){
        return ex;
    }
    
    public int getY(){
        return ey;
    }
    
    public int getZ(){
        return ez;
    }
    
    public void setName(String n){
        name = n;
    }
    
    public void setHealth(int h){
        health = h;
    }
    
    public void setStrength(int s){
        strength = s;
    }
    
    public void setImage(Image image){
        img = image;
    }
    
    public void setImage(String s){
        img = (new ImageIcon(s)).getImage();
    }
    
    public boolean dead(){
        return dead;
    }
    
    public void attack(Player p){
        int damage = strength;
        int armorCheck = (int) (Math.random() * 2);
        if (armorCheck == 0){
            Armor a = p.getArmor();
            if (a != null){
                damage -= a.getDefense();
                if (damage <= 0){
                    damage = 0;
                }
            }
        }
        p.setHealth(p.getHealth() - damage);
        if (p.getHealth() <= 0){
            p.die();
        }
        System.out.println(p.getHealth());
    }
    
    public void die(){
        dead = true;
    }
    
    public void act(Player p){
        float d = distanceTo(p);
        
        if (d > 10 || ez != p.getZ())
            return;
        numMoves += speed;
        if (d < 2){
            attack(p);
            return;
        }
        ArrayList<Point> path = astar(p);
        if (path != null){
            Point point;
            if (path.size() > Math.floor(numMoves)){
                point = path.get((int) Math.floor(numMoves - 1));
                numMoves -= (int) Math.floor(numMoves);
            }
            else {
                point = path.get(path.size() - 1);
                numMoves -= path.size();
            }
            ex = point.getX();
            ey = point.getY();
            System.out.println(ex + ", " + ey);
        }
        
        if (numMoves >= 1 && distanceTo(p) < 2){
            attack(p);
        }
    }
    
    public float distanceTo(Player p){
        float d = 0;
        d = (float) Math.sqrt(Math.pow(p.getX() - ex, 2) + Math.pow(p.getY() - ey, 2));
        return d;
    }
    
    public ArrayList<Point> astar(Player p){
        boolean found = false;
        TreeMap<Point, Integer> distance = new TreeMap<Point, Integer>();
		TreeMap<Point, Point> prev = new TreeMap<Point, Point>();
		Point source = new Point(ex, ey);
		Point dest = new Point(p.getX(), p.getY());
		prev.put(source, null);
		distance.put(source, 0);
		PriorTuple start = new PriorTuple(0, source);
		PriorityQueue<PriorTuple> pq = new PriorityQueue<PriorTuple>(comparator);
        ArrayList<PriorTuple> neighbors = null;
        ArrayList<Point> path = new ArrayList<Point>();
		
		pq.add(start);
		
		while (pq.size() != 0){
			PriorTuple curr = pq.poll();
			
			if (curr.getPoint().equals(dest)){
                System.out.println("found");
                found = true;
				break;
			}
			
			neighbors = getAdj(curr.getPoint());
			
			for (PriorTuple neigh : neighbors){
				int alt = distance.get(curr.getPoint()) + neigh.getDist();
				if (!distance.containsKey(neigh.getPoint()) /*|| alt < distance.get(neigh.getPoint())*/){
                    if (distance.containsKey(neigh.getPoint())){
                        System.out.println("(" + neigh.getPoint().getX() + ", " + neigh.getPoint().getY() + ")");
                    System.out.println(alt + " " + distance.get(neigh.getPoint()));
                    System.out.println(alt < distance.get(neigh.getPoint()));
                    }
                    
					distance.put(neigh.getPoint(), alt);
					PriorTuple newpt = new PriorTuple(alt, neigh.getPoint());
					prev.put(neigh.getPoint(), curr.getPoint());
                    pq.add(newpt);
				}
			}
		}
		
        if (found){
            Point point = dest;
            point = prev.get(point);
            while (!point.equals(source)){
                path.add(0, point);
                point = prev.get(point);
            }
            System.out.println("path found");
            return path;
        }
        
		return null;
    }
    
    public boolean emptySpace(char a){
		if (a == Dungeon.FLOOR || a == Dungeon.STAIRS_DOWN || a == Dungeon.STAIRS_UP){
			return true;
		}
		
		return false;
	}
    
    public ArrayList<PriorTuple> getAdj(Point p){
		ArrayList<PriorTuple> parr = new ArrayList<PriorTuple>();
		int x = p.getX();
		int y = p.getY();
        Player pl = Dungeon.player;
		try {
            if (x > 0 && x < Dungeon.worldheight - 1 && emptySpace(Dungeon.world.get(ez).getTerrain().get(x - 1).charAt(y))){
                    Point newp = new Point(x-1, y);
                    parr.add(new PriorTuple(1, newp));
                
            }
        } catch (IndexOutOfBoundsException e){
            
        }
		try {
            if (x < Dungeon.worldheight - 1 && x > 0 && emptySpace(Dungeon.world.get(ez).getTerrain().get(x + 1).charAt(y))){
                    System.out.println("Not colliding");
                    Point newp = new Point(x+1, y);
                    parr.add(new PriorTuple(1, newp));
            }
        } catch (IndexOutOfBoundsException e){
            
        }
		
        try{
            if (y > 0 && y < Dungeon.worldwidth - 1 && emptySpace(Dungeon.world.get(ez).getTerrain().get(x).charAt(y-1))){
                Point newp = new Point(x, y-1);
                parr.add(new PriorTuple(1, newp));
            }
        } catch (IndexOutOfBoundsException e){
            
        }
		
		try {
            if (y < Dungeon.worldwidth - 1 && y > 0 && emptySpace(Dungeon.world.get(ez).getTerrain().get(x).charAt(y+1))){
                Point newp = new Point(x, y+1);
                parr.add(new PriorTuple(1, newp));
            }
        } catch (IndexOutOfBoundsException e){
            
        }
		
		return parr;
	}
    
    public boolean collidingWithPlayer(int x, int y){
        Player p = Dungeon.player;
        if (ez == p.getZ() && x == p.getX() && y == p.getY()){
            return true;
        }
        
        return false;
    }
    
    /*class Point implements Comparable<Point>{
		int x, y;
		
		public Point(int x, int y){
			this.x = x;
			this.y = y;
		}
		
		public int getX(){
			return x;
		}
		
		public int getY(){
			return y;
		}
		
		public boolean equals(Point p){
			if (this.x == p.getX() && this.y == p.getY()){
				return true;
			}
			
			return false;
		}
        
        @Override
        public int compareTo(Point p){
            if (this.x == p.x){
                if (this.y == p.y){
                    return 0;
                }
                
                else if (this.y < p.y){
                    return -1;
                }
                
                else {
                    return 1;
                }
            }
            
            else {
                if (this.x < p.x){
                    return -1;
                }
                
                else {
                    return 1;
                }
            }
        }
	}
	
	class PriorTuple{
		Point p;
		int d;
		
		public PriorTuple(int d, Point p){
			this.d = d;
			this.p = p;
		}
		
		public int getDist(){
			return d;
		}
		
		public Point getPoint(){
			return p;
		}
	}*/
    
    
}