package main;

import data.MathToolkitNecessaryData;
import math.MathGroup;
import set.NecessarySet;

import javax.swing.*;
import java.awt.*;

public class MainInterface extends JFrame {
    public static MainInterface home = new MainInterface();
    private JPanel contentPane;
    private JList<MathGroup> list;
    private JButton set;
    private JLabel v;
    private JLabel downLabel;

    private MainInterface() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(
                (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 4),
                (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2)
        );
        v.setText(MathToolkitNecessaryData.V);
        setLocationRelativeTo(null);
        setContentPane(contentPane);
        set.addActionListener(e -> {
            NecessarySet set = new NecessarySet();
            set.setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2);
            set.setLocationRelativeTo(null);
            set.setVisible(true);
        });
    }
}
