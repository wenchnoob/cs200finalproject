package edu.cs200.gui;

import edu.cs200.Game;
import edu.cs200.GameObject;

public abstract class Item extends DrawableObject {

    public Item(int xPos, int yPos, int width, int height) {
        super(xPos, yPos, width, height);
    }

    public Item(String[] props) {
        super(props);
    }

    public abstract String getType();
    public abstract int getValue();

    public abstract void use(GameObject obj);

    public  String getDesc() {
        return "Just an Item bud";
    }

    @Override
    public boolean handleCollision(GameObject targ) {
        if (targ instanceof Player) {
            if (Bag.getInstance().addItem(this)) Map.getInstance().getCurrentRoom().removeObject(this);
        }
        return false;
    }
} 