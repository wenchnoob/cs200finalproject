package edu.cs200.util;

import edu.cs200.Entity;
import edu.cs200.GameObject;

import javax.swing.*;
import java.awt.*;

public class EntityObserver extends JPanel implements Observer {

    public static final int HEALTH = 0, ALL = 1;
    public static final int H = 0, V = 1;
    public Entity entity;
    public int type, orientation;
    JLabel health = new JLabel(), attack = new JLabel(), defence = new JLabel();

    public JPanel getHPPanel() {
        return new JPanel() {
            {
                setPreferredSize(new Dimension(500, 20));
                setMinimumSize(new Dimension(500, 20));
                setMaximumSize(new Dimension(500, 20));
            }

            @Override
            protected void paintComponent(Graphics g) {

                int maxHealth = entity.getMax_health();
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
    }

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

    protected void draw() {
        if (type == HEALTH) {
            setLayout(new FlowLayout());
            if (orientation == H) add(new JLabel("HP"));
            add(getHPPanel());
            if (orientation == V) add(new JLabel("HP"));
        }

        if (type == ALL) {
            setLayout(new GridLayout(0, 1));
            add(health);
            add(attack);
            add(defence);
        }
        update();
    }

    private void update() {
        if (type == HEALTH) {
            repaint();
            return;
        }
        health.setText("Health: " + entity.getHealth() + " /" + entity.getMax_health());
        attack.setText("Attack: " + entity.getAttackDmg());
        defence.setText("Defence: " + entity.getDefence());
    }

    @Override
    public void getUpdate(GameObject updater) {
        if (updater.equals(entity)) update();
    }
}
