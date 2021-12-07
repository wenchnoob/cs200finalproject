package edu.cs200.gui;
import javax.swing.*;

import edu.cs200.Entity;

import static edu.cs200.util.Globals.*;
import static edu.cs200.util.Helpers.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.Map;

public class Combat extends Card{
	private Entity enemy;
	private Player player;
private static Combat self;	
	
	 public static Combat getInstance() {
	        if (self == null) self = new Combat();
	        return self;
	 }
	public Combat() {
		
		super("Combat");
		JButton thrustButton = new JButton("Thrust");
		thrustButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				player.attack(1,enemy);
			}
		});
		JButton slashButton = new JButton("Slash");
		slashButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				player.attack(3,enemy);
			}
		});
		JButton riposteButton = new JButton("Riposte");
		riposteButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				player.attack(2,enemy);
			}
		});
		mainContent.add(riposteButton);
		mainContent.add(slashButton);
		mainContent.add(thrustButton);
		// TODO Auto-generated constructor stub
	}

}
