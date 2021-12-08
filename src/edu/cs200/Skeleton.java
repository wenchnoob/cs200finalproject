package edu.cs200;

import java.awt.Graphics;

import edu.cs200.gui.Enemy;

public class Skeleton extends Enemy {

	public Skeleton() {
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
