package edu.cs200.gui;

import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;

import static edu.cs200.util.Globals.*;
import static edu.cs200.util.Helpers.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.PrintWriter;
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
        heading = new JLabel(String.format("Your bag size %s/%s", getItemCount(), capacity));
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

    private int getItemCount() {
        int count = 0;
        for (Slot s: slots) {
            count += s.amount;
        }
        return count;
    }

    public boolean addItem(Item item) {
        return addItem(item, 1);
    }

    public boolean addItem(Item item, int amount) {
        if (getItemCount() >= capacity) return false;
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

    public void save(PrintWriter out) {
        out.write("Bag,");
        for (Slot slot: slots) {

        }
    }

    public void load(String in) {
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
            addMouseListener(
                    new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            super.mouseClicked(e);
                            if (item == null) return;

                            if (e.getButton() == MouseEvent.BUTTON1) {
                                int res = JOptionPane.showConfirmDialog(Window.getInstance().getFrame(), "Use " + item.getType(), "Use", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                                if (res == 0) {
                                    item.use(Player.getInstance());
                                    removeItem(1);
                                }
                            } else if (e.getButton() == MouseEvent.BUTTON3) {
                                int res = JOptionPane.showConfirmDialog(Window.getInstance().getFrame(), "Discard " + item.getType(), "Discard", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                                if (res == 0)
                                    removeItem(1);
                            }
                        }
                    }
            );
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
            setToolTipText(item.getDesc());
            return true;
        }

        public boolean removeItem(int amount) {
            if (this.amount == 0) return false;
            this.amount-=amount;
            if (this.amount <= 0) {
                this.amount = 0;
                this.item = null;
            }
            return true;
        }

        public Item getItem() {
            return this.item;
        }

        public int getAmount() {
            return this.amount;
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (this.item == null) return;
            g.drawString(item.getName(), 50, 100);
            g.drawString(String.valueOf(amount), 70, 150);
        }
    }
}
