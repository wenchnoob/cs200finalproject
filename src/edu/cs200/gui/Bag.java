package edu.cs200.gui;

import javax.swing.*;

import static edu.cs200.util.Globals.*;
import static edu.cs200.util.Helpers.*;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Bag extends Card {

    private static Bag self;
    private static int capacity = 251;
    private JLabel heading;
    private JPanel visiualBag;
    private GridLayout gridLayout;
    HashMap<Item, Integer> items;

    public static Bag getInstance() {
        if (self == null) self = new Bag();
        return self;
    }

    private Bag() {
        super(BAG);
        mainContent.setLayout(new BorderLayout());

        JPanel headingPanel = new JPanel();
        heading = new JLabel("Your bag size %s/%s".formatted(10 , capacity));
        heading.setFont(quatera(20));
        heading.setHorizontalTextPosition(SwingConstants.CENTER);
        heading.setLocation(WINDOW_WIDTH/2, 60);
        headingPanel.add(heading);
        mainContent.add(headingPanel, BorderLayout.PAGE_START);

        visiualBag = new JPanel();
        JScrollPane scrollPane = new JScrollPane(visiualBag, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        //visiualBag.setPreferredSize(new Dimension(WINDOW_WIDTH/5 * 4 - 30, 1000));
        mainContent.add(scrollPane, BorderLayout.CENTER);

        gridLayout = new GridLayout(0, 5);
        gridLayout.setHgap(2);
        gridLayout.setVgap(2);
        visiualBag.setLayout(gridLayout);
        for (int i = 0; i < capacity * 5; i++) {
            visiualBag.add(new JButton() {
                {
                    //setMinimumSize(new Dimension(100, 100));
                    setPreferredSize(new Dimension(100, 100));
                }
            });
        }
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

    private class Slot extends JButton {
        public Item item = null;
        public int capacity = 10;
        public int amout = 0;
    }
}
