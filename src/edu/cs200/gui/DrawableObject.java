package edu.cs200.gui;

import java.awt.*;

public abstract class DrawableObject {

    protected int xPos, yPos, xPos2, yPos2, width, height;

    public DrawableObject(int xPos, int yPos, int width, int height) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.xPos2 = xPos + width;
        this.yPos2 = yPos + height;
        this.width = width;
        this.height = height;
    }

    public abstract void paintWithOffset(Graphics g, int xOffset, int yOffset);

    public abstract boolean isInBounds(int x1, int y1, int x2, int y2);
}
