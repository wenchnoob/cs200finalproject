package edu.cs200.gui;

import edu.cs200.GameObject;

public class Potion extends GameObject implements Item{

private String type;
private int value;

public int getValue() {
return this.value;
}

public String getType() {
	return this.type;
}
}