package edu.cs200.gui;


public abstract class OrientedObject extends DrawableObject {

    protected int orientation;

    public static final int NORTH = 0, EAST = 1, SOUTH = 2, WEST = 3;

    public OrientedObject(int xPos, int yPos, int width, int height, int orientation) {
        super(xPos, yPos, width, height);
        this.orientation = EAST;
        if (!(orientation == NORTH || orientation == EAST || orientation == SOUTH || orientation == WEST))
            throw new IllegalArgumentException("Invalid orientation");
        changeOrientation(orientation);
    }

    public void changeOrientation(int newOrientation) {
        while(orientation != newOrientation) rotateRight90();
    }

    private void rotateRight90() {
        rotate(Math.toRadians(-90));
        orientation = (orientation + 1) % 4;
    }

    public void rotate(double angle) {
        double xCenter = (xPos2 + xPos)/2.0;
        double yCenter = (yPos2 + yPos)/2.0;

        double newXPos = xPos - xCenter;
        double newXPos2 = xPos2 - xCenter;

        double newYPos = yPos - yCenter;
        double newYPos2 = yPos2 - yCenter;


        double rotatedXPos = newXPos * Math.cos(angle) - newYPos * Math.sin(angle);
        double rotatedYPos = newYPos * Math.cos(angle) + newXPos * Math.sin(angle);

        double rotatedXPos2 = newXPos2 * Math.cos(angle) - newYPos2 * Math.sin(angle);
        double rotatedYPos2 = newYPos2 * Math.cos(angle) + newXPos2 * Math.sin(angle);

        rotatedXPos += xCenter;
        rotatedXPos2 += xCenter;

        rotatedYPos += yCenter;
        rotatedYPos2 += yCenter;


        this.xPos = (int) Math.min(rotatedXPos, rotatedXPos2);
        this.xPos2 = (int) Math.max(rotatedXPos, rotatedXPos2);
        this.yPos = (int) Math.min(rotatedYPos, rotatedYPos2);
        this.yPos2 = (int) Math.max(rotatedYPos, rotatedYPos2);

        this.width = this.xPos2 - this.xPos;
        this.height = this.yPos2 - this.yPos;
    }

}
