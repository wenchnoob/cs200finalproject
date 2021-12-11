package edu.cs200.gui.pages;

import edu.cs200.Game;
import edu.cs200.gui.components.entities.Player;
import edu.cs200.gui.components.Window;
import edu.cs200.utils.Helpers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.Serializable;

import static edu.cs200.utils.Globals.MAP;

public class Home extends JPanel {

    private static Home self;

    public static Home getInstance() {
        if (self == null) self = new Home();
        return self;
    }

    private Home () {
        SpringLayout layout = new SpringLayout();
        setLayout(layout);

        JLabel label = new JLabel("Welcome to The Legends of Rahal") {
            {
                setFont(Helpers.quatera(Font.BOLD, 45));
            }
        };

        JButton newGameButton = new JButton("New Game") {
            {
                addActionListener((ActionListener & Serializable) action -> {
                    Player.getInstance().resetPlayer();
                    Bag.getInstance().resetBag();
                    Map.getInstance().resetMap();
                    Window.getInstance().reset();
                    Window.getInstance().goTo(MAP);
                    Game.startGame();
                });

                setPreferredSize(new Dimension(200, 100));
                setMaximumSize(new Dimension(200, 100));
                setMinimumSize(new Dimension(200, 100));
            }
        };

        JButton resumeButton = new JButton("Resume Game") {
            {
                addActionListener((ActionListener & Serializable) action -> {
                    if (Game.isStarted()) Window.getInstance().goTo(MAP);
                    else JOptionPane.showConfirmDialog(Window.getInstance().getFrame(), "No game to resume :(", "Resume Failed", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
                });

                setPreferredSize(new Dimension(200, 100));
                setMaximumSize(new Dimension(200, 100));
                setMinimumSize(new Dimension(200, 100));
            }
        };

        JButton loadButton = new JButton("Load Saved Game") {
            {
                addActionListener((ActionListener & Serializable) action -> {
                    int res = Helpers.load();
                    if (res != Helpers.SUCCESS) Window.getInstance().goTo("Home");
                });

                setPreferredSize(new Dimension(200, 100));
                setMaximumSize(new Dimension(200, 100));
                setMinimumSize(new Dimension(200, 100));
            }
        };

        add(label);
        add(newGameButton);
        add(resumeButton);
        add(loadButton);

        layout.putConstraint(SpringLayout.WEST, label, 220, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, label, 100, SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.WEST, newGameButton, 270, SpringLayout.WEST, label);
        layout.putConstraint(SpringLayout.NORTH, newGameButton, 80, SpringLayout.NORTH, label);

        layout.putConstraint(SpringLayout.EAST, resumeButton, 0, SpringLayout.EAST, newGameButton);
        layout.putConstraint(SpringLayout.NORTH, resumeButton, 130, SpringLayout.NORTH, newGameButton);

        layout.putConstraint(SpringLayout.EAST, loadButton, 0, SpringLayout.EAST, resumeButton);
        layout.putConstraint(SpringLayout.NORTH, loadButton, 130, SpringLayout.NORTH, resumeButton);
    }
}
