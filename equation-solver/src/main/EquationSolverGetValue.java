package main;

import data.Style;
import math.equation.AbstractEquation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashSet;

/**
 * @author wysha
 */
public class EquationSolverGetValue extends JDialog {
    private JPanel contentPane;
    private JButton buttonOkay;
    private JList<String> list;
    private JPanel down;
    private JPanel up;
    private JScrollPane jScrollPane;

    public EquationSolverGetValue(AbstractEquation abstractFunction) {
        setContentPane(contentPane);
        setModal(true);
        setTitle("解方程");
        setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2);
        setStyle();
        buttonOkay.addActionListener(e -> onOkay());
        contentPane.registerKeyboardAction(e -> onOkay(), KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        contentPane.registerKeyboardAction(e -> dispose(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        setVisible(true);
    }

    public void setStyle() {
        HashSet<JComponent> jPanels = new HashSet<>();
        HashSet<JComponent> buttons = new HashSet<>();
        HashSet<JList<?>> jLists = new HashSet<>();
        jPanels.add(contentPane);
        jPanels.add(down);
        jPanels.add(up);
        jPanels.add(jScrollPane);
        buttons.add(buttonOkay);
        jLists.add(list);
        Style.setStyle(jPanels, buttons, jLists);
    }

    private void onOkay() {
        dispose();
    }
}
