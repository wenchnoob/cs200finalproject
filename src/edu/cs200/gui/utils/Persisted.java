package edu.cs200.gui.utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public interface Persisted extends Serializable {

    default boolean save(ObjectOutputStream out) {
        try {
            out.writeObject(this);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    boolean load(ObjectInputStream in);
}
