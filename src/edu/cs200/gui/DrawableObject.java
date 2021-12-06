package edu.cs200.gui;

import java.awt.*;

import edu.cs200.GameObject;

public abstract class DrawableObject extends GameObject{

    private static String name = "DRAWABLE_OBJECT";
    protected int xPos, yPos, xPos2, yPos2, width, height;

    public DrawableObject(int xPos, int yPos, int width, int height) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.xPos2 = xPos + width;
        this.yPos2 = yPos + height;
        this.width = width;
        this.height = height;
    }

    public DrawableObject(String[] props) {
        this(parse(props[1]), parse(props[2]), parse(props[3]), parse(props[4]));
    }

    private static int parse(String val) {
        return Integer.valueOf(val);
    }

    public abstract void paintWithOffset(Graphics g, int xOffset, int yOffset);

    public abstract boolean handleCollision();

    public boolean isInBounds(int x1, int y1, int x2, int y2) {
        // Checks if top left vertex of the boundary box is within the bounds specified.
        boolean lrInView = xPos > x1 && xPos < x2;
        boolean tbInView = yPos > y1 && yPos < y2;
        boolean topLeftInBounds = lrInView && tbInView;

        // Check if bottom right vertex of the boundary box is within the bounds specified.
        boolean rlInView = xPos2 > x1 && xPos2 < x2;
        boolean btInView = yPos2 > y1 && yPos2 < y2;
        boolean bottomRightInBounds = btInView && rlInView;

        return topLeftInBounds || bottomRightInBounds;
    }

    public String getName() {
        return this.name;
    }
}
