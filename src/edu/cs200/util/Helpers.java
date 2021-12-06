package edu.cs200.util;

import edu.cs200.gui.*;
import edu.cs200.gui.Window;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Helpers {

    public static void goTo(String pageName) {
        JFrame frame = Window.getInstance().getFrame();
        LayoutManager llayout = Window.getInstance().getLayoutManager();

        CardLayout layout = (CardLayout) llayout;
        layout.show(frame.getContentPane(), pageName);
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

    public static void initFight(Enemy e) {
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
