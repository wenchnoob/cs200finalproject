

import edu.cs200.LocationDescription;

import java.util.*;
import java.io.*;



public class GameLayout {
private HashMap<String,Set<String>> connections;
private HashMap<String, LocationDescription> descriptions;
	public GameLayout() {
		// TODO Auto-generated constructor stub
	}
public void load(String saveFile) {
	//TODO I do not know how we want to write the save files, need to finish locationDescription and Room stuff first
	try{
	    File newFile = new File(saveFile);
	    Scanner saveScanner = new Scanner(newFile);
       }
	catch(FileNotFoundException ioe){
	      System.out.print("File not found");
	    }
	
}
public void save() {
//TODO again i need to know how we want to write the files

}

public Iterator<String> allLocationIterator(){
	//TODO finish this
	return null;
}

public Iterator<String> connectionIterator(){
	//TODO finish this
	return null;
}

public void update() {
	//TODO finish this
	
}

}

