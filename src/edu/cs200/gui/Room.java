package edu.cs200.gui;
import java.awt.*;

import edu.cs200.LocationDescription;

public class Room {

    private boolean trapped;


    LocationDescription locationDescription;

    public Room() {
    }

    public static Room of(String src) {
        LocationDescription desc = LocationDescription.loadDescription(src);
        if (desc == null) return null;
        return new Room(desc);
    }

    public Room(LocationDescription locationDescription) {
        this();
        this.locationDescription = locationDescription;
    }

    public void setLocationDescription(LocationDescription desc) {
        this.locationDescription = desc;
    }

    public LocationDescription getLocationDescription() {
        return this.locationDescription;
    }

    public void paintWithOffset(Graphics g, int xOffset, int yOffset) {
        locationDescription.paintWithOffset(g, xOffset, yOffset);
    }
}