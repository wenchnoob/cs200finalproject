package edu.cs200.util;

import edu.cs200.gui.*;
import edu.cs200.gui.Window;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Helpers {

    public static void goTo(String pageName) {
        JFrame frame = Window.getInstance().getFrame();
        LayoutManager llayout = Window.getInstance().getLayoutManager();

        CardLayout layout = (CardLayout) llayout;
        layout.show(frame.getContentPane(), pageName);
    }

    public static boolean save() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showSaveDialog(Window.getInstance().getFrame());
        File f = fileChooser.getSelectedFile();
        if (f == null) return false;
        boolean saved;
        try( ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f)) ) {
            saved = Player.getInstance().save(out) && Bag.getInstance().save(out) && Map.getInstance().save(out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return saved;
    }

    public static boolean load() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(Window.getInstance().getFrame());
        File f = fileChooser.getSelectedFile();
        if (f == null) return false;
        boolean loaded;
        try( ObjectInputStream in = new ObjectInputStream(new FileInputStream(f)) ) {
            loaded = Player.getInstance().load(in) && Bag.getInstance().load(in) && Map.getInstance().load(in);
            Window.getInstance().reset();
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
        Window.getInstance().getFrame().repaint();
        return loaded;
    }

    public static Object parseObj(String objDef) {
        String[] props = objDef.split(",");
        switch (props[0]) {
            case "PORTAL":
                return new Portal(props);
            case "WALL":
                return new Wall(props);
            case "ENEMY":
                return new Enemy(props);
            case "POTION":
                return new Potion(props);
            case "WEAPON":
                return new Weapon(props);
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
