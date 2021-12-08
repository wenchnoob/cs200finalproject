package edu.cs200;
import java.util.*;

import edu.cs200.gui.DrawableObject;
import edu.cs200.gui.Enemy;
import edu.cs200.gui.Map;
import edu.cs200.gui.OrientedObject;

import java.awt.Graphics;
import java.io.*;
public abstract class Entity extends OrientedObject {
	
	
public Entity(int xPos, int yPos, int width, int height, int health, int attackDmg, int defence, int orientation) {
		super(xPos, yPos, width, height,orientation);
		this.health = health;
		this.attackDmg = attackDmg;
		this.defence = defence;
		// TODO Auto-generated constructor stub
	}

	public Entity(String[] props) {
	super(props);
	this.health = Integer.parseInt(props[6]);
	this.attackDmg = Integer.parseInt(props[7]);
	this.defence = Integer.parseInt(props[8]);
	}

	public boolean checkAllCollision() {
		HashSet<DrawableObject> others = Map.getInstance().getCurrentRoom().getObjects();
		for (DrawableObject obj: others) {
			if (collides(obj)) {
				obj.handleCollision(this);
				return true;
			}
		}
		return false;
	}

	public boolean collides(DrawableObject o) {
		if (xPos < o.getxPos() + o.getWidth() &&
				xPos + width > o.getxPos() &&
				yPos < o.getyPos() + o.getHeight() &&
				yPos + height > o.getyPos()) return true;
		return false;
	}



private int health;
private int attackDmg;
private int defence;

public abstract int attack();




/**
 * @return the health
 */
public int getHealth() {
	return health;
}

/**
 * @return the attackDmg
 */
public int getAttackDmg() {
	return attackDmg;
}

/**
 * @param health the health to set
 */
public void setHealth(int health) {
	this.health = health;
}

/**
 * @param attackDmg the attackDmg to set
 */
public void setAttackDmg(int attackDmg) {
	this.attackDmg = attackDmg;
}

public void setDefence(int defence) {
	this.defence = defence;
}

public int getDefence() {
	return this.defence;
}

public boolean isAlive() {
	return this.health > 0;
}


public void die(){
//TODO finish this
}




}