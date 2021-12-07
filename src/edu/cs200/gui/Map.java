package edu.cs200.gui;

import edu.cs200.LocationDescription;

import javax.swing.*;

import java.awt.*;
import java.util.Hashtable;

import static edu.cs200.util.Globals.*;

public class Map extends Card {

    public static final int CANVAS_WIDTH = 1000, CANVAS_HEIGHT = 600;
    private static Map self;
    private String currentRoom = "room1";
    private JPanel canvasPanel;
    private JPanel stats;
    Hashtable<String, LocationDescription> rooms;

    public static Map getInstance() {
        if (self == null) self = new Map();
        return self;
    }

    private Map() {
        super(MAP);
        this.label.setText("Map: " + currentRoom);
        this.rooms = new Hashtable<>();
        this.rooms.put("room1",  LocationDescription.loadDescription("assets/room1.csv"));


        this.stats = new JPanel();
        super.mainContent.add(this.stats, BorderLayout.PAGE_START);


        this.canvasPanel = new VisualMap();
        this.canvasPanel.setSize(CANVAS_WIDTH, CANVAS_HEIGHT);
        this.canvasPanel.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        this.canvasPanel.setBackground(Color.BLACK);
        super.mainContent.add(this.canvasPanel, BorderLayout.CENTER);
    }

    public void goToRoom(String roomName) {
        if (!rooms.contains(roomName)) {
            LocationDescription room = LocationDescription.loadDescription(String.format("assets/%s.csv", roomName));
            if (room == null) return;
            rooms.put(roomName, room);
        }

        this.currentRoom = roomName;
        Player.getInstance().reset();
        redraw();
    }

    public boolean isInBounds(DrawableObject obj) {
        int xOff = Player.getInstance().getXOffset();
        int yOff = Player.getInstance().getYOffset();
        return obj.isInBounds(0 + xOff, 0 + yOff, CANVAS_WIDTH + xOff, CANVAS_HEIGHT + yOff);
    }

    public void redraw() {
        this.label.setText("Map: " + currentRoom);
        this.canvasPanel.repaint();
    }

    public LocationDescription getCurrentRoom() {
        return rooms.get(currentRoom);
    }

    private class VisualMap extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Player player = Player.getInstance();
            int xOffset = player.getXOffset();
            int yOffset = player.getYOffset();
            rooms.get(currentRoom).paintWithOffset(g, xOffset, yOffset);
            player.paint(g);
        }
    }
}
