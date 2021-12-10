package edu.cs200.gui.pages;

import edu.cs200.gui.components.GameObject;
import edu.cs200.gui.components.LocationDescription;
import edu.cs200.gui.utils.Persisted;
import edu.cs200.gui.components.DrawableObject;
import edu.cs200.gui.components.entities.Player;
import edu.cs200.gui.components.utils.EntityObserver;
import edu.cs200.gui.utils.SerializableComponentAdapter;
import edu.cs200.gui.utils.SerializableKeyAdapter;
import edu.cs200.gui.utils.SerializableMouseAdapter;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.*;

import static edu.cs200.utils.Globals.*;

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

    public DrawableObject find(String name) {
        return find(name, currentRoom, new HashSet<>());
    }

    public Set<String> itemNames() {
        Set<String> names = new HashSet<>();
        itemNames(currentRoom, names, new HashSet<>());
        return names;
    }

    private void itemNames(String currentRoom, Set<String> names, Set<String> visited) {
        if (visited.contains(currentRoom)) return;
        visited.add(currentRoom);
        loadRoom(currentRoom);
        LocationDescription locationDescription = rooms.get(currentRoom);
        for (DrawableObject object: locationDescription.getObjects()) names.add(object.getName());
        for (String conn: locationDescription.getConnections()) itemNames(conn, names, visited);
    }

    private DrawableObject find(String name, String currentRoom, Set<String> visited) {
        if (visited.contains(currentRoom)) return null;
        visited.add(currentRoom);
        loadRoom(currentRoom);
        LocationDescription cur = rooms.get(currentRoom);
        if (cur == null) return null;
        DrawableObject obj = cur.getObjects().stream().parallel().filter(object -> object.getName().equals(name)).findFirst().orElse(null);
        if (Objects.nonNull(obj)) return obj;
        Set<String> conns = cur.getConnections();
        for (String conn: conns) {
            DrawableObject res = find(name, conn, visited);
            if (Objects.nonNull(res)) return res;
        }
        return null;
    }

    public Iterator<String> locations() {
        return rooms.keySet().iterator();
    }

    public Iterator<String> connections(String location) {
        loadRoom(location);
        LocationDescription loc = rooms.get(location);
        if (loc == null) return new LinkedList<String>().iterator();
        return loc.getConnections().iterator();
    }

    public LocationDescription getRoom(String room) {
        return rooms.get(room);
    }

    public void goToRoom(String roomName) {
        rooms.get(currentRoom).removeObject(Player.getInstance());
        loadRoom(roomName);
        this.currentRoom = roomName;
        Player.getInstance().reset();
        rooms.get(currentRoom).addObject(Player.getInstance());
        redraw();
    }

    public void loadRoom(String roomName) {
        rooms.get(currentRoom).addObject(Player.getInstance());
        if (rooms.containsKey(roomName)) return;
        LocationDescription room = LocationDescription.loadDescription(String.format("assets/%s.csv", roomName));
        if (room == null) return;
        rooms.put(roomName, room);
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
