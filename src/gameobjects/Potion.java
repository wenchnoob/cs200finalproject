package gameobjects;

public class Potion implements Item{

private String type;
private int value;

public int getValue() {
return this.value;
}

public String getType() {
	return this.type;
}
}