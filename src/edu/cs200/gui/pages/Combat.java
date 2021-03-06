
package edu.cs200.gui.pages;

import javax.swing.*;

import edu.cs200.gui.components.Window;

import edu.cs200.gui.components.entities.Enemy;
import edu.cs200.gui.components.entities.Entity;
import edu.cs200.gui.components.entities.Player;
import edu.cs200.gui.components.utils.EntityObserver;
import edu.cs200.gui.utils.SerializableComponentAdapter;
import edu.cs200.gui.utils.SerializableKeyAdapter;
import edu.cs200.gui.utils.SerializableMouseAdapter;

import static edu.cs200.utils.Globals.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.*;

public class Combat extends JPanel {

    int pOffsetX, pOffsetY, eOffsetX, eOffsetY;
    int PLAYER = 0, ENEMY = 1;
    int X_AXIS = 0, Y_AXIS = 1;
    int INCREASING = 0, DECREASING = 1;

    private Enemy currentEnemy;

    private static Combat self;

    CombatWindow center = new CombatWindow();

    JPanel top = new JPanel();

    EntityObserver playerHealthObserver;
    EntityObserver playerStatsObserver;
    EntityObserver enemyHealthObserver;
    EntityObserver enemyStatsObserver;

    public static Combat getInstance() {
        if (self == null) self = new Combat();
        return self;
    }

    public void setCurrentEnemy(Enemy currentEnemy) {
        try {
            top.remove(enemyHealthObserver);
            top.remove(playerHealthObserver);
            remove(enemyStatsObserver);
            remove(playerStatsObserver);

            Player.getInstance().removeObserver(playerHealthObserver);
            Player.getInstance().removeObserver(playerStatsObserver);
            currentEnemy.removeObserver(enemyHealthObserver);
            currentEnemy.removeObserver(enemyStatsObserver);
        } catch (NullPointerException ex) {}

        playerHealthObserver = new EntityObserver(Player.getInstance());

        top.add(playerHealthObserver);
        add(top, BorderLayout.PAGE_START);

        playerStatsObserver = new EntityObserver(Player.getInstance(), EntityObserver.ALL, EntityObserver.V);
        add(playerStatsObserver, BorderLayout.LINE_START);


        enemyHealthObserver = new EntityObserver(currentEnemy, EntityObserver.HEALTH, EntityObserver.V);
        top.add(enemyHealthObserver);
        enemyStatsObserver = new EntityObserver(currentEnemy, EntityObserver.ALL, EntityObserver.V);
        add(enemyStatsObserver, BorderLayout.LINE_END);
        this.currentEnemy = currentEnemy;
    }

    private Combat() {
        setLayout(new BorderLayout());
        add(center, BorderLayout.CENTER);

        JPanel bottom = new JPanel();

        JButton thrustButton = new JButton("Thrust");
        thrustButton.addActionListener((ActionListener & Serializable) action ->{
        	attack(Entity.THRUST);
        });

        JButton slashButton = new JButton("Slash");
        slashButton.addActionListener((ActionListener & Serializable) action ->{
        	attack(Entity.SLASH);
        });

        JButton dodgeButton = new JButton("Dodge");
        dodgeButton.addActionListener((ActionListener & Serializable) action ->{
        	attack(Entity.DODGE);
        });
        
        JButton parryButton = new JButton("Parry");
        parryButton.addActionListener((ActionListener & Serializable) action ->{
        	attack(Entity.PARRY);
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
                        attack(Entity.THRUST);
                        break;
                    case KeyEvent.VK_S:
                        attack(Entity.SLASH);
                        break;
                    case KeyEvent.VK_D:
                        attack(Entity.DODGE);
                        break;
                    case KeyEvent.VK_F:
                        attack(Entity.PARRY);
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


    public void attack(int playerAttack) {
        int enemyAttack = currentEnemy.attack(0, null);
        String msg = Player.getInstance().attackR(playerAttack, enemyAttack, currentEnemy);
        animatePlayer(playerAttack);
        animateEnemy(enemyAttack);
        JOptionPane.showMessageDialog(Window.getInstance().getFrame(), msg);
        if (!(Player.getInstance().isAlive() && currentEnemy.isAlive())) endCombat();
    }

    private void animatePlayer(int attack) {
        animateBasedOnAttack(attack, PLAYER, INCREASING, DECREASING);
    }

    private void animateEnemy(int attack) {
        animateBasedOnAttack(attack, ENEMY, DECREASING, INCREASING);
    }

    private void animateBasedOnAttack(int attack, int enemy, int decreasing, int increasing) {
        switch (attack) {
            case Entity.THRUST:
                animate(enemy, 200, X_AXIS, decreasing);
                break;
            case Entity.SLASH:
                animate(enemy, 200, X_AXIS, decreasing);
                animate(enemy, 200, Y_AXIS, increasing);
                break;
            case Entity.DODGE:
                animate(enemy, 200, X_AXIS, increasing);
                break;
            case Entity.PARRY:
                animate(enemy, 200, X_AXIS, increasing);
                animate(enemy, 200, Y_AXIS, decreasing);

        }
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

    public void animate(int entity, int amount, int axis, int dir) {
        Thread t = new Thread(() -> {
            for (int i = 0; i < amount * 2; i++) {
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int delta = dir == 0 ? amount - i > 0 ? 1 : -1 : amount - i > 0 ? -1 : 1;
                if (entity == PLAYER) {
                    if (axis == X_AXIS) pOffsetX += delta;
                    else pOffsetY += delta;
                } else if (entity == ENEMY) {
                    if (axis == X_AXIS) eOffsetX += delta;
                    else eOffsetY += delta;
                }
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
            int START_X = 100 + pOffsetX;
            int START_Y = 250 + pOffsetY;
            int DIM_X = 100;
            int DIM_Y = 100;
            g.fillPolygon(new int[]{START_X, START_X + DIM_X, START_X}, new int[]{START_Y, START_Y + DIM_Y / 2, START_Y + DIM_Y}, 3);
        }

        private void paintEnemy(Graphics g) {
            g.setColor(Color.RED);
            int START_X = 900 + eOffsetX;
            int START_Y = 250 + eOffsetY;
            int DIM_X = 100;
            int DIM_Y = 100;
            g.fillPolygon(new int[]{START_X + DIM_X, START_X, START_X + DIM_X}, new int[]{START_Y, START_Y + DIM_Y / 2, START_Y + DIM_Y}, 3);
        }
    }

}
