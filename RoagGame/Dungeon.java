import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.awt.Color;
import java.io.File;
//import java.God.*;


public class Dungeon extends JPanel implements KeyListener {
    /**
     * 
     */
     
    static final char FLOOR = '.';
    static final char WALL = '#';
    static final char STAIRS_DOWN = 'v';
    static final char STAIRS_UP = '^';
    static final char SKELETRON = 's';
    static final char PLAYER = '@';
    static final char EMPTY = ' ';
    static final char ARMOR = 'a';
    static final char WEAPON = 'w';
    static final char AMULET = 'g';
    static final char GATE = 'G';
    static final char KEY = 'k';
    static final char CHEST = 'c';
     
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
    static Image amulet;
    static Image chest;
    static boolean win = false;
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
    
    public Dungeon(){
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
        ImageIcon am = new ImageIcon("sprites\\stone3_magenta.png");
        ImageIcon ch = new ImageIcon("sprites\\chest.png");
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
        amulet = am.getImage();
        chest = ch.getImage();
        enemies = new ArrayList<Enemy>();
        armor = new ArrayList<Armor>();
        weapons = new ArrayList<Weapon>();
        player = new Player(10, 1, null, new Armor("Green Dress", "Chest", 0, -1, -1, -1, greendress), redhair);
        //loadDungeon(file);
        /*Level start = generateTopLevel();
        world.add(start);
        Level middle = generateMiddleLevel();
        world.add(middle);
        System.out.println(start);*/
        
        getNewTrace();
        readTrace("trace.txt");
        
        parseWorld(world);
        placePlayer();
        
        /*if (player.getX() == -1 || player.getY() == -1){
            setPlayerNearestOpen(1, 1);
            player.setZ(0);
        }*/
        

    }
    
    public void restart(){
        enemies = new ArrayList<Enemy>();
        armor = new ArrayList<Armor>();
        weapons = new ArrayList<Weapon>();
        win = false;
        player = new Player(10, 1, null, new Armor("Green Dress", "Chest", 0, -1, -1, -1, greendress), redhair);
        world = new ArrayList<Level>();
        getNewTrace();
        readTrace("trace.txt");
        
        parseWorld(world);
        placePlayer();
    }
    
    public void placePlayer(){
        for (int i = 0; i < world.size(); i++){
            ArrayList<String> lev = world.get(i).getTerrain();
            for (int j = 0; j < lev.size(); j++){
                if (lev.get(j).indexOf('@') != -1){
                    System.out.println("(" + j + ", " + lev.get(j).indexOf('@') + ", " + i);
                    player.setX(j);
                    player.setY(lev.get(j).indexOf('@'));
                    player.setZ(i);
                    return;
                }
            }
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
                        else if (a == SKELETRON || a == ARMOR || a == WEAPON || a == PLAYER){
                            g2d.drawImage(floor, lineNum * 32, xNum * 32, null);
                        }
                        else if (a == AMULET){
                            g2d.drawImage(floor, lineNum * 32, xNum * 32, null);
                            g2d.drawImage(amulet, lineNum * 32, xNum * 32, null);
                        }
                        else if (a == CHEST){
                            g2d.drawImage(floor, lineNum * 32, xNum * 32, null);
                            g2d.drawImage(chest, lineNum * 32, xNum * 32, null);
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
        
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("TimesRoman", Font.BOLD, 16));
        g2d.drawString("Health: " + player.getHealth() + "/" + player.getMaxHealth(), 50, 50);
        
        if (player.getHealth() <= 0){
            g2d.setColor(Color.RED);
            g2d.setFont(new Font("TimesRoman", Font.BOLD, 72));
            g2d.drawString("You died", 300, 400);
            g2d.setFont(new Font("TimesRoman", Font.PLAIN, 24));
            g2d.drawString("Press R to restart", 300, 450);
        }
        if (win){
            g2d.setColor(Color.BLUE);
            g2d.setFont(new Font("TimesRoman", Font.BOLD, 72));
            g2d.drawString("You win!", 300, 400);
            
            g2d.setFont(new Font("TimesRoman", Font.PLAIN, 24));
            g2d.drawString("Press R to restart", 300, 450);
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
                    if (a == SKELETRON){
                        enemies.add(new Enemy("Skeletron", 5, 1, .5f, xpos, j, zlev, skeleton));
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
        int id = e.getKeyCode();
        if (player.getHealth() > 0 && !win){
            boolean causesEnemyAction = false;
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
        
        if (id == KeyEvent.VK_R){
            restart();
            repaint();
        }
        
        
    }
    
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
        
        else if (a == AMULET){
            win = true;
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
        if (a == FLOOR || a == SKELETRON || a == STAIRS_UP || a == STAIRS_DOWN || a == ARMOR || a == WEAPON || a == PLAYER || a == AMULET){
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
    
    public Level generateStart(){
        int wid = (int) (Math.random() * 50) + 10;
        int hei = (int) (Math.random() * 50) + 10;
        Level newLev = new Level(wid, hei);
        newLev.generatePlayerAndStairs();
        System.out.println("player and stairs added");
        newLev.generateWalls();
        System.out.println("walls added");
        newLev.generateEnemies();
        System.out.println("skeletrons invading");
        return newLev;
        
    }   
    
    public Level generateKeyLock(){
        int wid = (int) (Math.random() * 50) + 10;
        int hei = (int) (Math.random() * 50) + 10;
        Level newLev = new Level(wid, hei);
        newLev.generateStairs();
        newLev.generateWalls();
        newLev.generateEnemies();
        return newLev;
    }
    
    public Level generateMaze(){
        int wid = (int) (Math.random() * 50) + 50;
        int hei = (int) (Math.random() * 50) + 50;
        Level newLev = new Level(wid, hei);
        newLev.generateStairs();
        System.out.println("stairs added");
        newLev.generateWalls();
        System.out.println("maze walls added");
        newLev.generateEnemies();
        System.out.println("skeletrons invading");
        return newLev;
    }
    
    public Level generateMiddleLevelNoObstacles(){
        int wid = (int) (Math.random() * 30) + 10;
        int hei = (int) (Math.random() * 30) + 10;
        Level newLev = new Level(wid, hei);
        newLev.generateStairsNoObstacles();
        System.out.println("stairs added");
        newLev.generateWallsNoObstacles();
        System.out.println("no obstacle walls added");
        newLev.generateEnemies();
        System.out.println("skeletrons invading");
        return newLev;
    }
    
    public Level generateEnd(){
        int wid = (int) (Math.random() * 50) + 50;
        int hei = (int) (Math.random() * 50) + 50;
        Level newLev = new Level(wid, hei);
        newLev.generateStairsAndEnd();
        System.out.println("stairs and gems added");
        newLev.generateWalls();
        System.out.println("maze walls added");
        newLev.generateEnemies();
        System.out.println("skeletrons invading");
        return newLev;
    }
    
    public void parseWorld(ArrayList<Level> levs){
        for (int z = 0; z < levs.size(); z++){
            for (int i = 0; i < levs.get(z).getTerrain().size(); i++){
                String str = levs.get(z).get(i);
                for (int j = 0; j < str.length(); j++){
                    if (str.charAt(j) == 's'){
                        enemies.add(new Enemy("Skeletron", 5, 1, .5f, i, j, z, skeleton));
                    }
                    else if (str.charAt(j) == 'a'){
                        armor.add(new Armor("Animal Skin", "Chest", 1, i, j, z, animalskin));
                    }
                    else if (str.charAt(j) == 'w'){
                        weapons.add(new Weapon("Evening Star", 1, i, j, z, geveningstar, weveningstar));
                    }
                }
            }
        }
        
    }
    
    public void getNewTrace(){
        try{
            String filepath = new File("").getAbsolutePath();
            filepath.concat("..\\TraceParcer\\expander.js");
            /*Process pbj = new ProcessBuilder("node",filepath).start();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(pbj.getOutputStream()));
            bw.*/
            Process pbj = Runtime.getRuntime().exec("node " + filepath);
            pbj.waitFor();
            System.out.println("Peanut Butter currently Jellying");
        } catch (IOException e){
            System.out.println("Peanut Butter failed to Jelly");
        } catch (InterruptedException e){
            System.out.println("Peanut Butter interrupted by Jelly");
        }
    }
    
    public void readTrace(String fn){
        
        
        String filename;
    try {
      filename = fn;
      ReadFile file = new ReadFile(filename);
      String[] Trace = file.OpenFile();
      String delims = "[ ]";
      for (int i = 0; i < Trace.length; i++){
        String[] coms = Trace[i].split(delims);
    
    
        if(coms[0].equals("start") && coms[1].equals("no_obstacle")){  //No obs start
            int wid = (int) (Math.random() * 30) + 10;
            int hei = (int) (Math.random() * 30) + 10;
            Level newLev = new Level(wid, hei);
          //build no obstacle start
          System.out.println("start, no obs");
            newLev.generatePlayerAndStairs();
            newLev.generateWallsNoObstacles();
          if(coms[2].contains(":")){
            coms[2] = coms[2].replaceAll("[^0-9]+","");
            int num = Integer.parseInt(coms[2]);
            //place "num" enemies
            newLev.generateEnemies(num);
          }
          for (int j=3;j<coms.length;j++){
            if(coms[j].contains(":")){
              if(coms[j].contains("treasure:")){
                coms[j] = coms[j].replaceAll("[^0-9]+","");
                int num = Integer.parseInt(coms[j]);
                //place "num" treasure
                System.out.println("t"+num);
                newLev.generateTreasure(num);
              }else if(coms[j].contains("armor:")){
                coms[j] = coms[j].replaceAll("[^0-9]+","");
                int num = Integer.parseInt(coms[j]);
                //place "num" armor
                System.out.println("a"+num);
                newLev.generateArmor(num);
              }else if(coms[j].contains("weapon:")){
                coms[j] = coms[j].replaceAll("[^0-9]+","");
                int num = Integer.parseInt(coms[j]);
                //place "num" weapon
                System.out.println("w"+num); 
                newLev.generateWeapons(num);
              }
            }
          }
                
          world.add(newLev);
        
        }else if(coms[0].equals("start") && coms[1].equals("keylock")){ //keylock start
            int wid = (int) (Math.random() * 50) + 10;
            int hei = (int) (Math.random() * 50) + 10;
            Level newLev = new Level(wid, hei);
          //build keylock start
          System.out.println("start, keylock");
            newLev.generatePlayerAndStairsKeyLock();
            newLev.generateWallsKeyLock();
          if(coms[2].contains(":")){
            coms[2] = coms[2].replaceAll("[^0-9]+","");
            int num = Integer.parseInt(coms[2]);
            //place "num" enemies
            newLev.generateEnemies(num);
          }
          for (int j=3;j<coms.length;j++){
            if(coms[j].contains(":")){
              if(coms[j].contains("treasure:")){
                coms[j] = coms[j].replaceAll("[^0-9]+","");
                int num = Integer.parseInt(coms[j]);
                //place "num" treasure
                System.out.println("t"+num);
                newLev.generateTreasure(num);
              }else if(coms[j].contains("armor:")){
                coms[j] = coms[j].replaceAll("[^0-9]+","");
                int num = Integer.parseInt(coms[j]);
                //place "num" armor
                System.out.println("a"+num);
                newLev.generateArmor(num);
              }else if(coms[j].contains("weapon:")){
                coms[j] = coms[j].replaceAll("[^0-9]+","");
                int num = Integer.parseInt(coms[j]);
                //place "num" weapon
                System.out.println("w"+num);    
                newLev.generateWeapons(num);
              }
            }
          }
                world.add(newLev);
        }else if(coms[0].equals("start") && coms[1].equals("maze")){ //maze start
            int wid = (int) (Math.random() * 50) + 50;
            int hei = (int) (Math.random() * 50) + 50;
            Level newLev = new Level(wid, hei);
            newLev.generatePlayerAndStairs();
            newLev.generateWalls();
            System.out.println("start, maze");
          if(coms[2].contains(":")){
            coms[2] = coms[2].replaceAll("[^0-9]+","");
            int num = Integer.parseInt(coms[2]);
            System.out.println("e"+num);
            //place "num" enemies
            newLev.generateEnemies(num);
          }
          for (int j=3;j<coms.length;j++){
            if(coms[j].contains(":")){
              if(coms[j].contains("treasure:")){
                coms[j] = coms[j].replaceAll("[^0-9]+","");
                int num = Integer.parseInt(coms[j]);
                //place "num" treasure
                System.out.println("t"+num);
                newLev.generateTreasure(num);
              }else if(coms[j].contains("armor:")){
                coms[j] = coms[j].replaceAll("[^0-9]+","");
                int num = Integer.parseInt(coms[j]);
                //place "num" armor
                System.out.println("a"+num);
                newLev.generateArmor(num);
              }else if(coms[j].contains("weapon:")){
                coms[j] = coms[j].replaceAll("[^0-9]+","");
                int num = Integer.parseInt(coms[j]);
                //place "num" weapon
                System.out.println("w"+num); 
                newLev.generateWeapons(num);
              }
            }
          }
          
          world.add(newLev);
        }
      
      
      
      
        else if(coms[0].equals("room") && coms[1].equals("no_obstacle")){ //no obs middle
            int wid = (int) (Math.random() * 30) + 10;
            int hei = (int) (Math.random() * 30) + 10;
            Level newLev = new Level(wid, hei);
          //build no obstacle room
          System.out.println("room, no obs");
            newLev.generateStairs();
            newLev.generateWallsNoObstacles();
          if(coms[2].contains(":")){
            coms[2] = coms[2].replaceAll("[^0-9]+","");
            int num = Integer.parseInt(coms[2]);
            //place "num" enemies
            newLev.generateEnemies(num);
          }
          for (int j=3;j<coms.length;j++){
            if(coms[j].contains(":")){
              if(coms[j].contains("treasure:")){
                coms[j] = coms[j].replaceAll("[^0-9]+","");
                int num = Integer.parseInt(coms[j]);
                //place "num" treasure
                System.out.println("t"+num);
                newLev.generateTreasure(num);
              }else if(coms[j].contains("armor:")){
                coms[j] = coms[j].replaceAll("[^0-9]+","");
                int num = Integer.parseInt(coms[j]);
                //place "num" armor
                System.out.println("a"+num);
                newLev.generateArmor(num);
              }else if(coms[j].contains("weapon:")){
                coms[j] = coms[j].replaceAll("[^0-9]+","");
                int num = Integer.parseInt(coms[j]);
                //place "num" weapon
                System.out.println("w"+num); 
                newLev.generateWeapons(num);
              }
            }
          }        
            world.add(newLev);
        }else if(coms[0].equals("room") && coms[1].equals("keylock")){  //keylock midde
            int wid = (int) (Math.random() * 50) + 10;
            int hei = (int) (Math.random() * 50) + 10;
            Level newLev = new Level(wid, hei);
          //build keylock room
          System.out.println("room, keylock");
            newLev.generateStairsKeyLock();
            newLev.generateWallsKeyLock();
          if(coms[2].contains(":")){
            coms[2] = coms[2].replaceAll("[^0-9]+","");
            int num = Integer.parseInt(coms[2]);
            //place "num" enemies
            newLev.generateEnemies(num);
          }
          for (int j=3;j<coms.length;j++){
            if(coms[j].contains(":")){
              if(coms[j].contains("treasure:")){
                coms[j] = coms[j].replaceAll("[^0-9]+","");
                int num = Integer.parseInt(coms[j]);
                //place "num" treasure
                System.out.println("t"+num);
                newLev.generateTreasure(num);
              }else if(coms[j].contains("armor:")){
                coms[j] = coms[j].replaceAll("[^0-9]+","");
                int num = Integer.parseInt(coms[j]);
                //place "num" armor
                System.out.println("a"+num);
                newLev.generateArmor(num);
              }else if(coms[j].contains("weapon:")){
                coms[j] = coms[j].replaceAll("[^0-9]+","");
                int num = Integer.parseInt(coms[j]);
                //place "num" weapon
                System.out.println("w"+num); 
                newLev.generateWeapons(num);
              }
            }
          }        
            world.add(newLev);
        }else if(coms[0].equals("room") && coms[1].equals("maze")){  //maze middle
            int wid = (int) (Math.random() * 50) + 50;
            int hei = (int) (Math.random() * 50) + 50;
            Level newLev = new Level(wid, hei);
          //build maze room
          System.out.println("room, maze");
            newLev.generateStairs();
            newLev.generateWalls();
          if(coms[2].contains(":")){
            coms[2] = coms[2].replaceAll("[^0-9]+","");
            int num = Integer.parseInt(coms[2]);
            //place "num" enemies
            newLev.generateEnemies(num);
          }
          for (int j=3;j<coms.length;j++){
            if(coms[j].contains(":")){
              if(coms[j].contains("treasure:")){
                coms[j] = coms[j].replaceAll("[^0-9]+","");
                int num = Integer.parseInt(coms[j]);
                //place "num" treasure
                System.out.println("t"+num);
                newLev.generateTreasure(num);
              }else if(coms[j].contains("armor:")){
                coms[j] = coms[j].replaceAll("[^0-9]+","");
                int num = Integer.parseInt(coms[j]);
                //place "num" armor
                System.out.println("a"+num);
                newLev.generateArmor(num);
              }else if(coms[j].contains("weapon:")){
                coms[j] = coms[j].replaceAll("[^0-9]+","");
                int num = Integer.parseInt(coms[j]);
                //place "num" weapon
                System.out.println("w"+num);
                newLev.generateWeapons(num);
              }
            }
          }  
            world.add(newLev);          
        }

        else if(coms[0].equals("end") && coms[1].equals("no_obstacle")){ //no obs end
            int wid = (int) (Math.random() * 30) + 10;
            int hei = (int) (Math.random() * 30) + 10;
            Level newLev = new Level(wid, hei);
          //build no obstacle end
          System.out.println("end, no obs");
            newLev.generateStairsAndEnd();
            newLev.generateWallsNoObstacles();
          if(coms[2].contains(":")){
            coms[2] = coms[2].replaceAll("[^0-9]+","");
            int num = Integer.parseInt(coms[2]);
            System.out.println("e"+num);
            //place "num" enemies
            newLev.generateEnemies(num);
          }
          for (int j=3;j<coms.length;j++){
            if(coms[j].contains(":")){
              if(coms[j].contains("treasure:")){
                coms[j] = coms[j].replaceAll("[^0-9]+","");
                int num = Integer.parseInt(coms[j]);
                //place "num" treasure
                System.out.println("t"+num);
                newLev.generateTreasure(num);
              }else if(coms[j].contains("armor:")){
                coms[j] = coms[j].replaceAll("[^0-9]+","");
                int num = Integer.parseInt(coms[j]);
                //place "num" armor
                System.out.println("a"+num);
                newLev.generateArmor(num);
              }else if(coms[j].contains("weapon:")){
                coms[j] = coms[j].replaceAll("[^0-9]+","");
                int num = Integer.parseInt(coms[j]);
                //place "num" weapon
                System.out.println("w"+num);
                newLev.generateWeapons(num);
              }
            }
          }        
            world.add(newLev);
        }else if(coms[0].equals("end") && coms[1].equals("keylock")){   //keylock end
            int wid = (int) (Math.random() * 50) + 10;
            int hei = (int) (Math.random() * 50) + 10;
            Level newLev = new Level(wid, hei);
          //build keylock end
          System.out.println("end, keylock");
            newLev.generateStairsAndEndKeyLock();
            newLev.generateWallsKeyLock();
          if(coms[2].contains(":")){
            coms[2] = coms[2].replaceAll("[^0-9]+","");
            int num = Integer.parseInt(coms[2]);
            //place "num" enemies
            newLev.generateEnemies(num);
          }
          for (int j=3;j<coms.length;j++){
            if(coms[j].contains(":")){
              if(coms[j].contains("treasure:")){
                coms[j] = coms[j].replaceAll("[^0-9]+","");
                int num = Integer.parseInt(coms[j]);
                //place "num" treasure
                System.out.println("t"+num);
                newLev.generateTreasure(num);
              }else if(coms[j].contains("armor:")){
                coms[j] = coms[j].replaceAll("[^0-9]+","");
                int num = Integer.parseInt(coms[j]);
                //place "num" armor
                System.out.println("a"+num);
                newLev.generateArmor(num);
              }else if(coms[j].contains("weapon:")){
                coms[j] = coms[j].replaceAll("[^0-9]+","");
                int num = Integer.parseInt(coms[j]);
                //place "num" weapon
                System.out.println("w"+num);  
                newLev.generateWeapons(num);
              }
            }
          }        
            world.add(newLev);
        }else if(coms[0].equals("end") && coms[1].equals("maze")){     //maze end
            int wid = (int) (Math.random() * 50) + 50;
            int hei = (int) (Math.random() * 50) + 50;
            Level newLev = new Level(wid, hei);
          //build maze room
          System.out.println("end, maze");
            newLev.generateStairsAndEnd();
            newLev.generateWalls();
          if(coms[2].contains(":")){
            coms[2] = coms[2].replaceAll("[^0-9]+","");
            int num = Integer.parseInt(coms[2]);
            System.out.println("e"+num);
            //place "num" enemies
            newLev.generateEnemies(num);
          }
          for (int j=3;j<coms.length;j++){
            if(coms[j].contains(":")){
              if(coms[j].contains("treasure:")){
                coms[j] = coms[j].replaceAll("[^0-9]+","");
                int num = Integer.parseInt(coms[j]);
                //place "num" treasure
                System.out.println("t"+num);
                newLev.generateTreasure(num);
              }else if(coms[j].contains("armor:")){
                coms[j] = coms[j].replaceAll("[^0-9]+","");
                int num = Integer.parseInt(coms[j]);
                //place "num" armor
                System.out.println("a"+num);
                newLev.generateArmor(num);
              }else if(coms[j].contains("weapon:")){
                coms[j] = coms[j].replaceAll("[^0-9]+","");
                int num = Integer.parseInt(coms[j]);
                //place "num" weapon
                System.out.println("w"+num);    
                newLev.generateWeapons(num);
              }
            }
          } 

           world.add(newLev);
        }
      }
    } catch(ArrayIndexOutOfBoundsException e) {
      System.err.println("No File");
    } catch(IOException e) {
      System.err.println(e.getMessage());
    }
  }
    
}
