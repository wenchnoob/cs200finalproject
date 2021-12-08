package edu.cs200;

import edu.cs200.gui.DrawableObject;
import edu.cs200.gui.Map;
import edu.cs200.gui.OrientedObject;
import edu.cs200.util.Observable;
import edu.cs200.util.Observer;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public abstract class Entity extends OrientedObject implements Observable {

	private List<Observer> observers;
	
	
public Entity(int xPos, int yPos, int width, int height, int health, int attackDmg, int defence, int orientation) {
		super(xPos, yPos, width, height,orientation);
		this.health = health;
		this.attackDmg = attackDmg;
		this.defence = defence;
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
private int max_health = 100;


public abstract int attack();


	public int getMax_health() {
		return max_health;
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
	notifyObservers();
}

/**
 * @param attackDmg the attackDmg to set
 */
public void setAttackDmg(int attackDmg) {
	this.attackDmg = attackDmg;
	notifyObservers();
}

public void setDefence(int defence) {
	this.defence = defence;
	notifyObservers();
}

public int getDefence() {
	return this.defence;
}

public boolean isAlive() {
	return this.health > 0;
}


	public void die(){}

	@Override
	public void addObserver(Observer observer) {
		if (observers == null) observers = new LinkedList<>();
		observers.add(observer);
	}

	@Override
	public void notifyObservers() {
		if (observers != null)
			for (Observer obs: observers) obs.getUpdate(this);
	}
}