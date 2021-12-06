package edu.cs200.gui;

import java.awt.*;

public class Portal extends DrawableObject {

    private static String name = "PORTAL";
    private String from;
    private String to;

    public Portal(int xPos, int yPos, int width, int height) {
        super(xPos, yPos, width, height);
        this.name = "PORTAL";
    }

    public Portal(String[] props) {
        super(props);
        this.from = props[5];
        this.to = props[6];
    }

    @Override
    public void paintWithOffset(Graphics g, int xOffset, int yOffset) {
        g.setColor(Color.MAGENTA);
        g.fillOval(xPos - xOffset, yPos - yOffset, 30, 30);
    }

    @Override
    public boolean handleCollision() {
        Map.getInstance().goToRoom(to);
        return true;
    }
}
