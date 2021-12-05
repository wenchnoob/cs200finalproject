package edu.cs200;
import java.util.*;

import edu.cs200.gui.DrawableObject;
import edu.cs200.gui.OrientedObject;

import java.awt.Graphics;
import java.io.*;
public abstract class Entity extends OrientedObject{
	
	
public Entity(int xPos, int yPos, int width, int height, int orientation) {
		super(xPos, yPos, width, height,orientation);
		// TODO Auto-generated constructor stub
	}



private int health;
private int attackDmg;


/**
 * This method determines damage based on the attack the other entity made.
 * the three attack types beat one lose to one and tie the same type.
 * Riposte beats slash
 * Slash beats thrust
 * thrust beats riposte
 * thrust = 1
 * riposte = 2
 * slash = 3
 * 
 */

public int attack() {
		Random attk = new Random();
		int chooseAttack = attk.nextInt(3);
	return chooseAttack;
	}



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



public void die(){
//TODO finish this
}




}