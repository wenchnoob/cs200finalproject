package edu.cs200.gui;

import edu.cs200.Entity;
import edu.cs200.GameObject;

import java.awt.*;
import java.util.HashSet;
import java.util.Random;
import java.util.function.UnaryOperator;

import static edu.cs200.util.Helpers.initFight;

public class Enemy extends Entity {

    private UnaryOperator<Integer> changeX = amount -> {
        xPos += amount;
        if (this.checkAllCollision()) xPos -= amount;
        return xPos;
    };

    private UnaryOperator<Integer> changeY = amount -> {
        yPos += amount;
        if (this.checkAllCollision()) yPos -= amount;
        return yPos;
    };

    public Enemy(int xPos, int yPos, int width, int height, int health, int attackDamage, int defence, int orientation) {
        super(xPos, yPos, width, height, health, attackDamage, defence, orientation);
    }

    public Enemy(String[] props) {
        super(props);
        animate(0);
    }

    public boolean checkAllCollision() {
        HashSet<DrawableObject> others = Map.getInstance().getCurrentRoom().getLocationDescription().getObjects();
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
                    changeX.apply(rand.nextInt(11) - 5);
                } else changeY.apply(rand.nextInt(11) - 5);
                Map.getInstance().redraw();
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
        if (targ instanceof Player) initFight(this);
        return false;
    }
}