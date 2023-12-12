package main;

import math.MathGroup;
import set.NecessarySet;

import javax.swing.*;
import java.awt.*;

public class MainInterface extends JFrame {
    public static MainInterface home = new MainInterface();
    private JPanel contentPane;
    private JList<MathGroup> list;
    private JButton set;

    private MainInterface() {
        setContentPane(contentPane);
        set.addActionListener(e -> {
            NecessarySet set = new NecessarySet();
            set.setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2);
            set.setLocationRelativeTo(null);
            set.setVisible(true);
            dispose();
            new MathGroupMainInterface().setVisible(true);
        });
    }
}
