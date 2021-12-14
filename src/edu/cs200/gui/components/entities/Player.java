package edu.cs200.gui.components.entities;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashSet;

import javax.swing.*;

import edu.cs200.Game;
import edu.cs200.gui.components.DrawableObject;
import edu.cs200.gui.components.GameObject;
import edu.cs200.gui.components.Weapon;
import edu.cs200.gui.components.Window;
import edu.cs200.gui.utils.Persisted;
import edu.cs200.gui.pages.Bag;
import edu.cs200.gui.pages.Map;
import edu.cs200.utils.MusicPlayer;

public class Player extends Entity implements Persisted {

    private static final Long serialVersionUID = 1L;

    private Weapon equippedWeapon;

    private static int START_X = 100;
    private static int START_Y = 300;
    private static int DIM_X = 20;
    private static int DIM_Y = 20;

    private int velocityX;
    private int velocityY;

    private Timer timer;

    private transient static Player self;

    public static Player getInstance() {
        if (self == null) self = new Player();
        return self;
    }

    private Player() {
        super("Player", 0, 0, DIM_X, DIM_Y, 90, 100, 7, 3, EAST);

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

    public void reset() {
        this.xPos = 0;
        this.yPos = 0;
    }

    public boolean checkAllCollision() {
        HashSet<DrawableObject> others = Map.getInstance().getCurrentRoom().getObjects();
        for (DrawableObject obj : others) {
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
        return true;
    }

    public boolean moveDown() {
        if (checkAllCollision()) return false;
        if (orientation != SOUTH) {
            this.orientation = SOUTH;
            return true;
        }
        this.yPos -= 5;
        if (checkAllCollision()) this.yPos += 5;
        return true;
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
        if (orientation == NORTH)
            g.fillPolygon(new int[]{START_X, START_X + DIM_X / 2, START_X + DIM_X}, new int[]{START_Y + DIM_Y, START_Y, START_Y + DIM_Y}, 3);
        else if (orientation == EAST)
            g.fillPolygon(new int[]{START_X, START_X + DIM_X, START_X}, new int[]{START_Y, START_Y + DIM_Y / 2, START_Y + DIM_Y}, 3);
        else if (orientation == SOUTH)
            g.fillPolygon(new int[]{START_X, START_X + DIM_X / 2, START_X + DIM_X}, new int[]{START_Y, START_Y + DIM_Y, START_Y}, 3);
        else
            g.fillPolygon(new int[]{START_X + DIM_X, START_X, START_X + DIM_X}, new int[]{START_Y, START_Y + DIM_Y / 2, START_Y + DIM_Y}, 3);
    }

    @Override
    public void die() {
        super.die();
        Game.stop();
        MusicPlayer.getInstance().playGameOver();
        JOptionPane.showMessageDialog(Window.getInstance().getFrame(), "You have died! RIP", "DEAD", JOptionPane.INFORMATION_MESSAGE);
        Player.getInstance().resetPlayer();
        Bag.getInstance().resetBag();
        Map.getInstance().resetMap();
        Window.getInstance().reset();
        Game.startGame();
        Window.getInstance().goTo("Home");
    }

    @Override
    public boolean handleCollision(DrawableObject targ) {
        return false;
    }

    public int getXOffset() {
        return -xPos;
    }

    public int getYOffset() {
        return -yPos;
    }

    public void equip(Weapon equipWeapon) {
        if (this.equippedWeapon != null) {
            this.setAttackDmg(this.getAttackDmg() - this.equippedWeapon.getValue());
            Bag.getInstance().addItem(equippedWeapon);
        }

        this.equippedWeapon = equipWeapon;
        this.setAttackDmg(this.getAttackDmg() + this.equippedWeapon.getValue());
    }

    /**
     * This method is the player Version of the attack method in the Entity class
     * thrust 1
     * slash 2
     * dodge 3
     * parry 4
     *
     * @param playerAttack gets the type of attack the player chose
     * @param enemy        the enemy that is being attacked
     */
    public String attackR(int playerAttack, int enemyAttack, Enemy enemy) {
        int enemyDamage = enemy.getAttackDmg();
        String enemyAttackType = enemy.getAttackType(enemyAttack);
        int playerDamage = this.getAttackDmg();
        String playerAttackType = this.getAttackType(playerAttack);
        String result;
        String message;

        if (enemy.isDidDodge()) {
            enemyDamage = enemyDamage * 2;
            enemy.setDidDodge(false);
        }

        if (this.isDidDodge()) {
            playerDamage = playerDamage * 2;
            this.setDidDodge(false);
        }

        if (isOffense(enemyAttack) && isOffense(playerAttack)) {//if both are attacks both take damage
            enemy.damage(playerDamage );
            this.damage(enemyDamage);
            result = "You both took damage!";
        } else if (countered(enemyAttack, playerAttack)) {//Player misses defensive skill
            this.setDidDodge(true);
            enemy.damage(playerDamage);
            result = "You countered, The enemy took damage!";
        } else if (countered(playerAttack, enemyAttack)) {//enemy misses defensive skill
            enemy.setDidDodge(true);
            this.damage(enemyDamage);
            result = "Enemy Counter, You took damage! ";
        } else if (unrelated(enemyAttack, playerAttack)) {
            result = "Lol, you both missed!";
        } else {
            result = "Counter x Counter -- Nothing happens";
        }
        message = "You: " + playerAttackType + "    " +
                "Enemy: " + enemyAttackType + "    --- " + result;
        return message;
    }

    private boolean isOffense(int attack) {
        return attack == Entity.THRUST || attack == Entity.SLASH;
    }

    private boolean unrelated(int a1, int a2) {
        return (a1 == Entity.THRUST && a2 == Entity.DODGE) || (a1 == Entity.SLASH && a2 == Entity.PARRY)
                || (a2 == Entity.THRUST && a1 == Entity.DODGE) || (a2 == Entity.SLASH && a1 == Entity.PARRY);
    }

    private boolean countered(int a1, int a2) {
        return (a1 == Entity.THRUST && a2 == Entity.PARRY) || (a1 == Entity.SLASH && a2 == Entity.DODGE);
    }

    private boolean isDefense(int attack) {
        return attack == Entity.DODGE || attack == Entity.PARRY;
    }

    public int attack(int a, Enemy e) { return 0; }

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
