public class Player {
    int maxHealth;
    int health;
    int strength;
    int playerx, playery, playerz;
    Weapon weapon;
    Armor armor;
    boolean dead;
    int healthRegen;
    
    public Player(int h, int s, Weapon w, Armor a){
        this.maxHealth = h;
        this.health = h;
        strength = s;
        this.weapon = w;
        this.armor = a;
        dead = false;
        playerx = -1;
        playery = -1;
        playerz = -1;
        healthRegen = 0;
    }
    
    public int getX(){
        return playerx;
    }
    
    public int getY(){
        return playery;
    }
    
    public int getZ(){
        return playerz;
    }
    
    public int getHealth(){
        return health;
    }
    
    public Weapon getWeapon(){
        return weapon;
    }
    
    public Armor getArmor(){
        return armor;
    }
    
    public void setHealth(int h){
        health = h;
    }
    
    public void setWeapon(Weapon w){
        weapon = w;
    }
    
    public void setArmor(Armor a){
        armor = a;
    }
    
    public void setX(int x){
        playerx = x;
    }
    
    public void setY(int y){
        playery = y;
    }
    
    public void setZ(int z){
        playerz = z;
    }
    
    public void attack(Enemy e){
        int damage = strength;
        int weaponCheck = (int) (Math.random() * 2);
        if (weaponCheck == 0){
            if (weapon != null){
                damage += weapon.getAttack();
            }
        }
        e.setHealth(e.getHealth() - damage);
        System.out.println("Attacking " + e.getHealth());
        if (e.getHealth() <= 0){
            e.die();
        }
    }
    
    public void die(){
        dead = true;
    }
    
    public void regen(){
        if (++healthRegen == 10){
            healthRegen = 0;
            if (health < maxHealth){
                health++;
                System.out.println("health " + health);
            }
        }
    }
}