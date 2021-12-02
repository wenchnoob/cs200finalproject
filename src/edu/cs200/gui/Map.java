package edu.cs200.gui;

import javax.swing.*;

import java.awt.*;

import static edu.cs200.util.Globals.*;

public class Map extends Card {

    private static Map self;
    private Canvas canvas;

    public static Map getInstance() {
        if (self == null) self = new Map();
        return self;
    }
    private Map() {
        super(MAP);
        this.canvas = new Canvas();
        this.canvas.setBackground(Color.BLUE);
        super.mainContent.add(this.canvas);
    }

    public Canvas getCanvas() {
        return this.canvas;
    }

    public Graphics getCanvasGraphics() {
        return this.canvas.getGraphics();
    }


}
