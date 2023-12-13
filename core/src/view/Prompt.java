
package view;

import data.Style;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashSet;

/**
 * @author wysha
 */
public class Prompt extends JDialog {
    private JPanel contentPane;
    private JButton buttonOkay;
    private javax.swing.JTextArea jTextArea;
    private JScrollPane jScrollPane;

    public Prompt(String prompt) {
        jTextArea.setLineWrap(true);
        jTextArea.setWrapStyleWord(true);
        jTextArea.setEditable(false);
        jTextArea.setText("提示:\n" + prompt);
        setContentPane(contentPane);
        setModal(true);
        setTitle("提示");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setStyle();
        setAlwaysOnTop(true);
        setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 3, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 3);
        setLocationRelativeTo(null);
        buttonOkay.addActionListener(e -> onOkay());
        contentPane.registerKeyboardAction(e -> dispose(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        contentPane.registerKeyboardAction(e -> onOkay(), KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public void setStyle() {
        HashSet<JComponent> jPanels = new HashSet<>();
        HashSet<JComponent> buttons = new HashSet<>();
        jPanels.add(contentPane);
        jPanels.add(jScrollPane);
        buttons.add(buttonOkay);
        buttons.add(jTextArea);
        Style.setStyle(jPanels,buttons,null);
    }

    private void onOkay() {
        dispose();
    }
}
