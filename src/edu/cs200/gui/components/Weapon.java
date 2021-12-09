package edu.cs200.gui.components;

import edu.cs200.GameObject;

import java.awt.Color;
import java.awt.Graphics;


public class Weapon extends Item {

	private int damage = 5;
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

	@Override
	public void use(GameObject obj) {
		if (!(obj instanceof Player)) return;
		Player.getInstance().equip(this);
	}

	public String getType() {
	return this.type;
}

	@Override
	public void paintWithOffset(Graphics g, int xOffset, int yOffset) {
		g.setColor(Color.GRAY);
		g.fillOval(xPos - xOffset, yPos - yOffset, 15, 15);
	}

}



