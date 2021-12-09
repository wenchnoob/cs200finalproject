package edu.cs200.gui.pages;

import edu.cs200.gui.utils.Persisted;
import edu.cs200.gui.components.Item;
import edu.cs200.gui.components.entities.Player;
import edu.cs200.gui.components.Window;
import edu.cs200.gui.utils.SerializeableMouseAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import static edu.cs200.utils.Globals.BAG;
import static edu.cs200.utils.Globals.WINDOW_WIDTH;
import static edu.cs200.utils.Helpers.quatera;

public class Bag extends Card implements Persisted {

    private static final Long serialVersionUID = 1L;

    private static Bag self;
    private static int capacity = 20;
    private JLabel heading;
    private JPanel visualBag;
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

        visualBag = new JPanel();
        JScrollPane scrollPane = new JScrollPane(visualBag, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        mainContent.add(scrollPane, BorderLayout.CENTER);

        gridLayout = new GridLayout(0, 5);
        gridLayout.setHgap(2);
        gridLayout.setVgap(2);
        visualBag.setLayout(gridLayout);
        for (int i = 0; i < capacity; i++) slots.add(new Slot());
        for (Slot slot: slots) visualBag.add(slot);
    }

    public void addSlots(int amount) {
        for (int i = 0; i < amount; i++) {
            slots.add(new Slot());
        }
        drawSlots();
    }

    private void drawSlots() {
        visualBag.removeAll();
        for(Slot slot: slots) {
            visualBag.add(slot);
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
        label.setText(String.format("Your bag size %s/%s", getItemCount(), capacity));
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

    @Override
    public boolean load(ObjectInputStream in) {
        try {
            self = (Bag) in.readObject();
            this.heading = self.heading;
            this.visualBag = self.visualBag;
            this.gridLayout = self.gridLayout;
            this.slots = self.slots;
            this.label = self.label;
            this.name = self.name;
            this.mainContent = self.mainContent;
            drawSlots();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void resetBag() {
        self = new Bag();
    }

    private class Slot extends JButton implements Serializable {

        private static final long serialVersionUID = 1L;

        public Item item = null;
        public int capacity = 10;
        public int amount = 0;

        public Slot() {
            setPreferredSize(new Dimension(100, 200));
            setMaximumSize(new Dimension(100, 200));
            setMinimumSize(new Dimension(100, 200));
            setSize(new Dimension(100, 200));
            addMouseListener(
                    new SerializeableMouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            super.mouseClicked(e);
                            System.out.println(item);
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
            this.amount += amount;
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

        public String toString() {
            if (item != null) return item.getName();
            return "";
        }
    }

}
