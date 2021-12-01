package edu.cs200.gui;

import javax.swing.*;
import java.awt.*;

public class Window {

    private static final long serialVersionUID = 1L;
    private final JFrame frame;

    private final JPanel panel1 = new JPanel();
    private final JPanel panel2 = new JPanel();

    public Window(int width, int height, String title) {
        frame = new JFrame(title);

        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));


        CardLayout layout = new CardLayout();
        frame.setLayout(layout);

        frame.add(panel1, "p1");
        panel1.add(new JButton("in P1 Go to P2") {
            {
                addActionListener(e -> {
                    String act = e.getActionCommand();
                    System.out.println(act);
                    layout.show(frame.getContentPane(), "p2");
                });
            }
        });
        frame.add(panel2, "p2");
        panel2.add(new JButton("in P2 Go to P1") {
            {
                addActionListener(e -> {
                    String act = e.getActionCommand();
                    System.out.println(act);
                    CardLayout card = (CardLayout) this.getLayout();
                    layout.show(frame.getContentPane(), "p1");
                });
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public JFrame getFrame() {
        return this.frame;
    }

}