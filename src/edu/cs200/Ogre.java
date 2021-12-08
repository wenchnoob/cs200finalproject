package edu.cs200;

import java.awt.Graphics;

import edu.cs200.gui.Enemy;

public class Ogre extends Enemy{

	public Ogre() {
		this.setAggressionModifier(75);
		this.setAttackTypeModifier(25);
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
