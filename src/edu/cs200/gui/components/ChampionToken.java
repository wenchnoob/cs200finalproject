package edu.cs200.gui.components;

import edu.cs200.gui.components.entities.Player;
import edu.cs200.utils.MusicPlayer;

import javax.swing.*;
import java.awt.*;

public class ChampionToken extends Item {

    public ChampionToken(String name, int xPos, int yPos, int width, int height, String desc) {
        super(name, xPos, yPos, width, height, desc);
    }

    public ChampionToken(String[] props) {
        super(props);
    }

    @Override
    public void paintWithOffset(Graphics g, int xOffset, int yOffset) {
        g.setColor(Color.GREEN);
        g.fillOval(xPos - xOffset, yPos - yOffset, 15, 15);
        g.setColor(Color.DARK_GRAY);
        String label = "C";
        g.drawString(label, xPos - xOffset, yPos - yOffset);
    }

    @Override
    public String getType() {
        return null;
    }

    @Override
    public int getValue() {
        return 0;
    }

    @Override
    public void use(Player obj) {

    }

    @Override
    public boolean handleCollision(DrawableObject targ) {
        super.handleCollision(targ);
        MusicPlayer.getInstance().playSuccess();
        JOptionPane.showMessageDialog(Window.getInstance().getFrame(), "Your a winner buddy, congrats!", "Complete", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }
}
