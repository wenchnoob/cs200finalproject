package edu.cs200.gui.components.utils;

import edu.cs200.gui.components.DrawableObject;

import java.io.Serializable;

public interface Observer extends Serializable {
    void getUpdate(DrawableObject updater);
}
