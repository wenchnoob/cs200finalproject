package edu.cs200;

import edu.cs200.gui.utils.Setup;
import edu.cs200.gui.components.Window;
import edu.cs200.utils.MusicPlayer;

import javax.swing.*;

public class Game {

    private static boolean started;

    public static void main(String[] args) throws InterruptedException {
        MusicPlayer.getInstance().playSMB();
        SwingUtilities.invokeLater(() -> {
            Setup.setup();
            Window win = Window.getInstance();
            Window.getInstance().goTo("Home");
        });
    }

    public static void startGame() {
        started = true;
    }

    public static boolean isStarted() {
        return started;
    }

    public static void stop() {
        started = false;
    }
}
