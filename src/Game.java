import gui.Window;

import javax.swing.*;
import java.awt.*;

public class Game {

    public static final int WIDTH = 1280, HEIGHT = 720;

    public static void main(String[] args) {
        Window win = new Window(1280, 720, "apple");
        win.getFrame().add(new Button("hey"));
        //win.getFrame().getGraphics().fillRect(400, 400, 100, 100);'
        JFrame frame = win.getFrame();
    }
}
