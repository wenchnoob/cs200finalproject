package edu.cs200.gui;

import java.awt.*;

public class Wall extends OrientedObject {

    public  Wall(int xPos, int yPos, int length, int orientation) {
        super(xPos, yPos, length, 5, orientation);
    }

    public void paintWithOffset(Graphics g, int xOffset, int yOffset) {
        g.setColor(Color.WHITE);
        g.fillRect(xPos - xOffset, yPos - yOffset, width, height);
        g.drawString(String.valueOf(orientation), xPos, yPos);
    }
}
