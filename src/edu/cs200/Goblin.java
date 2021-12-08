package edu.cs200;

import java.awt.Graphics;

import edu.cs200.gui.Enemy;

public class Goblin extends Enemy {

	public Goblin() {
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
