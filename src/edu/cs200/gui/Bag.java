package edu.cs200.gui;

import javax.swing.*;

import static edu.cs200.util.Globals.*;
import static edu.cs200.util.Helpers.*;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Bag extends Card {

    private static Bag self;
    private static int capacity = 20;
    private JLabel heading;
    private JPanel visiualBag;
    private GridLayout gridLayout;
    private LinkedList<Slot> slots;

    public static Bag getInstance() {
        if (self == null) self = new Bag();
        return self;
    }

    private Bag() {
        super(BAG);
        slots = new LinkedList<>();
        mainContent.setLayout(new BorderLayout());

        JPanel headingPanel = new JPanel();
        heading = new JLabel("Your bag size %s/%s");
        heading.setFont(quatera(20));
        heading.setHorizontalTextPosition(SwingConstants.CENTER);
        heading.setLocation(WINDOW_WIDTH/2, 60);
        headingPanel.add(heading);
        mainContent.add(headingPanel, BorderLayout.PAGE_START);

        visiualBag = new JPanel();
        JScrollPane scrollPane = new JScrollPane(visiualBag, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        mainContent.add(scrollPane, BorderLayout.CENTER);

        gridLayout = new GridLayout(0, 5);
        gridLayout.setHgap(2);
        gridLayout.setVgap(2);
        visiualBag.setLayout(gridLayout);
        for (int i = 0; i < capacity; i++) slots.add(new Slot());
        drawSlots();
    }

    public void addSlots(int amount) {
        visiualBag.removeAll();
        for (int i = 0; i < amount; i++) slots.add(new Slot());
        drawSlots();
    }

    private void drawSlots() {
        for (Slot slot: slots) {
            visiualBag.add(slot);
        }
    }

    public boolean addItem(Item item) {
        return addItem(item, 1);
    }

    public boolean addItem(Item item, int amount) {
        for (Slot slot: slots) {
            if (slot.addItem(item, amount)) return true;
        }
        return false;
    }

    public boolean removeItem(Item item) {
        return removeItem(item, 1);
    }

    public boolean removeItem(Item item, int amount) {
        for (Slot slot: slots) {
            if (slot.containsItem(item)) {
                slot.removeItem(amount);
            }
        }
        return false;
    }

    public List<Item> allItems() {
        List<Item> items = new LinkedList<>();

        for (Slot slot: slots) items.add(slot.item);

        return items;
    }

    private class Slot extends JButton {
        public Item item = null;
        public int capacity = 10;
        public int amount = 0;

        public Slot() {
            setPreferredSize(new Dimension(100, 200));
            setMaximumSize(new Dimension(100, 200));
            setMinimumSize(new Dimension(100, 200));
            setSize(new Dimension(100, 200));
        }

        public boolean containsItem(Item item) {
            return this.item.equals(item);
        }

        public boolean addItem(Item item, int amount) {
            if (this.item != null && this.item != item) return false;
            if (this.amount == capacity) return false;
            this.item = item;
            this.amount+= amount;
            if (this.amount > capacity) this.amount = capacity;
            return true;
        }

        public boolean removeItem(int amount) {
            if (amount == 0) return false;
            this.amount-=amount;
            if (amount <= 0) {
                this.amount = 0;
                this.item = null;
            }
            return true;
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (this.item == null) return;
            if (item instanceof Weapon) g.drawString("Weapon", 50, 100);
            if (item instanceof Potion) {
                Potion pot = (Potion) item;
                g.drawString(pot.getType(), 50, 100);
                g.drawString(String.valueOf(amount), 70, 150);
            }
        }
    }
}
