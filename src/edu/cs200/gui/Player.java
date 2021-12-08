package edu.cs200.gui;

import java.awt.*;
import java.io.PrintWriter;
import java.util.HashSet;

import edu.cs200.Entity;
import edu.cs200.GameObject;

public class Player extends Entity {
	private Weapon equippedWeapon;

    private static int START_X = 100;
    private static int START_Y = 300;
    private static int DIM_X = 20;
    private static int DIM_Y = 20;
    private static int max_health = 100;

    private static Player self;

    public static Player getInstance() {
        if (self == null) self = new Player();
        return self;
    }
    private Player() {
        super(0, 0, DIM_X, DIM_Y, 90, 5, 3, EAST);
    }

    public void paint(Graphics g) {
        paintWithOffset(g, 0, 0);
    }

    public void heal(int amount) {
        setHealth(getHealth() + amount);
        if (getHealth() > max_health) setHealth(max_health);
    }

    public void trueDamage(int amount) {
        if (getHealth() <= 0) return;
        if (amount < 0) return;
        setHealth(getHealth() - amount);
    }

    public void damage(int amount) {
        int dmg = amount - getDefence();
        if (dmg < 0) return;
        setHealth(getHealth() - dmg);
    }

    public void attackUp(int amount) {
        setAttackDmg(getAttackDmg() + amount);
    }

    public void defenceUp(int amount) {
        setDefence(getDefence() + amount);
    }

    public boolean collides(DrawableObject o) {
        if (START_X < o.xPos + xPos + o.width &&
                START_X + width > o.xPos + xPos &&
                START_Y < o.yPos + yPos + o.height &&
                START_Y + height > o.yPos + yPos) return true;
        return false;
    }

    public void reset() {
        this.xPos = 0;
        this.yPos = 0;
    }

    public boolean checkAllCollision() {
        HashSet<DrawableObject> others = Map.getInstance().getCurrentRoom().getObjects();
        for (DrawableObject obj: others) {
            if (!Map.getInstance().isInBounds(obj)) continue;
            if (collides(obj)) {
                obj.handleCollision(this);
                return true;
            }
        }

        return false;
    }

    public boolean moveUp() {
        if (checkAllCollision()) return false;
        if (orientation != NORTH) {
            this.orientation = NORTH;
            return true;
        }
        this.yPos += 5;
        if (checkAllCollision()) this.yPos -= 5;
        return true;
    }

    public boolean moveRight() {
        if (checkAllCollision()) return false;
        if (orientation != EAST) {
            this.orientation = EAST;
            return true;
        }
        this.xPos -= 5;
        if (checkAllCollision()) this.xPos += 5;
        return  true;
    }

    public boolean moveDown() {
        if (checkAllCollision()) return false;
        if (orientation != SOUTH) {
            this.orientation = SOUTH;
            return true;
        }
        this.yPos -= 5;
        if (checkAllCollision()) this.yPos += 5;
        return  true;
    }

    public boolean moveLeft() {
        if (checkAllCollision()) return false;
        if (orientation != WEST) {
           this.orientation = WEST;
           return true;
        }
        this.xPos += 5;
        if (checkAllCollision()) this.xPos -= 5;
        return true;
    }

    @Override
    public void paintWithOffset(Graphics g, int xOffset, int yOffset) {
        g.setColor(Color.WHITE);
        if (orientation == NORTH) g.fillPolygon(new int[]{START_X, START_X + DIM_X/2, START_X + DIM_X}, new int[]{START_Y + DIM_Y, START_Y, START_Y + DIM_Y}, 3);
        else if (orientation == EAST) g.fillPolygon(new int[]{START_X, START_X + DIM_X, START_X}, new int[]{START_Y, START_Y + DIM_Y/2, START_Y + DIM_Y}, 3);
        else if (orientation == SOUTH) g.fillPolygon(new int[]{START_X, START_X + DIM_X/2, START_X + DIM_X}, new int[]{START_Y, START_Y + DIM_Y, START_Y}, 3);
        else g.fillPolygon(new int[]{START_X + DIM_X, START_X, START_X + DIM_X}, new int[]{START_Y, START_Y + DIM_Y/2, START_Y + DIM_Y}, 3);
    }

    @Override
    public boolean handleCollision(GameObject targ) {
        return false;
    }

    public int getXOffset() {
        return -xPos;
    }

    public int getYOffset() {
        return -yPos;
    }

    public void equip(Weapon equipWeapon){
    	if(this.equippedWeapon != null) {
            this.setAttackDmg(this.getAttackDmg()- this.equippedWeapon.getValue());
            Bag.getInstance().addItem(equippedWeapon);
        }

    	this.equippedWeapon = equipWeapon;
    	this.setAttackDmg(this.getAttackDmg()+ this.equippedWeapon.getValue());
    }
    /**
     * This method is the player Version of the attack method in the Entity class
     * @param attackType gets the type of attack the player chose
     * @param enemy the enemy that is being attacked
     */


    public void attack(int attackType, Entity enemy) {
    	int enemyAttack = enemy.attack();//gets the type of attack the enemy will use
    	if(enemyAttack == attackType) {//if the attacks are the same nothing happens
    	}
    	else if((enemyAttack == 1 && attackType == 2) || (enemyAttack == 2 && attackType == 3)||(enemyAttack == 3 && attackType == 1)) {//if the enemy's attack trumps the players
    		this.setHealth(this.getHealth() - enemy.getAttackDmg());//player takes damage
    		if (this.getHealth()<= 0)//the player dies if its health is less than or equal to 0
    			this.die();
    	}
    	else if((enemyAttack == 2 && attackType == 1)||(enemyAttack==3&&attackType==2)||(enemyAttack == 1 && attackType == 3)) {// if the players attack trumps the enemy
    		enemy.setHealth(enemy.getHealth()-this.getAttackDmg());//enemy takes damage
    		if(enemy.getHealth()<=0)//if the enemies health is less than or equal to 0 it dies
    			enemy.die();
    	}
    }

    public void levelup(int xp) {
    	//potential method for realism do not make a priority rn
    }

    public void save(PrintWriter out) {
        out.write(String.format("Player,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s;", xPos, yPos, xPos2, yPos2, width, height, getHealth(), max_health, getAttackDmg(), getDefence()));
    }

    public void load(String in) {
        String[] props = in.split(",");
        xPos = Integer.valueOf(props[1]);
        yPos = Integer.valueOf(props[2]);
        xPos2 = Integer.valueOf(props[3]);
        yPos2 = Integer.valueOf(props[4]);
        width = Integer.valueOf(props[5]);
        height = Integer.valueOf(props[6]);
        setHealth(Integer.valueOf(props[7]));
        max_health = Integer.valueOf(props[8]);
        setAttackDmg(Integer.valueOf(props[9]));
        setDefence(Integer.valueOf(props[10]));
    }

}
