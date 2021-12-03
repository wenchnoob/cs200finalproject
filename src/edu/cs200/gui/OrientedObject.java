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

    public boolean isInBounds(int x1, int y1, int x2, int y2) {
        // Checks if top left vertex of the boundary box is within the bounds specified.
        boolean lrInView = xPos < x1 && xPos > x2;
        boolean tbInView = yPos > y1 && yPos < x1;
        boolean topLeftInBounds = lrInView && tbInView;

        // Check if bottom right vertex of the boundary box is within the bounds specified.
        boolean rlInView = xPos2 < x2 && xPos2 > x2;
        boolean btInView = yPos2 > y2 && yPos2 < x2;
        boolean bottomRightInBounds = btInView && rlInView;

        return topLeftInBounds && bottomRightInBounds;
    }

    public void changeOrientation(int newOrientation) {
        while(orientation != newOrientation) rotateRight902();
        System.out.println();
    }

    private void rotateRight902() {
        rotate(-90);
        orientation = (orientation + 1) % 4;
        System.out.println("Rotating Right to  " + orientation);
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

    private void rotateRight90() {
        int temp;
        switch (orientation) {
            case NORTH:
                temp = this.width;
                this.width = this.height;
                this.height = temp;
                orientation = EAST;
                break;
            case EAST:
                temp = this.width;
                this.width = this.height;
                this.height = temp;
                this.yPos += this.height;
                orientation = SOUTH;
                break;
            case SOUTH:
                temp = this.width;
                this.width = this.height;
                this.height = temp;
                orientation = WEST;
                break;
            case WEST:
                temp = this.width;
                this.width = this.height;
                this.height = temp;
                this.yPos -= this.height;
                orientation = NORTH;
        }
    }
}
