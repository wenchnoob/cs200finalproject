
package edu.cs200.gui.pages;

import javax.swing.*;

import edu.cs200.gui.components.entities.Entity;
import edu.cs200.gui.components.entities.Enemy;
import edu.cs200.gui.components.entities.Player;
import edu.cs200.gui.components.utils.EntityObserver;
import edu.cs200.gui.utils.SerializableComponentAdapter;
import edu.cs200.gui.utils.SerializableKeyAdapter;
import edu.cs200.gui.utils.SerializableMouseAdapter;

import static edu.cs200.utils.Globals.*;
import static edu.cs200.utils.Helpers.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.*;

public class Combat extends JPanel {

    int offsetX, offsetY;
    int X_AXIS = 0, Y_AXIS = 1;
    int INCREASING = 0, DEACREASING = 1;

    private Enemy currentEnemy;

    private static Combat self;

    CombatWindow center = new CombatWindow();

    public static Combat getInstance() {
        if (self == null) self = new Combat();
        return self;
    }

    public void setCurrentEnemy(Enemy currentEnemy) {
        this.currentEnemy = currentEnemy;

        JPanel top = new JPanel();
        top.add(new EntityObserver(Player.getInstance()));
        top.add(new EntityObserver(currentEnemy, EntityObserver.HEALTH, EntityObserver.V));
        add(top, BorderLayout.PAGE_START);

        add(new EntityObserver(Player.getInstance(), EntityObserver.ALL, EntityObserver.V), BorderLayout.LINE_START);
        add(new EntityObserver(currentEnemy, EntityObserver.ALL, EntityObserver.V), BorderLayout.LINE_END);
        currentEnemy.damage(1);
        currentEnemy.setHealth(currentEnemy.getHealth() + 1);
    }

    private Combat() {
        setLayout(new BorderLayout());
        add(center, BorderLayout.CENTER);

        JPanel bottom = new JPanel();

        JButton thrustButton = new JButton("Thrust");
        thrustButton.addActionListener((ActionListener & Serializable) action ->{
        	attack(1);
        });

        JButton slashButton = new JButton("Slash");
        slashButton.addActionListener((ActionListener & Serializable) action ->{
        	attack(2);
        });

        JButton dodgeButton = new JButton("Dodge");
        dodgeButton.addActionListener((ActionListener & Serializable) action ->{
        	attack(3);
        });
        
        JButton parryButton = new JButton("Parry");
        parryButton.addActionListener((ActionListener & Serializable) action ->{
        	attack(4);
        });

        JButton flee = new JButton("Flee");
        flee.addActionListener((ActionListener & Serializable) action -> flee());

        addComponentListener(new SerializableComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                requestFocusInWindow();
            }
        });

        addMouseListener(new SerializableMouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                requestFocus();
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                requestFocus();
            }
        });

        addKeyListener(new SerializableKeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_A:
                        attack(1);
                        break;
                    case KeyEvent.VK_S:
                        attack(2);
                        break;
                    case KeyEvent.VK_D:
                        attack(3);
                        break;
                    case KeyEvent.VK_F:
                        attack(4);
                        break;
                    case KeyEvent.VK_SPACE:
                        flee();
                }
            }
        });

        size(thrustButton, slashButton, dodgeButton, parryButton, flee);

        bottom.add(thrustButton);
        bottom.add(slashButton);
        bottom.add(dodgeButton);
        bottom.add(parryButton);
        bottom.add(flee);
        add(bottom, BorderLayout.PAGE_END);
    }

    private void size(JButton...buttons) {
        for (JButton button: buttons) {
            button.setPreferredSize(new Dimension(100, 50));
            button.setMaximumSize(new Dimension(100, 50));
            button.setMinimumSize(new Dimension(100, 50));
        }
    }

    public Entity getCurrentEnemy() {
    	return currentEnemy;
    }

    public void attack(int playerAttack) {
        Player.getInstance().attack(playerAttack,currentEnemy);
        animateH(500, X_AXIS, INCREASING);
        if (!(Player.getInstance().isAlive() && currentEnemy.isAlive())) endCombat();
    }

    public void flee() {
        if (new Random().nextInt(100) % 2 == 0) {
            edu.cs200.gui.components.Window.getInstance().goTo(MAP);
        } else {
            Player.getInstance().trueDamage(2);
        }
    }

    public void endCombat() {
        if (!currentEnemy.isAlive()) {
            currentEnemy.die();
        }

        if (!Player.getInstance().isAlive()){
            Player.getInstance().die();
            // end the game
        }
        edu.cs200.gui.components.Window.getInstance().goTo(MAP);
        currentEnemy = null;
    }

    public void animateH(int amount, int axis, int dir) {
        Thread t = new Thread(() -> {
            for (int i = 0; i < amount * 2; i++) {
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int delta = dir == 0 ? amount - i > 0 ? -1 : 1 : amount - i > 0 ? 1 : -1;
                if (axis == X_AXIS) offsetX += delta;
                else offsetY += delta;
                repaint();
            }
        });
        t.start();
    }

    private class CombatWindow extends JPanel {
        public CombatWindow() {
            setBackground(Color.GRAY);
        }


        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            paintPlayer(g);
            paintEnemy(g);
        }

        private void paintPlayer(Graphics g) {
            g.setColor(Color.WHITE);
            int START_X = 100;
            int START_Y = 250;
            int DIM_X = 100;
            int DIM_Y = 100;
            g.fillPolygon(new int[]{START_X, START_X + DIM_X, START_X}, new int[]{START_Y, START_Y + DIM_Y / 2, START_Y + DIM_Y}, 3);
        }

        private void paintEnemy(Graphics g) {
            g.setColor(Color.RED);
            int START_X = 900 + offsetX;
            int START_Y = 250 + offsetY;
            int DIM_X = 100;
            int DIM_Y = 100;
            g.fillPolygon(new int[]{START_X + DIM_X, START_X, START_X + DIM_X}, new int[]{START_Y, START_Y + DIM_Y / 2, START_Y + DIM_Y}, 3);
        }
    }

}
