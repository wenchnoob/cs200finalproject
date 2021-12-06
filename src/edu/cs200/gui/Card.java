package edu.cs200.gui;

import static edu.cs200.util.Globals.*;
import static edu.cs200.util.Helpers.*;
import javax.swing.*;
import java.awt.*;

public class Card extends JPanel {

    protected String name;
    protected JPanel mainContent;

    public Card(String name) {
        this.name = name;

        setLayout(new BorderLayout());

        JPanel top = new JPanel(new FlowLayout());
        JLabel label = new JLabel(this.name);

        label.setHorizontalTextPosition(SwingConstants.CENTER);
        label.setFont(new Font("quatera", Font.BOLD, 30));
        label.setLocation((WINDOW_WIDTH - label.getWidth())/2, 20);
        top.add(label);

        JPanel leftPane = new JPanel();
        leftPane.setPreferredSize(new Dimension(WINDOW_WIDTH/5, WINDOW_HEIGHT - 20));
        leftPane.setLayout(new GridLayout(2, 1));


        JButton mapButton = new JButton(MAP);
        mapButton.addActionListener(action -> goTo(MAP));
        mapButton.setFont(quatera());

        JButton bagButton = new JButton(BAG);
        bagButton.addActionListener(action -> goTo(BAG));
        bagButton.setFont(quatera());

        JButton combatButton = new JButton("Combat");
        combatButton.addActionListener(action -> goTo("Combat"));
        combatButton.setFont(quatera());
        
        

        leftPane.add(mapButton);
        leftPane.add(bagButton);

        add(top, BorderLayout.PAGE_START);
        add(leftPane, BorderLayout.LINE_START);

        mainContent = new JPanel(new FlowLayout());
        add(mainContent, BorderLayout.CENTER);
    }

}
