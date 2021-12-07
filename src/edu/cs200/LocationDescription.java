package edu.cs200;
import edu.cs200.gui.DrawableObject;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import edu.cs200.gui.OrientedObject;
import edu.cs200.gui.Wall;
import static edu.cs200.util.Helpers.*;
import edu.cs200.gui.Map;

public class LocationDescription implements Serializable {

	private HashSet<DrawableObject> objects;

	public LocationDescription() {
		this.objects = new HashSet<>();
		objects.add(new Wall(950, 50, 500, OrientedObject.SOUTH));
		objects.add(new Wall(50, 50, 900, OrientedObject.EAST));
		objects.add(new Wall(950, 550, 900, OrientedObject.WEST));
		objects.add(new Wall(50, 550, 500, OrientedObject.NORTH));
	}

	public void paintWithOffset(Graphics g, int xOffset, int yOffset) {
		for (DrawableObject obj: objects) if (Map.getInstance().isInBounds(obj)) obj.paintWithOffset(g, xOffset, yOffset);
	}

	public void addObject(DrawableObject obj) {
		this.objects.add(obj);
	}

	public void removeObject(DrawableObject obj) {
		this.objects.remove(obj);
	}

	public HashSet<DrawableObject> getObjects() {
		return objects;
	}

	public static LocationDescription loadDescription(String path) {
		LocationDescription desc = new LocationDescription();
		try {
			List<String> lines = new BufferedReader(new InputStreamReader(new FileInputStream(path))).lines().collect(Collectors.toList());
			for (String line: lines) {
				Object obj = parseObj(line);
				if (obj instanceof DrawableObject) {
					desc.addObject((DrawableObject) obj);
				}
			}
		} catch (FileNotFoundException e) {
			return null;
		}
		return desc;
	}
}
