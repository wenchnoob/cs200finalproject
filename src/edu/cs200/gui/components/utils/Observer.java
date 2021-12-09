package edu.cs200.gui.components.utils;

import edu.cs200.GameObject;

import java.io.Serializable;

public interface Observer extends Serializable {
    void getUpdate(GameObject updater);
}
