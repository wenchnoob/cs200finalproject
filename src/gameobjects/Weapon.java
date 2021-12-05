package gameobjects;

public class Weapon implements Item{
private int damage;
private String type = "Damage";



/**
 * This deletes the item and gives the appropriate effects to the user
 * @return the damage to be added to the players attack damage
 */
public int getValue() {
return this.damage;
}

public String getType() {
	return this.type;
}
}



