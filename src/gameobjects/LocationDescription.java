package gameobjects;
import java.awt.Graphics;
import java.util.*;

import edu.cs200.Entity;
import edu.cs200.GameObject;
import edu.cs200.gui.DrawableObject;
import edu.cs200.gui.Item;
public class LocationDescription {

private HashSet<DrawableObject> objects;

public void paintWithOffset(Graphics graphics, int xOffset, int yOffset) {
	for(DrawableObject o : objects) {
		o.paintWithOffset(graphics, yOffset, yOffset);
	}
}

	public LocationDescription() {
		// TODO Auto-generated constructor stub
	}
 public String toString() {
	 return "";
 }
}
