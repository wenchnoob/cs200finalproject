package edu.cs200.gui.components;


import edu.cs200.gui.components.entities.Player;

import java.awt.*;
import java.io.Serializable;
import java.util.function.Consumer;

public class Potion extends Item {

	private String type;
	private int value;
	private Consumer<Player> effect;

	public Potion(String name, int xPos, int yPos, int width, int height, int value, String desc) {
		super(name, xPos, yPos, width, height, desc);
		this.value = value;
	}

	public Potion(String name, int xPos, int yPos, int width, int height, int value, String type, String desc) {
		this(name, xPos, yPos, width, height, value, desc);
		this.type = type;
		this.effect = parseEffect(type);
	}

	public Potion(String[] props) {
		super(props);
		this.value = Integer.parseInt(props[6]);
		this.type = props[7];
		this.effect = parseEffect(props[7]);
	}

	private Consumer<Player> parseEffect(String effect) {
		switch (effect) {
			case "HEAL":
				return (Consumer<Player> & Serializable)  obj -> {
					if (obj instanceof Player) {
						Player.getInstance().heal(value);
					}
				};
			case "ATTACK_UP":
				return (Consumer<Player> & Serializable) obj -> {
					if (obj instanceof Player) {
						Player.getInstance().attackUp(value);
					}
				};
			case "DEFENCE_UP":
				return (Consumer<Player> & Serializable) obj -> {
					if (obj instanceof Player) {
						Player.getInstance().defenceUp(value);
					}
				};
		}
		return (Consumer<Player> & Serializable) obj -> {};
	}

	public int getValue() {
return this.value;
}

	@Override
	public void use(Player obj) {
		effect.accept(obj);
	}

	public void useEffect(Player object) {
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
	public int hashCode() {
		return getType().hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Potion)) return false;
		Potion pot = (Potion) o;
		return pot.getType().equals(type) && pot.getValue() == value;
	}

	@Override
	public boolean handleCollision(DrawableObject targ) {
		// TODO Auto-generated method stub
		return false;
	}


}