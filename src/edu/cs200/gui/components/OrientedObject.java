package edu.cs200.gui.components;


import java.io.PrintWriter;

public abstract class OrientedObject extends DrawableObject {

    protected int orientation;
    protected String rotation_type;

    public static final int NORTH = 0, EAST = 1, SOUTH = 2, WEST = 3;
    public static final String CENTER = "CENTER", TOP_LEFT = "TOP_LEFT";


    public OrientedObject(int xPos, int yPos, int width, int height, int orientation) {
        this(xPos, yPos, width, height, orientation, TOP_LEFT);
    }

    public OrientedObject(int xPos, int yPos, int width, int height, int orientation, String rotation_type) {
        super(xPos, yPos, width, height);
        this.rotation_type = rotation_type;
        orient(orientation);
    }

    private void orient(int orientation) {
        this.orientation = EAST;
        if (!(orientation == NORTH || orientation == EAST || orientation == SOUTH || orientation == WEST))
            throw new IllegalArgumentException("Invalid orientation");
        changeOrientation(orientation);
    }

    public OrientedObject(String[] props) {
        super(props);
        this.rotation_type = TOP_LEFT;
        orient(parseOrientation(props[5]));
    }


    private int parseOrientation(String orientation) {
        switch (orientation) {
            case "NORTH":
                return NORTH;
            case "EAST":
                return EAST;
            case "SOUTH":
                return SOUTH;
            case "WEST":
                return WEST;
        }
        return -1;
    }

    public void changeOrientation(int newOrientation) {
        while(orientation != newOrientation) rotateRight90();
    }

    private void rotateRight90() {
        if (rotation_type.equals(CENTER)) rotate(Math.toRadians(-90));
        else {
            rotate(Math.toRadians(-90), xPos, yPos);

            switch (orientation) {
                // es sw wn ne
                case NORTH:
                    yPos -= height;
                    yPos2 -= height;
                    break;
                case WEST:
                    xPos += height - width;
                    xPos2 += height - width;
                    yPos += width;
                    yPos2 += width;
                    break;
                case SOUTH:
                    xPos -= width;
                    xPos2 -= width;
                    break;
                case EAST:
                    yPos += height;
                    yPos2 += height;
                    break;
            }
        }
        orientation = (orientation + 1) % 4;
    }

    public void rotate(double angle, double xCenter, double yCenter) {
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

    public void rotate(double angle) {
        double xCenter = (xPos2 + xPos)/2.0;
        double yCenter = (yPos2 + yPos)/2.0;
        rotate(angle, xCenter, yCenter);
    }

    public void save(PrintWriter out) {
        super.save(out);
    }

}
