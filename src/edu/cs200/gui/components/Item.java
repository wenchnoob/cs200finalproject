package edu.cs200.gui.components;

import edu.cs200.gui.components.entities.Player;
import edu.cs200.gui.pages.Bag;
import edu.cs200.gui.pages.Map;

public abstract class Item extends DrawableObject {

    protected String desc;

    public Item(String name, int xPos, int yPos, int width, int height, String desc) {
        super(name, xPos, yPos, width, height);
        this.desc = desc;
    }

    public Item(String[] props) {
        super(props);
        this.desc = props[props.length - 2];
    }

    public abstract String getType();
    public abstract int getValue();

    public abstract void use(GameObject obj);

    public  String getDesc() {
        if (desc.contains("%s")) return String.format(desc, getValue());
        return desc;
    }

    @Override
    public boolean handleCollision(GameObject targ) {
        if (targ instanceof Player) {
            if (Bag.getInstance().addItem(this)) Map.getInstance().getCurrentRoom().removeObject(this);
        }
        return false;
    }

} 