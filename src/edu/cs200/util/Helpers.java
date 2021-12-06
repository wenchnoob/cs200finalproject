package edu.cs200.util;

import edu.cs200.gui.Portal;
import edu.cs200.gui.Wall;
import edu.cs200.gui.Window;

import javax.swing.*;
import java.awt.*;

public class Helpers {

    public static void goTo(String pageName) {
        JFrame frame = Window.getInstance().getFrame();
        LayoutManager llayout = Window.getInstance().getLayoutManager();

        CardLayout layout = (CardLayout) llayout;
        layout.show(frame.getContentPane(), pageName);
    }

    public static Object parseObj(String objDef) {
        String[] props = objDef.split(",");
        switch (props[0]) {
            case "PORTAL":
                return new Portal(props);
            case "WALL":
                return new Wall(props);
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
