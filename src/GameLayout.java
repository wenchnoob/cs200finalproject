import edu.cs200.LocationDescription;
import edu.cs200.gui.Window;

import javax.swing.*;
import java.util.*;

import edu.cs200.LocationDescription;

import java.io.*;

public class GameLayout {

	private HashMap<String,Set<String>> connections;
	private HashMap<String, LocationDescription> descriptions;

	public GameLayout() {

	}

	public static void save() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.showSaveDialog(Window.getInstance().getFrame());
		File f = fileChooser.getSelectedFile();
		System.out.println(f);
	}

	public static void load() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.showOpenDialog(Window.getInstance().getFrame());
		File f = fileChooser.getSelectedFile();
		System.out.println(f);
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

