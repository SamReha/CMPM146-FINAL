import java.awt.Image;

public class Armor {
    int defense;
    String name;
    String bodyPart;
    int xpos, ypos, zpos;
    Image image;
    
    public Armor(String n, String bp, int d, int x, int y, int z, Image img){
        name = n;
        bodyPart = bp;
        defense = d;
        xpos = x;
        ypos = y;
        zpos = z;
        image = img;
    }
    
    public String getName(){
        return name;
    }
    
    public String getBodyPart(){
        return bodyPart;
    }
    
    public int getDefense(){
        return defense;
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
    
    public Image getImage(){
        return image;
    }
    
    public void setName(String n){
        name = n;
    }
    
    public void setDefense(int d){
        defense = d;
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