package edu.cs200.util;

public interface Observable {
    void addObserver(Observer observer);
    void notifyObservers();
}
