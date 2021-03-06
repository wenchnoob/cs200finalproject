package edu.cs200.gui.components;
import edu.cs200.gui.pages.Map;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static edu.cs200.utils.Helpers.*;

public class LocationDescription extends DrawableObject {

	private HashSet<DrawableObject> objects;

	public LocationDescription() {
		this("DEFAULT_LOCATION");
	}

	public LocationDescription(String name) {
		super(name, 1, 1, 1,1 );
		this.objects = new HashSet<>();
		objects.add(new Wall(950, 50, 500, OrientedObject.SOUTH));
		objects.add(new Wall(50, 50, 900, OrientedObject.EAST));
		objects.add(new Wall(950, 550, 900, OrientedObject.WEST));
		objects.add(new Wall(50, 550, 500, OrientedObject.NORTH));
	}

	public Set<String> getConnections() {
		Set<String> portals = new HashSet<>();
		for (DrawableObject obj: objects) {
			if (obj instanceof Portal) {
				Portal port = (Portal) obj;
				portals.add(port.getTo());
			}
		}
		return portals;
	}

	public void paintWithOffset(Graphics g, int xOffset, int yOffset) {
		for (DrawableObject obj: objects) {
			if (Map.getInstance().isInBounds(obj)) obj.paintWithOffset(g, xOffset, yOffset);
		}
	}

	public void addObject(DrawableObject obj) {
		this.objects.add(obj);
	}

	public void removeObject(DrawableObject obj) {
		this.objects.remove(obj);
	}

	public boolean contains(DrawableObject obj) {
		return objects.contains(obj);
	}

	public HashSet<DrawableObject> getObjects() {
		return objects;
	}

	public static LocationDescription loadDescription(String path) {
		String[] parts = path.split("/");
		LocationDescription desc = new LocationDescription(parts[parts.length - 1]);
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

	@Override
	public boolean handleCollision(DrawableObject targ) {
		// TODO Auto-generated method stub
		return false;
	}
}
