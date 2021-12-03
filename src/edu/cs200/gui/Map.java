package edu.cs200.gui;

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
        this.canvasPanel.setSize(1200, 1200);
        this.canvasPanel.setPreferredSize(new Dimension(1200, 1200));

        this.canvasPanel.setBackground(Color.BLACK);
        super.mainContent.add(this.canvasPanel, BorderLayout.CENTER);
    }

    public void redraw() {
        this.canvasPanel.repaint();
    }

    private class VisualMap extends JPanel implements ActionListener {

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

                    System.out.println("a");
                    redraw();
                }
            });
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Player n = Player.getInstance();
            int xOffset = n.getXOffset();
            int yOffset = n.getYOffset();
            for (DrawableObject obj: gameObjects) obj.paintWithOffset(g, xOffset, yOffset);
            n.paint(g);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            redraw();
        }
    }
}
