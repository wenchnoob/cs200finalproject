package edu.cs200.gui;


import edu.cs200.GameObject;

import java.awt.*;
import java.util.function.Consumer;

public class Potion extends DrawableObject implements Item {

	private String type;
	private int value;
	private Consumer<GameObject> effect;

	public Potion(int xPos, int yPos, int width, int height, int value) {
		super(xPos, yPos, width, height);
		this.value = value;
	}

	public Potion(int xPos, int yPos, int width, int height, int value, String type) {
		this(xPos, yPos, width, height, value);
		this.type = type;
		this.effect = parseEffect(type);
	}

	public Potion(String[] props) {
		super(props);
		this.value = Integer.parseInt(props[5]);
		this.type = props[6];
		this.effect = parseEffect(props[6]);
	}

	private Consumer<GameObject> parseEffect(String effect) {
		switch (effect) {
			case "HEAL":
				return obj -> {
					if (obj instanceof Player) {
						Player.getInstance().heal(value);
					}
				};
			case "ATTACK_UP":
				return obj -> {
					if (obj instanceof Player) {
						Player.getInstance().attackUp(value);
					}
				};
			case "DEFENCE_UP":
				return obj -> {
					if (obj instanceof Player) {
						Player.getInstance().defenceUp(value);
					}
				};
		}
		return obj -> {};
	}

	public int getValue() {
return this.value;
}


	public void useEffect(GameObject object) {
		effect.accept(object);
	}

	public String getType() {
	return this.type;
}

	@Override
	public void paintWithOffset(Graphics g, int xOffset, int yOffset) {
		g.setColor(Color.GREEN);
		g.fillOval(xPos - xOffset, yPos - yOffset, 15, 15);
		g.setColor(Color.DARK_GRAY);

		String label = "";

		switch (type) {
			case "HEAL":
				label = "H";
				break;
			case "ATTACK_UP":
				label = "A";
				break;
			case "DEFENCE_UP":
				label = "D";
		}

		g.drawString(label, xPos - xOffset, yPos - yOffset);
	}

	@Override
	public boolean handleCollision(GameObject targ) {
		if (targ instanceof Player) {
			Bag.getInstance().addItem(this);
			Map.getInstance().getCurrentRoom().getLocationDescription().removeObject(this);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getType().hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Potion)) return false;
		Potion pot = (Potion) o;
		return pot.getType().equals(type) && pot.getValue() == value;
	}
}