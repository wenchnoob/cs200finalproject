package edu.cs200.gui;

import edu.cs200.Game;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;

import static edu.cs200.util.Globals.*;

public class Map extends Card {

    public static final int CANVAS_WIDTH = 1000, CANVAS_HEIGHT = 600;
    private static Map self;
    private JPanel canvasPanel;
    List<DrawableObject> gameObjects = new LinkedList<>();

    public static Map getInstance() {
        if (self == null) self = new Map();

        return self;
    }

    private Map() {
        super(MAP);
        this.canvasPanel = new VisualMap();
        this.canvasPanel.setSize(CANVAS_WIDTH, CANVAS_HEIGHT);
        this.canvasPanel.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        this.canvasPanel.setBackground(Color.BLACK);
        super.mainContent.add(this.canvasPanel, BorderLayout.CENTER);
    }

    public boolean isInBounds(DrawableObject obj) {
        int xOff = Player.getInstance().getXOffset();
        int yOff = Player.getInstance().getYOffset();
        return obj.isInBounds(0 + xOff, 0 + yOff, CANVAS_WIDTH + xOff, CANVAS_HEIGHT + yOff);
    }

    public void redraw() {
        this.canvasPanel.repaint();
    }

    private class VisualMap extends JPanel implements ActionListener {

        private List<DrawableObject> components;

        public VisualMap () {
            setFocusable(true);
            addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    super.keyPressed(e);
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_DOWN:
                            Player.getInstance().moveDown();
                            break;
                        case KeyEvent.VK_UP:
                            Player.getInstance().moveUp();
                            break;
                        case KeyEvent.VK_LEFT:
                            Player.getInstance().moveLeft();
                            break;
                        case KeyEvent.VK_RIGHT:
                            Player.getInstance().moveRight();
                    }

                    redraw();
                }
            });
            components = new LinkedList<>();
            components.add(new Wall(100, 100, 100, OrientedObject.NORTH));
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Player player = Player.getInstance();
            int xOffset = player.getXOffset();
            int yOffset = player.getYOffset();
            for (DrawableObject obj: components) obj.paintWithOffset(g, xOffset, yOffset);
            for (DrawableObject obj: gameObjects) obj.paintWithOffset(g, xOffset, yOffset);
            player.paint(g);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            redraw();
        }
    }
}
