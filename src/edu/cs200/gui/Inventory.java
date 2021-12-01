package edu.cs200.gui;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Inventory extends JPanel {

    private static Inventory self;
    private static int capacity = 50;
    HashMap<Item, Integer> items;

    public Inventory getInstance() {
        if (self == null) self = new Inventory();
        return self;
    }

    private Inventory() {

    }

    public boolean addItem(Item item) {
        return addItem(item, 1);
    }

    public boolean addItem(Item item, int amount) {
        if (items.keySet().size() > capacity) return false;
        items.put(item, items.get(item) + amount);
        return true;
    }

    public boolean removeItem(Item item) {
        if (!items.containsKey(item)) return false;
        items.remove(item);
        return true;
    }

    public boolean removeItem(Item item, int amount) {
        if (!items.containsKey(item)) return false;
        int curAmount = items.get(item);
        if (amount > curAmount)
            return removeItem(item);
        items.put(item, curAmount - amount);
        return true;
    }

    public Set<Map.Entry<Item, Integer>> allItems() {
        return items.entrySet();
    }


}
