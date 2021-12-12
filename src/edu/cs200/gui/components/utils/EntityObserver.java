package edu.cs200.gui.components.utils;

import edu.cs200.gui.components.entities.Entity;
import edu.cs200.gui.components.GameObject;

import javax.swing.*;
import java.awt.*;

public class EntityObserver extends JPanel implements Observer {

    public static final int HEALTH = 0, ALL = 1;
    public static final int H = 0, V = 1;
    private Entity entity;
    private int type, orientation;
    private JLabel health = new JLabel(), attack = new JLabel(), defence = new JLabel();

    private JPanel HPPanel = new JPanel() {
        {
            setPreferredSize(new Dimension(500, 20));
            setMinimumSize(new Dimension(500, 20));
            setMaximumSize(new Dimension(500, 20));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int maxHealth = entity.getMaxHealth();
            maxHealth = maxHealth == 0 ? 1 : maxHealth;

            super.paintComponent(g);
            g.setColor(Color.RED);
            g.fillRect(0, 5, 500, 15);
            g.setColor(Color.GREEN);
            if (orientation == H) g.fillRect(0, 5, 500 * entity.getHealth() / maxHealth, 15);
            else
                g.fillRect((int) (0 + 500 * (1 - entity.getHealth() / (double) maxHealth)), 5, 500 * entity.getHealth() / maxHealth, 15);
            g.setColor(Color.BLACK);
            g.drawString(String.format("%d /%d", entity.getHealth(), maxHealth), 230, 18);
        }
    };

    public EntityObserver(Entity e) {
        this(e, HEALTH);
    }

    public EntityObserver(Entity e, int type) {
        this(e, type, H);
    }

    public EntityObserver(Entity e, int type, int orientation) {
        this.type = type;
        this.orientation = orientation;
        this.entity = e;
        e.addObserver(this);
        draw();
    }

    public void changeEntity(Entity newEntity) {
        this.entity = newEntity;
        entity.addObserver(this);
        update();
    }

    protected void draw() {
        if (type == HEALTH) {
            setLayout(new FlowLayout());
            if (orientation == H) add(new JLabel("HP"));
            add(HPPanel);
            if (orientation == V) add(new JLabel("HP"));
        }

        if (type == ALL) {
            if (orientation == H) {
                setLayout(new FlowLayout());
                add(new JLabel("HP"));
                add(HPPanel);
                add(attack);
                add(defence);
            }

            if (orientation == V) {
                setLayout(new GridLayout(0, 1));
                add(health);
                add(attack);
                add(defence);
            }
        }
        update();
    }

    private void update() {
        System.out.println("updating");
        if (type == HEALTH) {
            HPPanel.repaint();
            return;
        }
        health.setText("Health: " + entity.getHealth() + " /" + entity.getMaxHealth());
        attack.setText("Attack: " + entity.getAttackDmg());
        defence.setText("Defence: " + entity.getDefence());
    }

    @Override
    public void getUpdate(GameObject updater) {
        if (updater.equals(entity)) update();
    }
}
