package edu.cs200.gui.components.entities;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashSet;

import javax.swing.JOptionPane;

import edu.cs200.gui.components.DrawableObject;
import edu.cs200.gui.components.Weapon;
import edu.cs200.gui.components.Window;
import edu.cs200.GameObject;
import edu.cs200.gui.utils.Persisted;
import edu.cs200.gui.pages.Bag;
import edu.cs200.gui.pages.Map;

public class Player extends Entity implements Persisted {

    private static final Long serialVersionUID = 1L;

    private Weapon equippedWeapon;

    private static int START_X = 100;
    private static int START_Y = 300;
    private static int DIM_X = 20;
    private static int DIM_Y = 20;

    private transient static Player self;

    public static Player getInstance() {
        if (self == null) self = new Player();
        return self;
    }
    private Player() {
        super("player", 0, 0, DIM_X, DIM_Y, 90, 100, 5, 5, EAST);

    }

    public void paint(Graphics g) {
        paintWithOffset(g, 0, 0);
    }

    public void heal(int amount) {
        setHealth(getHealth() + amount);
        if (getHealth() > this.getMaxHealth()) setHealth(getMaxHealth());
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
        if (START_X < o.getxPos() + xPos + o.getWidth() &&
                START_X + width > o.getxPos() + xPos &&
                START_Y < o.getyPos() + yPos + o.getHeight() &&
                START_Y + height > o.getyPos() + yPos) return true;
        return false;
    }

    @Override
    public int attack() {
        return 0;
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
     * thrust 1
     * slash 2
     * dodge 3
     * parry 4
     * @param playerAttack gets the type of attack the player chose
     * @param enemy the enemy that is being attacked
     */


    public int attack(int playerAttack, Enemy enemy) {
    	int enemyAttack = enemy.attack(0,enemy);//gets the type of attack the enemy will use
    	int enemyDamage = enemy.getAttackDmg();
    	int enemyDefence = enemy.getDefence();
    	int enemyHealth = enemy.getHealth();
    	String enemyAttackType = enemy.getAttackType(enemyAttack);
    	int playerDamage = this.getAttackDmg();
    	int playerDefence = this.getDefence();
    	int playerHealth = this.getHealth();
    	String playerAttackType = this.getAttackType(playerAttack);
    	String result = "";
    	String message = "";
    	if(enemy.isDidDodge()) {
    		enemyDamage = enemyDamage *2;
    		enemy.setDidDodge(false);
    	}
    	if(this.isDidDodge()) {
    		playerDamage = playerDamage * 2;
    		this.setDidDodge(false);
    	}
    	
        if ((enemyAttack == 1 || enemyAttack == 2) && (playerAttack== 1 || playerAttack == 2)) {//if both are attacks both take damage
        	if(playerDamage - enemyDefence > 0)
        	enemy.setHealth(enemyHealth -(playerDamage - enemyDefence));
        	else {
        		enemy.setHealth(enemyHealth-1);
        	}
        	if(enemyDamage - playerDefence>0)
        		this.setHealth(playerHealth-1);
        	else {
        	this.setHealth(playerHealth - 1);
        	}
        	result = "you both took damage!";
        }
        else if((enemyAttack == 1 && playerAttack == 4)||(enemyAttack == 2 && playerAttack == 3)) {//Player misses defensive skill
        	if(enemyDamage-playerDefence > 0)
        	this.setHealth(playerHealth - (enemyDamage- playerDefence));
        	else {
        		this.setHealth(playerHealth-1);
        	}
        	result = "you took damage!";
        }
        else if((playerAttack == 1 && enemyAttack == 4)||(playerAttack == 2 && enemyAttack == 3)) {//enemy misses defensive skill
        	if(playerDamage-enemyDefence>0)
        	enemy.setHealth(enemyHealth - (playerDamage - enemyDefence));
        	else {
        		enemy.setHealth(enemyHealth-1);
        	}
        	result = "the enemy took damage!";
        }
        else if((enemyAttack == 1 && playerAttack == 3)|| (enemyAttack == 2 && playerAttack ==4)) {//player dodged enemy attack
        	this.setDidDodge(true);
        	result = "you countered the enemy attack! Your next attack will do double damage!!";
        }
        else if((playerAttack == 1 && enemyAttack == 3)||(playerAttack == 2 && enemyAttack == 4)) {//enemy dodged player attack
        	enemy.setDidDodge(true);
        	result = "the enemy countered your attack! Their next attack will do double damage!!";
        }
        else {
        	result = "Both you and your enemy countered.  Nothing happens";
        }
        message = "You used a " + playerAttackType + " and your enemy used a "+ enemyAttackType + " meaning " + result;
        JOptionPane.showMessageDialog(Window.getInstance().getFrame() , message);
        return 0;
    }

    public void levelup(int xp) {
    	//potential method for realism do not make a priority rn
    }


    @Override
    public boolean load(ObjectInputStream in) {
        try {
            self = (Player) in.readObject();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void resetPlayer() {
        self = new Player();
    }
}
