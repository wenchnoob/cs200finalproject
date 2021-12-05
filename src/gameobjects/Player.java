package gameobjects;
import java.util.*;
public class Player extends Entity{

private HashSet<Item> inventory = new HashSet<Item>();
private Weapon equippedWeapon;


public void equip(Weapon equipWeapon){
	if(this.equippedWeapon != null)
		this.setAttackDmg(this.getAttackDmg()- this.equippedWeapon.getValue());
	this.equippedWeapon = equipWeapon;
	this.setAttackDmg(this.getAttackDmg()+ this.equippedWeapon.getValue());
}

public void pickup(Item newItem){
inventory.add(newItem);
}
/**
 * This method is the player Version of the attack method in the Entity class
 * @param attackType gets the type of attack the player chose
 * @param enemy the enemy that is being attacked
 */


public void attack(int attackType, Entity enemy) {
	int enemyAttack = enemy.attack();//gets the type of attack the enemy will use
	if(enemyAttack == attackType) {//if the attacks are the same nothing happens
	}
	else if((enemyAttack == 1 && attackType == 2) || (enemyAttack == 2 && attackType == 3)||(enemyAttack == 3 && attackType == 1)) {//if the enemy's attack trumps the players
		this.setHealth(this.getHealth() - enemy.getAttackDmg());//player takes damage
		if (this.getHealth()<= 0)//the player dies if its health is less than or equal to 0
			this.die();
	}
	else if((enemyAttack == 2 && attackType == 1)||(enemyAttack==3&&attackType==2)||(enemyAttack == 1 && attackType == 3)) {// if the players attack trumps the enemy
		enemy.setHealth(enemy.getHealth()-this.getAttackDmg());//enemy takes damage
		if(enemy.getHealth()<=0)//if the enemies health is less than or equal to 0 it dies
			enemy.die();
	}
}
public void move(){
//@TODO
}

public void useItem(Item used) {
	String itemType = used.getType();
	int itemValue = used.getValue();
	if(itemType.equals("Health"))
		this.setHealth(this.getHealth()+itemValue);
	else {
		this.setAttackDmg(this.getAttackDmg()+ itemValue);
	}
	inventory.remove(used);
}

public void levelup(int xp) {
	//potential method for realism do not make a priority rn
}
}