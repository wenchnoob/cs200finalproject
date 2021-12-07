package edu.cs200.gui;

import edu.cs200.util.Helpers;

import static edu.cs200.util.Globals.*;
import static edu.cs200.util.Helpers.*;
import javax.swing.*;
import java.awt.*;

public class Card extends JPanel {

    protected String name;
    protected JLabel label;
    protected JPanel mainContent;

    public Card(String name) {
        this.name = name;

        setLayout(new BorderLayout());

        JPanel top = new JPanel();
        label = new JLabel(this.name);

        label.setHorizontalTextPosition(SwingConstants.CENTER);
        label.setFont(new Font("quatera", Font.BOLD, 30));
        label.setLocation((WINDOW_WIDTH - label.getWidth())/2, 20);
        top.add(label);

        JPanel leftPane = new JPanel(new GridLayout(5, 0));
        leftPane.setPreferredSize(new Dimension(WINDOW_WIDTH/5, WINDOW_HEIGHT - 20));


        JButton mapButton = new JButton(MAP);
        mapButton.addActionListener(action -> goTo(MAP));
        mapButton.setFont(quatera());

        JButton bagButton = new JButton(BAG);
        bagButton.addActionListener(action -> goTo(BAG));
        bagButton.setFont(quatera());

        JButton save = new JButton("Save");
        save.addActionListener(action -> Helpers.save());

        JButton load = new JButton("Load");
        load.addActionListener(action -> Helpers.load());

        JButton reset = new JButton("Reset");
        reset.addActionListener(action -> {});
        
        

        leftPane.add(mapButton);
        leftPane.add(bagButton);
        leftPane.add(save);
        leftPane.add(load);
        leftPane.add(reset);
        
        add(top, BorderLayout.PAGE_START);
        add(leftPane, BorderLayout.LINE_START);

        mainContent = new JPanel(new BorderLayout());
        add(mainContent, BorderLayout.CENTER);
    }

}
