package set;

import data.CuringConfiguration;
import data.Style;

import javax.swing.*;
import java.util.HashSet;

/**
 * @author wysha
 */
public class DefaultPage extends AbstractSetSubpages {
    public JPanel contentPane;
    private JLabel jLabel;
    private JLabel down;

    public DefaultPage(JDialog jDialog) {
        super(DefaultPage.class.toString(), jDialog);
        jLabel.setText(CuringConfiguration.VERSION);
        setStyle();
    }

    @Override
    public void onOkay() {
        jDialog.dispose();
    }

    @Override
    public void onCancel() {
        jDialog.dispose();
    }

    @Override
    public void save() {

    }

    private void setStyle() {
        HashSet<JComponent> jPanels = new HashSet<>();
        HashSet<JComponent> buttons = new HashSet<>();
        jPanels.add(contentPane);
        buttons.add(jLabel);
        buttons.add(down);
        Style.setStyle(jPanels, buttons, null);
    }
}
