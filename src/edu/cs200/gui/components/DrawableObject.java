package edu.cs200.gui.components;

import java.awt.*;
import java.io.PrintWriter;

import edu.cs200.GameObject;

public abstract class DrawableObject extends GameObject {

    protected int xPos, yPos, xPos2, yPos2, width, height;

    public DrawableObject(String name, int xPos, int yPos, int width, int height) {
        super(name);
        this.xPos = xPos;
        this.yPos = yPos;
        this.xPos2 = xPos + width;
        this.yPos2 = yPos + height;
        this.width = width;
        this.height = height;
    }

    public DrawableObject(String[] props) {
        this(props[1], parse(props[2]), parse(props[3]), parse(props[4]), parse(props[5]));
    }

    protected static int parse(String val) {
        return Integer.valueOf(val);
    }

    public abstract void paintWithOffset(Graphics g, int xOffset, int yOffset);

    public abstract boolean handleCollision(GameObject targ);

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

    public void save(PrintWriter out) {
        out.write(String.format("%s,%s,%s,%s,%s,%s;", xPos, yPos, xPos2, yPos2, width, height));
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public int getxPos2() {
        return xPos2;
    }

    public void setxPos2(int xPos2) {
        this.xPos2 = xPos2;
    }

    public int getyPos2() {
        return yPos2;
    }

    public void setyPos2(int yPos2) {
        this.yPos2 = yPos2;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
