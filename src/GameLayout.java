

import edu.cs200.LocationDescription;

import java.util.*;

import edu.cs200.LocationDescription;

import java.io.*;



public class GameLayout {
private HashMap<String,Set<String>> connections;
private HashMap<String, LocationDescription> descriptions;
	public GameLayout() {
		// TODO Auto-generated constructor stub
	}
public void load(String saveFile) {
	//TODO I do not know how we want to write the save files, need to finish locationDescription and Room stuff first

	
}
public void save() {
//TODO again i need to know how we want to write the files

}

public Iterator<String> allLocationIterator(){
	return connections.keySet().iterator();
}

public Iterator<String> connectionIterator(String connection){
	return connections.get(connection).iterator();
}

public void update() {
	//TODO finish this
	
}

}

