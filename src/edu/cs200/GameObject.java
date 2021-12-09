package edu.cs200;

import java.io.Serializable;

public abstract class GameObject implements Serializable {

    private static String name = "Game Object";

    public GameObject() {
	this("non");
}

    public GameObject(String name) {
	this.name = name;
}

    public String getName() {
    return this.name;
}

    public void setName(String name) {
   this.name = name;
}

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GameObject)) return false;
        GameObject gobj = (GameObject) obj;
        return this.getName().equals(gobj.getName());
    }
}