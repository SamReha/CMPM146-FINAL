import java.awt.Image;

public class Weapon {
    int attack;
    String name;
    int xpos, ypos, zpos;
    Image groundImage;
    Image wieldImage;
    
    public Weapon(String n, int a, int x, int y, int z, Image gimg, Image wimg){
        name = n;
        attack = a;
        xpos = x;
        ypos = y;
        zpos = z;
        groundImage = gimg;
        wieldImage = wimg;
    }
    
    public String getName(){
        return name;
    }
    
    public int getAttack(){
        return attack;
    }
    
    public int getX(){
        return xpos;
    }
    
    public int getY(){
        return ypos;
    }
    
    public int getZ(){
        return zpos;
    }
    
    public Image getGroundImage(){
        return groundImage;
    }
    
    public Image getWieldImage(){
        return wieldImage;
    }
    
    public void setName(String n){
        name = n;
    }
    
    public void setAttack(int a){
        attack = a;
    }
    
    public void setX(int x){
        xpos = x;
    }
    
    public void setY(int y){
        ypos = y;
    }
    
    public void setZ(int z){
        zpos = z;
    }
}