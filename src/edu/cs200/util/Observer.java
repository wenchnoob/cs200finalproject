package edu.cs200.util;

import edu.cs200.GameObject;

import java.io.Serializable;

public interface Observer extends Serializable {
    void getUpdate(GameObject updater);
}
