package edu.cs200.gui;

import java.awt.Color;
import java.awt.Graphics;

public class Weapon extends DrawableObject implements Item{
public Weapon(int xPos, int yPos, int width, int height) {
		super(xPos, yPos, width, height);
		// TODO Auto-generated constructor stub
	}

private int damage;
private String type = "Damage";

protected int xPos, yPos, xPos2, yPos2, width, height;

@Override
public void paintWithOffset(Graphics g, int xOffset, int yOffset) {
    if (!isInBounds(0, 0, Map.CANVAS_WIDTH, Map.CANVAS_HEIGHT)) return;
    System.out.println("in bounds");
    g.setColor(Color.GREEN);
    g.fillPolygon(new int[]{xPos + width, xPos, xPos + width}, new int[]{yPos, yPos + height/2, yPos + height}, 3);
}

/**
 * This deletes the item and gives the appropriate effects to the user
 * @return the damage to be added to the players attack damage
 */
public int getValue() {
return this.damage;
}

public String getType() {
	return this.type;
}
}



