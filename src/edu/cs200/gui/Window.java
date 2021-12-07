package edu.cs200.gui;

import javax.swing.*;
import java.awt.*;

import static edu.cs200.util.Globals.*;

public class Window {

    private static Window self;
    private final JFrame frame;
    private final LayoutManager layoutManager;

    public static Window getInstance() {
        if (self == null) self = new Window(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_NAME);
        return self;
    }

    private Window(int width, int height, String title) {
        frame = new JFrame(title);

        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));


        layoutManager = new CardLayout();
        frame.setLayout(layoutManager);

        frame.add(Map.getInstance(), MAP);
        frame.add(Bag.getInstance(), BAG);
        frame.add(Combat.getInstance(), "Combat");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setFocusable(true);
        frame.setVisible(true);
    }

    public JFrame getFrame() {
        return this.frame;
    }

    public void addPage(Component page, String name) {
        frame.add(page, name);
    }

    public LayoutManager getLayoutManager() {
        return this.layoutManager;
    }

}