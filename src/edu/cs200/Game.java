package edu.cs200;

import edu.cs200.gui.Card;
import edu.cs200.gui.Window;
import static edu.cs200.util.Globals.*;

import javax.swing.*;
import java.awt.*;
import edu.cs200.gui.Map;
import edu.cs200.util.Setup;


public class Game {



    public static void main(String[] args) {
        Setup.setup();
        new Card("temp");
        Window win = Window.getInstance();
        Map.getInstance().getCanvasGraphics().drawRect(100, 100, 100, 100);
        //win.getFrame().getGraphics().fillRect(400, 400, 100, 100);'
        JFrame frame = win.getFrame();
    }
}
