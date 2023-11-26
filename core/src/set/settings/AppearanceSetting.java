/*
 * 开发者:熊锦枫
 * 开发者邮箱:wyshazhisishen@yeah.net
 */

package set.settings;

import data.NecessaryData;
import data.Setting;
import data.Style;

import javax.swing.*;
import java.util.HashSet;
import java.util.Objects;

/**
 * @author wysha
 */
public class AppearanceSetting extends AbstractSettings {
    public JPanel contentPane;
    private JComboBox<Style> comboBox;
    private JLabel jLabel;
    private JPanel setStyle;

    public AppearanceSetting(JDialog jDialog) {
        super(AppearanceSetting.class.toString(),jDialog);
        setStyle();
        comboBox.setModel(new  DefaultComboBoxModel<>(NecessaryData.necessaryData.styles));
        comboBox.setSelectedItem(NecessaryData.necessaryData.setting.style);
    }
    @Override
    public void onCancel() {
        jDialog.dispose();
    }

    @Override
    public void save() {
        Setting.setStyle((Style) Objects.requireNonNull(comboBox.getSelectedItem()));
    }

    private void setStyle() {
        HashSet<JComponent> jPanels = new HashSet<>();
        HashSet<JComponent> buttons = new HashSet<>();
        jPanels.add(contentPane);
        jPanels.add(setStyle);
        buttons.add(comboBox);
        buttons.add(jLabel);
        Style.setStyle(jPanels,buttons,null);
    }
}
