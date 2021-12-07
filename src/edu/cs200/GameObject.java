package edu.cs200;
public abstract class GameObject{
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
}