package edu.cs200.gui;

import edu.cs200.util.Helpers;
import edu.cs200.util.SerializeableMouseAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.Serializable;

import static edu.cs200.util.Globals.MAP;

public class Home extends JPanel {

    private static Home self;

    public static Home getInstance() {
        if (self == null) self = new Home();
        return self;
    }


    private Home () {
        Box box = new Box(BoxLayout.Y_AXIS);
        box.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        box.setAlignmentY(JComponent.CENTER_ALIGNMENT);
        box.add(Box.createVerticalGlue());
        box.add(new JLabel("Welcome to the Legends of Rahal") {
            {
                setFont(Helpers.quatera(Font.BOLD, 25));
            }
        }, BorderLayout.PAGE_START);

        box.add(Box.createVerticalGlue());

        JPanel container = new JPanel();
        container.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS) {
            {
                setAlignmentX(JComponent.CENTER_ALIGNMENT);
            }
        });
        container.setPreferredSize(new Dimension(400, 300));
        container.add(new JButton("New Game") {
            {
                addActionListener((ActionListener & Serializable) action -> {
                    Helpers.goTo(MAP);
                });

                setPreferredSize(new Dimension(200, 100));
                setMaximumSize(new Dimension(200, 100));
                setMinimumSize(new Dimension(200, 100));
            }
        });
        container.add(new JButton("Load Saved Game") {
            {
                addActionListener((ActionListener & Serializable) action -> {
                    boolean loaded = Helpers.load();
                    if (!loaded) Helpers.goTo("Home");
                });

                setPreferredSize(new Dimension(200, 100));
                setMaximumSize(new Dimension(200, 100));
                setMinimumSize(new Dimension(200, 100));
            }
        });

        box.add(container);
        add(box);
    }

}
