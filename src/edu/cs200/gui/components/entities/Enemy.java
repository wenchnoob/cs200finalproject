package edu.cs200.gui.components.entities;

import edu.cs200.gui.components.DrawableObject;
import edu.cs200.gui.components.GameObject;
import edu.cs200.gui.pages.Combat;
import edu.cs200.gui.pages.Map;

import java.awt.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Random;
import java.util.function.Consumer;


public abstract class Enemy extends Entity {

    private Consumer<Integer> changeX = (Consumer<Integer> & Serializable) (amount -> {
        xPos += amount;
        if (this.checkAllCollision()) xPos -= amount;
    });

    private Consumer<Integer> changeY = (Consumer<Integer> & Serializable) (amount -> {
        yPos += amount;
        if (this.checkAllCollision()) yPos -= amount;
    });


    public Enemy(String name, int xPos, int yPos, int width, int height, int health, int maxHealth, int attackDamage, int defence, int orientation) {
        super(name, xPos, yPos, width, height, health, maxHealth, attackDamage, defence, orientation);
        animate(4);
    }

    public Enemy(String[] props) {
        super(props);
        animate(4);
    }

    public boolean checkAllCollision() {
        HashSet<DrawableObject> others = Map.getInstance().getCurrentRoom().getObjects();
        for (DrawableObject obj : others) {
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
                    Thread.sleep(1000/60 - speed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Random rand = new Random();
                int axis = rand.nextInt() % 2;
                if (axis == 0) {
                    changeX.accept(rand.nextInt(11) - 5);
                } else changeY.accept(rand.nextInt(11) - 5);
            }
        }).start();
    }

    @Override
    public void paintWithOffset(Graphics g, int xOffset, int yOffset) {
        g.setColor(Color.RED);
        int xPos = this.xPos - xOffset;
        int yPos = this.yPos - yOffset;
        if (orientation == NORTH)
            g.fillPolygon(new int[]{xPos, xPos + width / 2, xPos + width}, new int[]{yPos + width, yPos, yPos + width}, 3);
        else if (orientation == EAST)
            g.fillPolygon(new int[]{xPos, xPos + width, xPos}, new int[]{yPos, yPos + width / 2, yPos + width}, 3);
        else if (orientation == SOUTH)
            g.fillPolygon(new int[]{xPos, xPos + width / 2, xPos + width}, new int[]{yPos, yPos + width, yPos}, 3);
        else
            g.fillPolygon(new int[]{xPos + width, xPos, xPos + width}, new int[]{yPos, yPos + width / 2, yPos + width}, 3);
    }

    @Override
    public boolean handleCollision(DrawableObject targ) {
        if (targ instanceof Player) {
            Combat.getInstance().setCurrentEnemy(this);
            notifyObservers();
            edu.cs200.gui.components.Window.getInstance().goTo("Combat");
        }
        return false;
    }

    public abstract double getAggressionModifier();


    /**
     * defence is higher value than aggression modifier
     * thrust and dodge are lower than modifier
     * thrust is 1
     * slash is 2
     * dodge is 3
     * parry is 4
     */
    public int attack(int a, Enemy enemy) {
        Random randAttack = new Random();
        int attackType = randAttack.nextInt(100);
        double attackOrDefend = getAggressionModifier();
        int thrustOrSlash = getAttackTypeModifier();
        if (attackOrDefend < attackType) {
            if (thrustOrSlash < attackType) {
                return Entity.SLASH;
            } else {
                return Entity.THRUST;
            }
        } else {
            if (attackType > attackOrDefend/2) {
                return Entity.DODGE;
            } else {
                return Entity.PARRY;
            }
        }

    }

    public abstract int getAttackTypeModifier();

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Enemy)) return false;
        Enemy dobj = (Enemy) obj;
        if (this.getName() == null) return dobj.getName() == null;
        return this.getName().equals(dobj.getName());//&& this.getClass().equals(obj.getClass());
    }
}
