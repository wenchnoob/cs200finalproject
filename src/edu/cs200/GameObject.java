package edu.cs200;
public abstract class GameObject{
private String name;
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