package gameobjects;
import java.util.*;
import java.io.*;
public class Entity implements GameObject{
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