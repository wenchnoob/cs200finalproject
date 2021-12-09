package edu.cs200.gui.components.utils;

public interface Observable {
    void addObserver(Observer observer);
    void notifyObservers();
}
