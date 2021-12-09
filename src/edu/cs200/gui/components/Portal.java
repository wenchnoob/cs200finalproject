package edu.cs200.gui.components;

import edu.cs200.GameObject;
import edu.cs200.gui.pages.Map;

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

    public String getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Portal)) return false;
        Portal portal = (Portal) o;
        return portal.getFrom().equals(this.from) && portal.getTo().equals(this.to);
    }

    @Override
    public void paintWithOffset(Graphics g, int xOffset, int yOffset) {
        g.setColor(Color.MAGENTA);
        g.fillOval(xPos - xOffset, yPos - yOffset, 30, 30);
    }

    @Override
    public boolean handleCollision(GameObject targ) {
        if (targ instanceof Player) Map.getInstance().goToRoom(to);
        return true;
    }
}
