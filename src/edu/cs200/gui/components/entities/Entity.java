package edu.cs200.gui.components.entities;

import edu.cs200.gui.components.DrawableObject;
import edu.cs200.gui.pages.Map;
import edu.cs200.gui.components.OrientedObject;
import edu.cs200.gui.components.utils.Observable;
import edu.cs200.gui.components.utils.Observer;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public abstract class Entity extends OrientedObject implements Observable {

    private List<Observer> observers;
    private int health;
    private int attackDmg;
    private int defence;
    private int maxHealth;
    private boolean didDodge = false;


    public Entity(String name, int xPos, int yPos, int width, int height, int health, int maxHealth, int attackDmg, int defence, int orientation) {
        super(name, xPos, yPos, width, height, orientation);
        this.health = health;
        this.attackDmg = attackDmg;
        this.defence = defence;
        this.maxHealth = maxHealth;
    }

    public Entity(String[] props) {
        super(props);
        this.health = Integer.parseInt(props[7]);
        this.maxHealth = Integer.parseInt(props[8]);
        this.attackDmg = Integer.parseInt(props[9]);
        this.defence = Integer.parseInt(props[10]);
    }

    public boolean checkAllCollision() {
        HashSet<DrawableObject> others = Map.getInstance().getCurrentRoom().getObjects();
        for (DrawableObject obj : others) {
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

    public void damage(int amount) {
        int dmg = amount - getDefence();
        if (dmg < 0) return;
        setHealth(getHealth() - dmg);
    }

    public abstract int attack(int i, Enemy e);


    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
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

    public String getAttackType(int attack) {
        if (attack == 1) {
            return "Thrust";
        } else if (attack == 2) {
            return "Slash";
        } else if (attack == 3) {
            return "Dodge";
        } else {
            return "Parry";
        }
    }

    public boolean isAlive() {
        return this.health > 0;
    }


    public void die() {
        Map.getInstance().getCurrentRoom().removeObject(this);
    }

    public boolean isDidDodge() {
        return didDodge;
    }

    public void setDidDodge(boolean didDodge) {
        this.didDodge = didDodge;
    }

    @Override
    public void addObserver(Observer observer) {
        if (observers == null) observers = new LinkedList<>();
        System.out.println("adding observer to " + this.getName());
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        if (observers != null)
            for (Observer obs : observers) obs.getUpdate(this);
    }
}