package edu.cs200.gui.components;

import java.awt.*;

public class Wall extends OrientedObject {

    private static final int WALL_HEIGHT = 5;

    public  Wall(int xPos, int yPos, int length, int orientation) {
        super("Wall", xPos, yPos, length, WALL_HEIGHT, orientation);
    }

    public Wall(String[] props) {
        super(props);
    }

    public void paintWithOffset(Graphics g, int xOffset, int yOffset) {
        g.setColor(Color.WHITE);
        g.fillRect(xPos - xOffset, yPos - yOffset, width, height);
    }

    @Override
    public boolean handleCollision(DrawableObject object) {
        return false;
    }


}
