package edu.cs200.gui.components;

import edu.cs200.GameObject;
import edu.cs200.gui.components.entities.Player;
import edu.cs200.gui.pages.Map;

import java.awt.*;

public class Portal extends DrawableObject {
    private String from;
    private String to;

    public Portal(int xPos, int yPos, int width, int height) {
        super("Portal", xPos, yPos, width, height);
    }

    public Portal(String[] props) {
        super(props);
        this.to = props[7];
        this.from = props[6];
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
