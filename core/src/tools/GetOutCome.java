/*
 * 开发者:熊锦枫
 * 开发者邮箱:wyshazhisishen@yeah.net
 */

package tools;

import date.Style;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.HashSet;

public class GetOutCome extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel jLabel;

    public GetOutCome(String title, String s) {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        jLabel.setText(s);
        setContentPane(contentPane);
        setModal(true);
        setTitle(title);
        setStyle();
        buttonOK.addActionListener(e -> onOK());
        contentPane.registerKeyboardAction(e -> dispose(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        contentPane.registerKeyboardAction(e -> onOK(), KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public void setStyle() {
        HashSet<JComponent> jPanels = new HashSet<>();
        HashSet<JComponent> buttons = new HashSet<>();
        jPanels.add(contentPane);
        buttons.add(buttonOK);
        buttons.add(jLabel);
        Style.setStyle(jPanels,buttons,null);
    }

    private void onOK() {
        dispose();
    }
}
