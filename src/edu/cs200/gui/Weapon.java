package edu.cs200.gui;

import java.awt.Color;
import java.awt.Graphics;

import edu.cs200.GameObject;

import java.awt.*;

public class Weapon extends DrawableObject implements Item {
private int damage;
private String type = "Damage";

	public Weapon(int xPos, int yPos, int width, int height) {
		super(xPos, yPos, width, height);
	}

	public Weapon(String[] props) {
		super(props);
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

	@Override
	public void paintWithOffset(Graphics g, int xOffset, int yOffset) {
		g.setColor(Color.GRAY);
		g.fillOval(xPos - xOffset, yPos - yOffset, 15, 15);
	}

	@Override
	public boolean handleCollision(GameObject targ) {
		if (targ instanceof Player) Bag.getInstance().addItem(this);
		return true;
	}

}



