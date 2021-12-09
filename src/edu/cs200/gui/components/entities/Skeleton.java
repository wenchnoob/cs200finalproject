package edu.cs200.gui.components.entities;

import java.awt.Graphics;

import edu.cs200.GameObject;

public class Skeleton extends Enemy {

	public Skeleton(int xPos, int yPos, int width, int height, int health, int maxHealth, int attackDamage, int defence, int orientation) {
		super("Skeleton", xPos, yPos, width, height, health, maxHealth, attackDamage, defence, orientation);
		this.setAggressionModifier(0.5);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void paintWithOffset(Graphics g, int xOffset, int yOffset) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean handleCollision(GameObject targ) {
		// TODO Auto-generated method stub
		return false;
	}

}
