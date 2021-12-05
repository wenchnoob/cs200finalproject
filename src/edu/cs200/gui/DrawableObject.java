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
}
