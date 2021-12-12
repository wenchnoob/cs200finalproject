package edu.cs200.gui.components;

import edu.cs200.gui.components.entities.Player;

import java.awt.Color;
import java.awt.Graphics;


public class Weapon extends Item {

	protected int damage;
	private String type = "Damage";

	public Weapon(String name, int xPos, int yPos, int width, int height, int damage, String desc) {
		super(name, xPos, yPos, width, height, desc);
		this.damage = damage;
	}

	public Weapon(String[] props) {
		super(props);
		this.damage = Integer.parseInt(props[6]);
	}



	/**
 * This deletes the item and gives the appropriate effects to the user
 * @return the damage to be added to the players attack damage
 */
	public int getValue() {
return this.damage;
}

	
	public void use(Player obj) {
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



	@Override
	public boolean handleCollision(DrawableObject targ) {
		// TODO Auto-generated method stub
		return false;
	}

}



