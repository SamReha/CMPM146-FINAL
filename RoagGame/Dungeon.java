import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class Dungeon extends JPanel implements KeyListener {
	/**
	 * 
	 */
	 
	 static final char FLOOR = '.';
	 static final char WALL = '#';
	 static final char STAIRS_DOWN = 'v';
	 static final char STAIRS_UP = '^';
     static final char SKELETON = 's';
     static final char PLAYER = '@';
     static final char EMPTY = ' ';
     static final char ARMOR = 'a';
     static final char WEAPON = 'w';
	 
	private static final long serialVersionUID = 7547983272105812599L;
    static Image floor;
    static Image wall;
    static Image playerImg;
    static Image empty;
    static Image stairsup;
    static Image stairsdown;
    static Image skeleton;
    static Image animalskin;
    static Image geveningstar;
    static Image weveningstar;
    static Image metalbreastplate;
    static Image greendress;
    static Image redhair;
	static boolean game = true;
	static int worldwidth = 0;
    static int worldheight = 0;
	static ArrayList<Level> world = new ArrayList<Level>();
	static Player player;
	static boolean placed = false;
	String playerdirection = "up";
    static ArrayList<Enemy> enemies;
    static ArrayList<Armor> armor;
    static ArrayList<Weapon> weapons;
	long startTime;
	
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
	
	public Dungeon(String file){
		addKeyListener(this);
		setFocusable(true);
        ImageIcon pl = new ImageIcon("sprites\\human_f.png");
        ImageIcon fl = new ImageIcon("sprites\\rect_gray2.png");
        ImageIcon wa = new ImageIcon("sprites\\wall_vines6.png");
        ImageIcon un = new ImageIcon("sprites\\unseen.png");
        ImageIcon su = new ImageIcon("sprites\\stone_stairs_up.png");
        ImageIcon sd = new ImageIcon("sprites\\stone_stairs_down.png");
        ImageIcon sk = new ImageIcon("sprites\\skeleton_humanoid_large.png");
        ImageIcon as = new ImageIcon("sprites\\animal_skin.png");
        ImageIcon groundES = new ImageIcon("sprites\\eveningstar1.png");
        ImageIcon wieldES = new ImageIcon("sprites\\eveningstar.png");
        ImageIcon mbs = new ImageIcon("sprites\\bplate_metal1.png");
        ImageIcon gd = new ImageIcon("sprites\\dress_green.png");
        ImageIcon rh = new ImageIcon("sprites\\fem_red.png");
        playerImg = pl.getImage();
        floor = fl.getImage();
        wall = wa.getImage();
        empty = un.getImage();
        stairsup = su.getImage();
        stairsdown = sd.getImage();
        skeleton = sk.getImage();
        animalskin = as.getImage();
        weveningstar = wieldES.getImage();
        geveningstar = groundES.getImage();
        metalbreastplate = mbs.getImage();
        greendress = gd.getImage();
        redhair = rh.getImage();
        enemies = new ArrayList<Enemy>();
        armor = new ArrayList<Armor>();
        weapons = new ArrayList<Weapon>();
        player = new Player(10, 1, null, new Armor("Green Dress", "Chest", 0, -1, -1, -1, greendress), redhair);
		loadDungeon(file);
        player.setZ(0);
        
        if (player.getX() == -1 || player.getY() == -1){
            setPlayerNearestOpen(1, 1);
        }
		

	}
	
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		int lineNum = 0;
        int xNum = 0;
        int offset = 12;
        Level currLev = world.get(player.getZ());
		for (int i = player.getX() - offset; i <= player.getX() + offset; i++){
            xNum = 0;
			for (int j = player.getY() - offset; j < player.getY() + offset; j++){
                    if (i < 0 || i > currLev.getTerrain().size() - 1 || j < 0 || j >= currLev.getTerrain().get(i).length()){
                        g2d.drawImage(empty, lineNum * 32, xNum * 32, null);
                    }
                    else {
                        char a = currLev.getTerrain().get(i).charAt(j);
						if (a == FLOOR){
							g2d.drawImage(floor, lineNum * 32, xNum * 32, null);
						}
                        else if (a == WALL){
                            g2d.drawImage(wall, lineNum * 32, xNum * 32, null);
                        }
                        else if (a == EMPTY){
                            g2d.drawImage(empty, lineNum * 32, xNum * 32, null);
                        }
                        else if (a == STAIRS_DOWN){
                            g2d.drawImage(stairsdown, lineNum * 32, xNum * 32, null);
                        }
                        else if (a == STAIRS_UP){
                            g2d.drawImage(floor, lineNum * 32, xNum * 32, null);
                            g2d.drawImage(stairsup, lineNum * 32, xNum * 32, null);
                        }
                        else if (a == SKELETON || a == ARMOR || a == WEAPON){
                            g2d.drawImage(floor, lineNum * 32, xNum * 32, null);
                        }
                        else {
                            g2d.drawImage(empty, lineNum * 32, xNum * 32, null);
                        }
                        
                        for (Enemy en : enemies){                            
                            if (en.getZ() == player.getZ() && en.getX() == i && en.getY() == j){
                                g2d.drawImage(en.getImage(), lineNum * 32, xNum * 32, null);
                            }
                        }
                        
                        for (Weapon we : weapons){
                            if (we.getZ() == player.getZ() && we.getX() == i && we.getY() == j){
                                g2d.drawImage(we.getGroundImage(), lineNum * 32, xNum * 32, null);
                            }
                        }
                        
                        for (Armor ar : armor){
                            if (ar.getZ() == player.getZ() && ar.getX() == i && ar.getY() == j){
                                g2d.drawImage(ar.getImage(), lineNum * 32, xNum * 32, null);
                            }
                        }
                        
                        
                        
                        // DRAW PLAYER LAST, ABOVE EVERYTHING ELSE
                        if (player.getX() == i && player.getY() == j){
                            g2d.drawImage(playerImg, lineNum * 32, xNum * 32, null);
                            Armor ar = player.getArmor();
                            Weapon we = player.getWeapon();
                            if (ar != null){
                                g2d.drawImage(ar.getImage(), lineNum * 32, xNum * 32, null);
                            }
                            if (we != null){
                                g2d.drawImage(we.getWieldImage(), lineNum * 32, xNum * 32, null);
                            }
                            if (player.getHair() != null){
                                g2d.drawImage(player.getHair(), lineNum * 32, xNum * 32, null);
                            }
                        }
                    }
				
				xNum++;
			} 
            lineNum++;
		}
	}
	
	public static void loadDungeon(String file){
        worldheight = 0;
            
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int numLine = 0;
            Level lev = new Level();
            boolean added = false;
            
            while ((line = br.readLine()) != null) {
                added = false;
                lev.add(line);
                if (line.length() > worldwidth){
                    worldwidth = line.length();
                }
                numLine++;
                
                if (line.equals("")){
                    if (numLine > worldheight){
                        worldheight = numLine;
                    }
                    world.add(lev);
                    lev = new Level();
                    numLine = 0;
                    added = true;
                }
            }
            
            if (!added){
                if (numLine > worldheight){
                    worldheight = numLine;
                }
                world.add(lev);
            }
            
        } catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }
        
        int xpos = 0;
        int zlev = 0;
        for (Level currLev : world){
            xpos = 0;
            for (String line : currLev.getTerrain()){
                for (int j = 0; j < line.length(); j++){
                    char a = line.charAt(j);
                    if (a == SKELETON){
                        enemies.add(new Enemy("Skeleton", 5, 1, 1, xpos, j, zlev, skeleton));
                    } else if (a == PLAYER){
                        player.setX(xpos);
                        player.setY(j);
                        player.setZ(zlev);
                    }
                    else if (a == ARMOR){
                        armor.add(new Armor("Animal Skin", "Chest", 1, xpos, j, zlev, animalskin));
                    }
                    else if (a == WEAPON){
                        weapons.add(new Weapon("Evening Star", 1, xpos, j, zlev, geveningstar, weveningstar));
                    }
                } 
                xpos++;
            }
            zlev++;
        }
	}

	public static void setPlayerNearestOpen(int x, int y){
		if (world.get(0).getTerrain().get(x).charAt(y) == FLOOR){
			player.setX(x);
			player.setY(y);
			placed = true;
		} else {
			if (!placed){
				if (x > 0)
					setPlayerNearestOpen(x - 1, y);
				else if (x < worldwidth)
					setPlayerNearestOpen(x + 1, y);
				else if (y > 0)
					setPlayerNearestOpen(x, y - 1);
				else if (y < worldheight)
					setPlayerNearestOpen(x, y + 1);	
			}
			
		}
	}

	public void keyPressed(KeyEvent e){
        boolean causesEnemyAction = false;
		//System.out.println("key");
		//System.out.println(e.getKeyCode());
		int id = e.getKeyCode();
		if (id == KeyEvent.VK_UP){
            causesEnemyAction = true;
			movePlayer(0, -1);
			playerdirection = "up";
		} else if (id == KeyEvent.VK_DOWN){
            causesEnemyAction = true;
			movePlayer(0, 1);
			playerdirection = "down";
		} else if (id == KeyEvent.VK_LEFT){
            causesEnemyAction = true;
			movePlayer(-1, 0);
			playerdirection = "left";
		} else if (id == KeyEvent.VK_RIGHT){
            causesEnemyAction = true;
			movePlayer(1, 0);
			playerdirection = "right";
		} else if (id == KeyEvent.VK_SPACE){
            causesEnemyAction = true;
			performAction(player.getX(), player.getY());
		}
        
        if (causesEnemyAction){
            for (int i = 0; i < enemies.size(); i++){
                if (enemies.get(i).dead()){
                    enemies.remove(i);
                }
            }
            for (Enemy en : enemies){
                en.act(player);
            }
        }
        
	}
	
	/*public void doAction(){
		int newx = 0, newy = 0;
		if (playerdirection.equals("up")){
			newx = player.getX();
			newy = player.getY() - 1;
		} else if (playerdirection.equals("down")){
			newx = player.getX();
			newy = player.getY() + 1;
		} else if (playerdirection.equals("left")){
			newx = player.getX() - 1;
			newy = player.getY();
		} else if (playerdirection.equals("right")){
			newx = player.getX() + 1;
			newy = player.getY();
		}
		
		performAction(newx, newy);
	}*/
    
    public Enemy enemyAt(int x, int y){
        for (Enemy e : enemies){
            if (e.getZ() == player.getZ()){
                if (e.getX() == x && e.getY() == y){
                    return e;
                }
            }
        }
        
        return null;
    }
	
	public void performAction(int newx, int newy){
		char a = world.get(player.getZ()).getTerrain().get(newx).charAt(newy);
		/*if (a == TREE){
			world[newx][newy] = DEADTREE;
			findTrees(startTime);
		} else if (a == LADDER_DOWN){
		} else if (a == LADDER_UP){
		}*/
        if (a == STAIRS_DOWN){
            if (player.getZ() < world.size() - 1){
                int x = 0;
                int y = 0;
                player.setZ(player.getZ() + 1);
                Level curr = world.get(player.getZ());
                x = curr.findFirstOfCharX(STAIRS_UP);
                if (x == -1){
                    x = curr.findNearestMiddleX(FLOOR);
                    y = curr.findNearestMiddleY(FLOOR);
                } else {
                    y = curr.findFirstOfCharY(STAIRS_UP);
                }
                
                player.setX(x);
                player.setY(y);
            }
        }
        
        else if (a == STAIRS_UP){
            if (player.getZ() > 0){
                int x = 0;
                int y = 0;
                player.setZ(player.getZ() - 1);
                Level curr = world.get(player.getZ());
                x = curr.findFirstOfCharX(STAIRS_DOWN);
                if (x == -1){
                    x = curr.findNearestMiddleX(FLOOR);
                    y = curr.findNearestMiddleY(FLOOR);
                } else {
                    y = curr.findFirstOfCharY(STAIRS_DOWN);
                }
                
                player.setX(x);
                player.setY(y);
            }
        }
        
        for (int i = 0; i < armor.size(); i++){
            Armor ar = armor.get(i);
            if (ar.getZ() == player.getZ() && ar.getX() == newx && ar.getY() == newy){
                Armor old = player.getArmor();
                player.setArmor(ar);
                if (old != null){
                    old.setZ(player.getZ());
                    old.setX(newx);
                    old.setY(newy);
                    armor.add(old);
                }
                
                ar.setX(-1);
                ar.setY(-1);
                ar.setZ(-1);
                armor.remove(ar);
            }
        }
        
        for (int i = 0; i < weapons.size(); i++){
            Weapon we = weapons.get(i);
            if (we.getZ() == player.getZ() && we.getX() == newx && we.getY() == newy){
                Weapon old = player.getWeapon();
                player.setWeapon(we);
                if (old != null){
                    old.setZ(player.getZ());
                    old.setX(newx);
                    old.setY(newy);
                    weapons.add(old);
                }
                
                we.setX(-1);
                we.setY(-1);
                we.setZ(-1);
                armor.remove(we);
            }
        }
		
		repaint();
	}
    
    public ArrayList<Level> getWorld(){
        return world;
    }

	public void movePlayer(int x, int y){
		int tx = player.getX() + x;
		int ty = player.getY() + y;
            if (!collidingWithEnemy(tx, ty)){
                if (walkable(world.get(player.getZ()).getTerrain().get(tx).charAt(ty))){
                    player.setX(tx);
                    player.setY(ty);
                    player.regen();
                }
            } else {
                player.attack(enemyAt(tx, ty));
            }
			
		repaint();
		//printWorld();
	}
    
    public boolean collidingWithEnemy(int x, int y){
        for (Enemy en : enemies){
            if (en.getZ() == player.getZ()){
                if (en.getX() == x && en.getY() == y){
                    return true;
                }
            }
        }
        
        return false;
    }
    
    public boolean walkable(char a){
        if (a == FLOOR || a == SKELETON || a == STAIRS_UP || a == STAIRS_DOWN || a == ARMOR || a == WEAPON){
            return true;
        }
        
        return false;
    }
		
		public void keyTyped(KeyEvent e) {
		}

		public void keyReleased(KeyEvent e){

		}
	
	public boolean emptySpace(char a){
		if (a == FLOOR){
			return true;
		}
		
		return false;
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
		
		return false;
	}
	
	public ArrayList<PriorTuple> getAdj(Point p){
		ArrayList<PriorTuple> parr = new ArrayList<PriorTuple>();
		int x = p.getX();
		int y = p.getY();
		
		if (x > 0 && emptySpace(world.get(player.getZ()).getTerrain().get(x-1).charAt(y))){
			Point newp = new Point(x-1, y);
			parr.add(new PriorTuple(1, newp));
		}
		if (x < 15 && emptySpace(world.get(player.getZ()).getTerrain().get(x+1).charAt(y))){
			Point newp = new Point(x+1, y);
			parr.add(new PriorTuple(1, newp));
		}
		if (y > 0 && emptySpace(world.get(player.getZ()).getTerrain().get(x).charAt(y-1))){
			Point newp = new Point(x, y-1);
			parr.add(new PriorTuple(1, newp));
		}
		if (y < 15 && emptySpace(world.get(player.getZ()).getTerrain().get(x).charAt(y+1))){
			Point newp = new Point(x, y+1);
			parr.add(new PriorTuple(1, newp));
		}
		
		return parr;
	}
	
	public double heuristic(int x, int y, int x2, int y2){
		return Math.sqrt(Math.pow((x2-x), 2) + Math.pow((y2-y), 2));
	}
	
    public double heuristic(Point p1, Point p2){
        return Math.sqrt(Math.pow((p2.getX() - p1.getX()), 2) + Math.pow((p2.getY() - p1.getY()), 2));
    }
    
	class Point implements Comparable<Point>{
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
	}
	
}
