package edu.cs200;

import edu.cs200.gui.Window;
import edu.cs200.util.Helpers;
import edu.cs200.util.Setup;

import javax.swing.*;

public class Game {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Setup.setup();
            Window win = Window.getInstance();
            Helpers.goTo("Home");
        });
    }
}
