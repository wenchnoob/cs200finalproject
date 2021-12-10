package edu.cs200.gui.components.entities;

import java.awt.Color;
import java.awt.Graphics;

import edu.cs200.GameObject;

public class Goblin extends Enemy {
private int aggressionModifier = 50;
private int attackTypeModifier = 50;
	public Goblin(String name, int xPos, int yPos, int width, int height, int health, int maxHealth, int attackDamage, int defence, int orientation) {
		super(name, xPos, yPos,  width,height, 10, 10, 5, 2, orientation);
		// TODO Auto-generated constructor stub
	}
	
    public Goblin(String[] props) {
        super(props);
    }
	
@Override
	public void paintWithOffset(Graphics g, int xOffset, int yOffset) {
	 g.setColor(Color.GREEN);
     int xPos = this.xPos - xOffset;
      int yPos = this.yPos - yOffset;
      if (orientation == NORTH) g.fillPolygon(new int[]{xPos, xPos + width/2, xPos + width}, new int[]{yPos + width, yPos, yPos + width}, 3);
     else if (orientation == EAST) g.fillPolygon(new int[]{xPos, xPos + width, xPos}, new int[]{yPos, yPos + width/2, yPos + width}, 3);
    else if (orientation == SOUTH) g.fillPolygon(new int[]{xPos, xPos + width/2, xPos + width}, new int[]{yPos, yPos + width, yPos}, 3);
     else g.fillPolygon(new int[]{xPos + width, xPos, xPos + width}, new int[]{yPos, yPos + width/2, yPos + width}, 3);
	}

 public double getAggressionModifier() {
	 return this.aggressionModifier;
 }


@Override
public int getAttackTypeModifier() {
	// TODO Auto-generated method stub
	return this.attackTypeModifier;
}
}
