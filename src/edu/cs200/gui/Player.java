package edu.cs200.gui;

import java.awt.*;
import java.util.HashSet;

import edu.cs200.Entity;

public class Player extends Entity {
	private HashSet<Item> inventory = new HashSet<Item>();
	private Weapon equippedWeapon;

    private static int START = 400;
    private static Player self;

    public static Player getInstance() {
        if (self == null) self = new Player();
        return self;
    }
    private Player() {
        super(START, START, 20, 20, EAST);
    }

    public void paint(Graphics g) {
        paintWithOffset(g, 0, 0);
    }

    public boolean moveUp() {
        this.yPos -= 5;
        this.yPos2 -= 5;
        changeOrientation(NORTH);
        return true;
    }

    public boolean moveRight() {
        this.xPos += 5;
        this.xPos2 += 5;
        changeOrientation(EAST);
        return  true;
    }

    public boolean moveDown() {
        this.yPos += 5;
        this.yPos2 += 5;
        changeOrientation(SOUTH);
        return  true;
    }

    public boolean moveLeft() {
        this.xPos -= 5;
        this.xPos2 -= 5;
        changeOrientation(WEST);
        return true;
    }

    @Override
    public void paintWithOffset(Graphics g, int xOffset, int yOffset) {
        if (!isInBounds(0, 0, Map.CANVAS_WIDTH, Map.CANVAS_HEIGHT)) return;
        System.out.println("in bounds");
        g.setColor(Color.WHITE);
        //g.fillRect(xPos, yPos, width, height);
        if (orientation == NORTH) g.fillPolygon(new int[]{xPos, xPos + width/2, xPos + width}, new int[]{yPos + height, yPos, yPos + height}, 3);
        else if (orientation == EAST) g.fillPolygon(new int[]{xPos, xPos + width, xPos}, new int[]{yPos, yPos + height/2, yPos + height}, 3);
        else if (orientation == SOUTH) g.fillPolygon(new int[]{xPos, xPos + width/2, xPos + width}, new int[]{yPos, yPos + height, yPos}, 3);
        else g.fillPolygon(new int[]{xPos + width, xPos, xPos + width}, new int[]{yPos, yPos + height/2, yPos + height}, 3);
    }

    public int getXOffset() {
        return xPos - START;
    }

    public int getYOffset() {
        return  yPos - START;
    }
    public void equip(Weapon equipWeapon){
    	if(this.equippedWeapon != null)
    		this.setAttackDmg(this.getAttackDmg()- this.equippedWeapon.getValue());
    	this.equippedWeapon = equipWeapon;
    	this.setAttackDmg(this.getAttackDmg()+ this.equippedWeapon.getValue());
    }

    public void pickup(Item newItem){
    inventory.add(newItem);
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


    public void useItem(Item used) {
    	String itemType = used.getType();
    	int itemValue = used.getValue();
    	if(itemType.equals("Health"))
    		this.setHealth(this.getHealth()+itemValue);
    	else {
    		this.setAttackDmg(this.getAttackDmg()+ itemValue);
    	}
    	inventory.remove(used);
    }

    public void levelup(int xp) {
    	//potential method for realism do not make a priority rn
    }
   
}
