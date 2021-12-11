package edu.cs200.gui.pages;

import edu.cs200.gui.components.Window;
import edu.cs200.utils.Helpers;

import static edu.cs200.utils.Globals.*;
import static edu.cs200.utils.Helpers.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.Serializable;

public class Card extends JPanel {

    private static final Long serialVersionUID = 1L;

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
        mapButton.addActionListener((ActionListener & Serializable) action -> Window.getInstance().goTo(MAP));
        mapButton.setFont(quatera());

        JButton bagButton = new JButton(BAG);
        bagButton.addActionListener((ActionListener & Serializable)  action -> Window.getInstance().goTo(BAG));
        bagButton.setFont(quatera());

        JButton save = new JButton("Save");
        save.addActionListener((ActionListener & Serializable) action -> {
            int res = Helpers.save();
            if (res == FAILED) {
                JOptionPane.showConfirmDialog(Window.getInstance().getFrame(), "Saving has failed!", "Notification", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
            } else if (res == SUCCESS) {
                JOptionPane.showConfirmDialog(Window.getInstance().getFrame(), "Saved!", "Notification", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
            } else if (res == NO_SELECTION) {
                //JOptionPane.showConfirmDialog(Window.getInstance().getFrame(), "No Selection Made", "Notification", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton load = new JButton("Load");
        load.addActionListener((ActionListener & Serializable) action -> {
            int res = Helpers.load();
            if (res == FAILED) {
                JOptionPane.showConfirmDialog(Window.getInstance().getFrame(), "Loading has failed!", "Notification", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
            } else if (res == SUCCESS) {
                JOptionPane.showConfirmDialog(Window.getInstance().getFrame(), "Loaded!", "Notification", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
            } else if (res == NO_SELECTION) {
                JOptionPane.showConfirmDialog(Window.getInstance().getFrame(), "No Selection Made", "Notification", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton home = new JButton("Home");
        home.addActionListener((ActionListener & Serializable) action -> Window.getInstance().goTo("Home"));
        
        

        leftPane.add(mapButton);
        leftPane.add(bagButton);
        leftPane.add(save);
        leftPane.add(load);
        leftPane.add(home);
        
        add(top, BorderLayout.PAGE_START);
        add(leftPane, BorderLayout.LINE_START);

        mainContent = new JPanel(new BorderLayout());
        add(mainContent, BorderLayout.CENTER);
    }

}
