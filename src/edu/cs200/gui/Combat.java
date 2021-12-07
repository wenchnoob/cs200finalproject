package edu.cs200.gui;

import javax.swing.*;

import edu.cs200.Entity;
import edu.cs200.GameObject;
import edu.cs200.util.EntityObserver;
import edu.cs200.util.Observer;

import static edu.cs200.util.Globals.*;
import static edu.cs200.util.Helpers.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.Map;

public class Combat extends JPanel {

    private Entity current_enemy;

    private static Combat self;

    CombatWindow center = new CombatWindow();

    public static Combat getInstance() {
        if (self == null) self = new Combat();
        return self;
    }

    public void setCurrent_enemy(Enemy current_enemy) {
        this.current_enemy = current_enemy;

        JPanel top = new JPanel();
        top.add(new EntityObserver(Player.getInstance()));
        top.add(new EntityObserver(current_enemy, EntityObserver.HEALTH, EntityObserver.V));
        add(top, BorderLayout.PAGE_START);

        add(new EntityObserver(Player.getInstance(), EntityObserver.ALL, EntityObserver.V), BorderLayout.LINE_START);
        add(new EntityObserver(current_enemy, EntityObserver.ALL, EntityObserver.V), BorderLayout.LINE_END);
    }

    private Combat() {
        setLayout(new BorderLayout());
        add(center, BorderLayout.CENTER);

        JPanel bottom = new JPanel();

        JButton thrustButton = new JButton("Thrust");

        JButton slashButton = new JButton("Slash");

        JButton riposteButton = new JButton("Riposte");

        JButton flee = new JButton("Flee");
        flee.addActionListener(action -> {
        	if (new Random().nextInt(100) % 2 == 0) {
        		goTo(MAP);
			} else {
        		Player.getInstance().trueDamage(2);
			}
        });

        bottom.add(thrustButton);
        bottom.add(slashButton);
        bottom.add(riposteButton);
        bottom.add(flee);
        add(bottom, BorderLayout.PAGE_END);
    }

    private class CombatWindow extends JPanel {
        public CombatWindow() {
            setBackground(Color.GRAY);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Player.getInstance().paint(g);
            current_enemy.paintWithOffset(g, -800, 10);
        }
    }

}
