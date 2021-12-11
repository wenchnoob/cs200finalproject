package edu.cs200;


import edu.cs200.utils.Helpers;
import edu.cs200.gui.utils.Setup;
import edu.cs200.gui.components.Window;


import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Game {

    private static boolean started;

    public static void main(String[] args) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        Clip clip = AudioSystem.getClip();
        clip.open(AudioSystem.getAudioInputStream(new File("assets/sounds/success.wav")));
        clip.loop(Clip.LOOP_CONTINUOUSLY);

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
}
