package edu.cs200.gui.pages;

import static edu.cs200.utils.Globals.MAP;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import javax.swing.JPanel;

import edu.cs200.gui.components.DrawableObject;
import edu.cs200.gui.components.LocationDescription;
import edu.cs200.gui.components.entities.Player;
import edu.cs200.gui.components.utils.EntityObserver;
import edu.cs200.gui.utils.Persisted;

public class Map extends Card implements Persisted {

    private static final Long serialVersionUID = 1L;

    public static final int CANVAS_WIDTH = 1000, CANVAS_HEIGHT = 600;
    private static Map self;
    private String currentRoom = "room1";
    private JPanel canvasPanel;
    private JPanel stats;
    private Hashtable<String, LocationDescription> rooms;

    public static Map getInstance() {
        if (self == null) self = new Map();
        return self;
    }

    private Map() {
        super(MAP);
        this.label.setText("Map: " + currentRoom);
        this.rooms = new Hashtable<>();
        this.rooms.put("room1",  LocationDescription.loadDescription("assets/room1.csv"));

        super.mainContent.add(new EntityObserver(Player.getInstance(), EntityObserver.ALL, EntityObserver.H), BorderLayout.PAGE_START);

        this.canvasPanel = new VisualMap();
        this.canvasPanel.setSize(CANVAS_WIDTH, CANVAS_HEIGHT);
        this.canvasPanel.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        this.canvasPanel.setBackground(Color.BLACK);
        super.mainContent.add(this.canvasPanel, BorderLayout.CENTER);
    }

    public LocationDescription find(GameObject object) {
        return find(object, currentRoom, new HashSet<>());
    }

    private LocationDescription find(GameObject object, String currentRoom, Set<String> visited) {
        if (visited.contains(currentRoom)) return null;
        visited.add(currentRoom);
        LocationDescription cur = rooms.get(currentRoom);
        if (cur == null) return null;
        if (cur.contains(object)) return cur;
        Set<String> conns = cur.getConnections();
        for (String conn: conns) {
            LocationDescription res = find(object, conn, visited);
            if (res != null) return res;
        }
        return null;
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
        this.repaint();
    }

    public void redraw(int x, int y, int width, int height) {
        this.label.setText("Map: " + currentRoom);
        this.canvasPanel.repaint(x, y, width, height);
    }

    public LocationDescription getCurrentRoom() {
        return rooms.get(currentRoom);
    }

    @Override
    public boolean load(ObjectInputStream in) {
        try {
            self = (Map)in.readObject();
            redraw();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void focusOnMap() {
        this.canvasPanel.requestFocusInWindow();
    }

    public void resetMap() {
        self = new Map();
    }

    private class VisualMap extends JPanel {


        {
            setFocusable(true);

            addMouseListener(new SerializableMouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    requestFocusInWindow();
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e);
                    requestFocusInWindow();
                }
            });

            addKeyListener(new SerializableKeyAdapter() {
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
                    Map.getInstance().redraw();
                }
            });
        }

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
