package edu.cs200.gui.components.entities;

import java.awt.Graphics;

import edu.cs200.GameObject;

public class Goblin extends Enemy {

	public Goblin(String name, int xPos, int yPos, int width, int height, int health, int maxHealth, int attackDamage, int defence, int orientation) {
		super(name, xPos, yPos,  width,height, health, maxHealth, attackDamage, defence, orientation);
		this.setAggressionModifier(50);
		this.setAttackTypeModifier(50);
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
	public int attack() {
		
		return -1;
	}

}
