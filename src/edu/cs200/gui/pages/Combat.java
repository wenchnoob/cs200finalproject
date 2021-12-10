
package edu.cs200.gui.pages;

import javax.swing.*;

import edu.cs200.gui.components.entities.Entity;
import edu.cs200.gui.components.entities.Enemy;
import edu.cs200.gui.components.entities.Player;
import edu.cs200.gui.components.utils.EntityObserver;
import edu.cs200.gui.utils.SerializableComponentAdapter;
import edu.cs200.gui.utils.SerializableKeyAdapter;

import static edu.cs200.utils.Globals.*;
import static edu.cs200.utils.Helpers.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.*;

public class Combat extends JPanel {

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

        bottom.add(thrustButton);
        bottom.add(slashButton);
        bottom.add(dodgeButton);
        bottom.add(parryButton);
        bottom.add(flee);
        add(bottom, BorderLayout.PAGE_END);
    }

    public Entity getCurrentEnemy() {
    	return currentEnemy;
    }

    public void attack(int playerAttack) {
        Player.getInstance().attack(playerAttack,currentEnemy);
        if (!(Player.getInstance().isAlive() && currentEnemy.isAlive())) endCombat();
    }

    public void flee() {
        if (new Random().nextInt(100) % 2 == 0) {
            goTo(MAP);
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
        goTo(MAP);
        currentEnemy = null;
    }

    private class CombatWindow extends JPanel {
        public CombatWindow() {
            setBackground(Color.GRAY);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Player.getInstance().paint(g);
            currentEnemy.paintWithOffset(g, -800, 10);
        }
    }

}
