<<<<<<< HEAD:src/edu/cs200/gui/Combat.java
package edu.cs200.gui;
import edu.cs200.util.*;
=======
package edu.cs200.gui.pages;

>>>>>>> ae84bc8df8b9d3eb3727a4b1fb1f00e1df9cc67c:src/edu/cs200/gui/pages/Combat.java
import javax.swing.*;

import edu.cs200.Entity;
import edu.cs200.gui.components.Enemy;
import edu.cs200.gui.components.Player;
import edu.cs200.util.EntityObserver;

import static edu.cs200.util.Globals.*;
import static edu.cs200.util.Helpers.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.*;

public class Combat extends JPanel {

    private Entity currentEnemy;

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
        flee.addActionListener((ActionListener & Serializable) action -> {
        	if (new Random().nextInt(100) % 2 == 0) {
        		goTo(MAP);
			} else {
        		Player.getInstance().trueDamage(2);
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
        if (Player.getInstance().getHealth() <= 0 || currentEnemy.getHealth() <= 0) endCombat();

    }

    public void endCombat() {
        goTo(MAP);
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
