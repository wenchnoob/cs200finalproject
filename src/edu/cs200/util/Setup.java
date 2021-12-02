package edu.cs200.util;

import static edu.cs200.util.Globals.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Setup {

    private static boolean setup = false;

    public static void setup() {
        if (setup) return;
        setupFont();
        setup = true;
    }

    private static void setupFont() {
        try {
            Font quatera = Font.createFont(Font.TRUETYPE_FONT, new File("assets/Quatera-Italic.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(quatera);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}