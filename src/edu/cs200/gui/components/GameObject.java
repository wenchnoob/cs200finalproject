package edu.cs200.gui.components;

import java.io.Serializable;
import java.util.Objects;

public abstract class GameObject implements Serializable {

    protected final String name;

    public GameObject() {
        this("GameObject");
    }

    public GameObject(String name) {
        this.name = name;
    }

    public GameObject(String[] props) {
        this.name = props[1];
    }

    public String getName() {
        return this.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GameObject)) return false;
        GameObject gobj = (GameObject) obj;
        return this.getName().equals(gobj.getName());
    }
}