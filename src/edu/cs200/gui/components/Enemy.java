package edu.cs200.gui.components;

import edu.cs200.Entity;
import edu.cs200.GameObject;
import edu.cs200.gui.pages.Combat;
import edu.cs200.gui.pages.Map;

import java.awt.*;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Random;
import java.util.function.Consumer;

import static edu.cs200.util.Helpers.goTo;

public class Enemy extends Entity {
    private double aggressionModifier;
    private int attackTypeModifier;

    private Consumer<Integer> changeX = (Consumer<Integer> & Serializable) (amount -> {


        xPos += amount;
        if (this.checkAllCollision()) xPos -= amount;
    });

    private Consumer<Integer> changeY = (Consumer<Integer> & Serializable) (amount -> {
        yPos += amount;
        if (this.checkAllCollision()) yPos -= amount;
    });

    public Enemy(int xPos, int yPos, int width, int height, int health, int maxHealth, int attackDamage, int defence, int orientation) {
        super(xPos, yPos, width, height, health, maxHealth ,attackDamage, defence, orientation);
        if(this.getMax_health()==0) {
        	this.setMax_health(100);
        }
    }

    public Enemy(String[] props) {
        super(props);
        //animate(0);
    }

    public boolean checkAllCollision() {
        HashSet<DrawableObject> others = Map.getInstance().getCurrentRoom().getObjects();
        for (DrawableObject obj: others) {
            if (!Map.getInstance().isInBounds(obj)) continue;
            if (!(obj instanceof Enemy) && collides(obj)) {
                obj.handleCollision(this);
                return true;
            }
        }
        return false;
    }

    public void animate(int speed) {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5 - speed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Random rand = new Random();
                int axis = rand.nextInt() % 2;
                if (axis == 0) {
                    changeX.accept(rand.nextInt(11) - 5);
                } else changeY.accept(rand.nextInt(11) - 5);
                Map.getInstance().redraw(xPos, yPos, width, height);
            }
        }).start();
    }

    @Override
    public void paintWithOffset(Graphics g, int xOffset, int yOffset) {
        g.setColor(Color.RED);
        int xPos = this.xPos - xOffset;
        int yPos = this.yPos - yOffset;
        if (orientation == NORTH) g.fillPolygon(new int[]{xPos, xPos + width/2, xPos + width}, new int[]{yPos + width, yPos, yPos + width}, 3);
        else if (orientation == EAST) g.fillPolygon(new int[]{xPos, xPos + width, xPos}, new int[]{yPos, yPos + width/2, yPos + width}, 3);
        else if (orientation == SOUTH) g.fillPolygon(new int[]{xPos, xPos + width/2, xPos + width}, new int[]{yPos, yPos + width, yPos}, 3);
        else g.fillPolygon(new int[]{xPos + width, xPos, xPos + width}, new int[]{yPos, yPos + width/2, yPos + width}, 3);
    }

    @Override
    public boolean handleCollision(GameObject targ) {
        if (targ instanceof Player) {
            Combat.getInstance().setCurrent_enemy(this);
            goTo("Combat");
        }
        return false;
    }

    @Override
    public void save(PrintWriter out) {
        super.save(out);
    }
	public double getAggressionModifier() {
		return aggressionModifier;
	}

	public void setAggressionModifier(double aggressionModifier) {
		this.aggressionModifier = aggressionModifier;
	}

	@Override
	/**
	 * defence is higher value than aggression modifier
	 * thrust and dodge are lower than modifier
	 * thrust is 1
	 * slash is 2
	 * dodge is 3
	 * parry is 4
	 */
	public int attack(int a, Entity enemy) {
		Random randAttack = new Random();
		int attackType = randAttack.nextInt(100);
	    double attackOrDefend = this.getAggressionModifier();
	    int thrustOrSlash = this.getAttackTypeModifier();
	    if(attackOrDefend < attackType) {
	    	if(thrustOrSlash < attackType) {
	    		return 1;
	    	}
	    	else {
	    		return 2;
	    	}
	    }
	    else {
	    	if(attackType > 50) {
	    		return 3;
	    	}
	    	else {
	    		return 4;
	    	}
	    }

	}

	public int getAttackTypeModifier() {
		return attackTypeModifier;
	}

	public void setAttackTypeModifier(int attackTypeModifier) {
		this.attackTypeModifier = attackTypeModifier;
	}
}
