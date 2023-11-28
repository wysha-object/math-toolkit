/*
 * 开发者:熊锦枫
 * 开发者邮箱:wyshazhisishen@yeah.net
 */

package tools;

import data.Style;

import javax.swing.*;
import java.util.HashSet;

/**
 * @author wysha
 */
public class Choose extends JDialog {
    private JPanel contentPane;
    private JButton buttonOkay;
    private JButton buttonCancel;
    private JTextArea textArea;
    private JScrollPane jScrollPane;
    public boolean choose;

    public Choose(String s) {
        textArea.setText(s);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOkay);
        buttonOkay.addActionListener(e -> onOkay());
        buttonCancel.addActionListener(e -> onCancel());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setStyle();
    }

    private void onOkay() {
        choose=true;
        dispose();
    }

    private void onCancel() {
        choose=false;
        dispose();
    }

    public void setStyle(){
        HashSet<JComponent> jPanels = new HashSet<>();
        HashSet<JComponent> buttons = new HashSet<>();
        jPanels.add(contentPane);
        jPanels.add(jScrollPane);
        buttons.add(textArea);
        buttons.add(buttonCancel);
        buttons.add(buttonOkay);
        Style.setStyle(jPanels,buttons,null);
    }
}
