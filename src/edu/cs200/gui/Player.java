package edu.cs200.gui;

import java.awt.*;

public class Player extends OrientedObject {

    private static int x = 150;
    private static int y = 150;
    private static Player self;

    public static Player getInstance() {
        if (self == null) self = new Player();
        System.out.println(self);
        return self;
    }
    private Player() {
        super(x, y, 50, 50, EAST);
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
        g.setColor(Color.WHITE);
        g.fillRect(xPos, yPos, width, height);
        //g.fillPolygon(new int[]{xPos, xPos + width, xPos}, new int[]{yPos, yPos + height/2, yPos + height}, 3);
    }

    public int getXOffset() {
        return x - 150;
    }

    public int getYOffset() {
        return  y - 150;
    }

    public String toString() {
        return "x1: %s\t y1: %s\tx2: %s\t y2: %s\t width: %s\t height: %s".formatted(xPos, yPos, xPos2, yPos2, width, height);
    }
}
