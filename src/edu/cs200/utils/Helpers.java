package edu.cs200.utils;

import edu.cs200.gui.components.*;
import edu.cs200.gui.components.Window;
import edu.cs200.gui.components.entities.*;
import edu.cs200.gui.pages.Bag;
import edu.cs200.gui.pages.Map;

import javax.swing.*;
import java.awt.*;
import java.io.*;

import static edu.cs200.utils.Globals.MAP;

public class Helpers {

    public static int NO_SELECTION = 0, FAILED = 1, SUCCESS = 2;

    public static int save() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showSaveDialog(Window.getInstance().getFrame());

        File f = fileChooser.getSelectedFile();

        if (f == null) return NO_SELECTION;

        boolean saved;
        try( ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f)) ) {
            saved = Player.getInstance().save(out) && Bag.getInstance().save(out) && Map.getInstance().save(out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return FAILED;
        } catch (IOException e) {
            e.printStackTrace();
            return FAILED;
        }
        if (saved) return SUCCESS;
        return FAILED;
    }

    public static int load() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(Window.getInstance().getFrame());
        File f = fileChooser.getSelectedFile();
        if (f == null) return NO_SELECTION;
        boolean loaded;
        try( ObjectInputStream in = new ObjectInputStream(new FileInputStream(f)) ) {
            loaded = Player.getInstance().load(in) && Bag.getInstance().load(in) && Map.getInstance().load(in);
            Window.getInstance().reset();
        } catch (FileNotFoundException e) {
            return FAILED;
        } catch (IOException e) {
            return FAILED;
        }
        Window.getInstance().getFrame().repaint();
        if (loaded) return SUCCESS;
        return FAILED;
    }

    public static Object parseObj(String objDef) {
        String[] props = objDef.split(",");
        switch (props[0]) {
            case "PORTAL":
                return new Portal(props);
            case "WALL":
                return new Wall(props);
            case "POTION":
                return new Potion(props);
            case "WEAPON":
                return new Weapon(props);
            case "SKELETON":
            	return new Skeleton(props);
            case "GOBLIN":
            	return new Goblin(props);
            case "OGRE":
            	return new Ogre(props);
        }
        return null;
    }

    public static Font quatera() {
        return quatera(15);
    }

    public static Font quatera(int size) {
        return quatera(Font.PLAIN, size);
    }

    public static Font quatera(int style, int size) {
        return new Font("quatera", style, size);
    }
}
