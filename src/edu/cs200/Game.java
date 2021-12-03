package edu.cs200;

import edu.cs200.gui.Card;
import edu.cs200.gui.Player;
import edu.cs200.gui.Window;
import static edu.cs200.util.Globals.*;

import javax.swing.*;
import java.awt.*;
import edu.cs200.gui.Map;
import edu.cs200.util.Setup;


public class Game {



    public static void main(String[] args) throws InterruptedException {
//        Player p = Player.getInstance();
//        System.out.println(p);
//        p.rotate(90);
//        System.out.println(p);
//        p.rotate(50);
//        System.out.println(p);

        Setup.setup();
        new Card("temp");
        Window win = Window.getInstance();
        //Thread.sleep(1);
        //Player.getInstance().moveDown();
        //Map.getInstance().redraw();
    }
}
