package edu.cs200.util;

public interface Observable {
    public void addObserver(Observer observer);
    public void notifyObservers();
}
