package edu.cs200.gui;

import edu.cs200.Game;
import edu.cs200.LocationDescription;
import gameobjects.Room;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import static edu.cs200.util.Globals.*;

public class Map extends Card {

    public static final int CANVAS_WIDTH = 1000, CANVAS_HEIGHT = 600;
    private static Map self;
    private String currentRoom = "room1";
    private JPanel canvasPanel;
    Hashtable<String, Room> rooms;
    List<DrawableObject> gameObjects = new LinkedList<>();

    public static Map getInstance() {
        if (self == null) self = new Map();
        return self;
    }

    private Map() {
        super(MAP);
        this.label.setText("Map: " + currentRoom);
        this.rooms = new Hashtable<>();
        this.rooms.put("room1",  Room.of("assets/room1.csv"));
        this.canvasPanel = new VisualMap();
        this.canvasPanel.setSize(CANVAS_WIDTH, CANVAS_HEIGHT);
        this.canvasPanel.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        this.canvasPanel.setBackground(Color.BLACK);
        super.mainContent.add(this.canvasPanel, BorderLayout.CENTER);
    }

    public void goToRoom(String roomName) {
        if (!rooms.contains(roomName)) {
            Room room = Room.of(String.format("assets/%s.csv", roomName));
            if (room == null) return;
            rooms.put(roomName, room);
        }
        this.currentRoom = roomName;
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

    public Room getCurrentRoom() {
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
