
package set;

import data.NecessaryData;
import data.Style;
import set.settings.AppearanceSetting;
import tools.ErrorInterface;

import javax.swing.*;
import java.util.HashSet;

/**
 * @author wysha
 */
public class NecessarySet extends Set {
    private JPanel contentPane;
    private JButton style;
    private JButton buttonOkay;
    private JPanel right;
    final AppearanceSetting appearanceSetting =new AppearanceSetting(this);
    private JButton buttonCancel;
    private JPanel up;
    private JPanel down;

    public NecessarySet() {
        super.show = right;
        setContentPane(contentPane);
        right.setLayout(cardLayout);
        right.add(defaultPage.contentPane,defaultPage.name);
        right.add(appearanceSetting.contentPane,appearanceSetting.name);
        setCurrent(defaultPage);
        buttonOkay.addActionListener(ee -> {
            if (current!=defaultPage){
                current.onOkay();
                try {
                    NecessaryData.necessaryData.write();
                } catch (Throwable e) {
                    new ErrorInterface(
                            "写入失败",
                            e,
                            false
                    ).setVisible(true);
                }
            }
        });
        buttonCancel.addActionListener(ee -> defaultPage.onCancel());
        setModal(true);
        setTitle("核心设置");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setStyle();
        buttonOkay.addActionListener(ee -> dispose());
        buttonCancel.addActionListener(ee -> dispose());
        style.addActionListener(e -> setCurrent(appearanceSetting));
        buttonOkay.addActionListener(e -> current.onOkay());
        buttonCancel.addActionListener(e -> current.onCancel());
    }

    public void setStyle() {
        HashSet<JComponent> jPanels = new HashSet<>();
        HashSet<JComponent> buttons = new HashSet<>();
        jPanels.add(contentPane);
        jPanels.add(up);
        jPanels.add(down);
        jPanels.add(right);
        buttons.add(style);
        buttons.add(buttonOkay);
        buttons.add(buttonCancel);
        Style.setStyle(jPanels,buttons,null);
    }
}
