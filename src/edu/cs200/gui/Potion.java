package edu.cs200.gui;

import java.awt.Color;
import java.awt.Graphics;

public class Potion extends DrawableObject implements Item{

	
	
public Potion(int xPos, int yPos, int width, int height) {
		super(200, 200, width, height);
		// TODO Auto-generated constructor stub
	}

private String type;
private int value;


protected int xPos, yPos, xPos2, yPos2, width, height;

@Override
public void paintWithOffset(Graphics g, int xOffset, int yOffset) {
    if (!isInBounds(0, 0, Map.CANVAS_WIDTH, Map.CANVAS_HEIGHT)) return;
    System.out.println("in bounds");
    g.setColor(Color.BLUE);
    g.fillPolygon(new int[]{xPos + width, xPos, xPos + width}, new int[]{yPos, yPos + height/2, yPos + height}, 3);
}


public int getValue() {
return this.value;
}

public String getType() {
	return this.type;
}
}