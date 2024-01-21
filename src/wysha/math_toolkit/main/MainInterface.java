package wysha.math_toolkit.main;

import wysha.math_toolkit.math.MathGroup;
import wysha.math_toolkit.set.NecessarySet;

import javax.swing.*;
import java.awt.*;

/**
 * @author wysha
 */
public class MainInterface extends JFrame {
    public static final MainInterface home = new MainInterface();
    private JPanel contentPane;
    private JList<MathGroup> list;
    private JButton set;

    private MainInterface() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(
                (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 4),
                (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2)
        );
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
