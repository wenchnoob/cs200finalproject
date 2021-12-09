package edu.cs200;

import java.awt.Graphics;

import edu.cs200.gui.components.Enemy;

public class Goblin extends Enemy {

	public Goblin(int xPos, int yPos, int width, int height, int health, int maxHealth, int attackDamage, int defence, int orientation) {
		super(xPos, yPos,  width,height, health, 30, 7, 3,orientation);
		this.setAggressionModifier(50);
		this.setAttackTypeModifier(50);
		// TODO Auto-generated constructor stub
	}

	public void paintWithOffset(Graphics g, int xOffset, int yOffset) {
		// TODO Auto-generated method stub
		
	}

	public boolean handleCollision(GameObject targ) {
		// TODO Auto-generated method stub
		return false;
	}
	public int attack() {
		
		return -1;
	}

}
