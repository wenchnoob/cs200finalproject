package edu.cs200.gui;

import java.awt.*;

public class Player extends OrientedObject {

    private static int START = 400;
    private static Player self;

    public static Player getInstance() {
        if (self == null) self = new Player();
        return self;
    }
    private Player() {
        super(START, START, 20, 20, EAST);
    }

    public void paint(Graphics g) {
        paintWithOffset(g, 0, 0);
    }

    public boolean moveUp() {
        this.yPos -= 5;
        this.yPos2 -= 5;
        changeOrientation(NORTH);
        return true;
    }

    public boolean moveRight() {
        this.xPos += 5;
        this.xPos2 += 5;
        changeOrientation(EAST);
        return  true;
    }

    public boolean moveDown() {
        this.yPos += 5;
        this.yPos2 += 5;
        changeOrientation(SOUTH);
        return  true;
    }

    public boolean moveLeft() {
        this.xPos -= 5;
        this.xPos2 -= 5;
        changeOrientation(WEST);
        return true;
    }

    @Override
    public void paintWithOffset(Graphics g, int xOffset, int yOffset) {
        if (!isInBounds(0, 0, Map.CANVAS_WIDTH, Map.CANVAS_HEIGHT)) return;
        System.out.println("in bounds");
        g.setColor(Color.WHITE);
        //g.fillRect(xPos, yPos, width, height);
        if (orientation == NORTH) g.fillPolygon(new int[]{xPos, xPos + width/2, xPos + width}, new int[]{yPos + height, yPos, yPos + height}, 3);
        else if (orientation == EAST) g.fillPolygon(new int[]{xPos, xPos + width, xPos}, new int[]{yPos, yPos + height/2, yPos + height}, 3);
        else if (orientation == SOUTH) g.fillPolygon(new int[]{xPos, xPos + width/2, xPos + width}, new int[]{yPos, yPos + height, yPos}, 3);
        else g.fillPolygon(new int[]{xPos + width, xPos, xPos + width}, new int[]{yPos, yPos + height/2, yPos + height}, 3);
    }

    public int getXOffset() {
        return xPos - START;
    }

    public int getYOffset() {
        return  yPos - START;
    }

   
}
