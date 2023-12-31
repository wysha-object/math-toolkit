
package set;

import data.Style;
import main.GroupDataAnalyzerData;
import tools.ErrorInterface;

import javax.swing.*;
import java.util.HashSet;

/**
 * @author wysha
 */
public class GroupDataAnalyzerSet extends Set {
    private JPanel contentPane;
    private JButton buttonOkay;
    private JPanel right;
    private JButton buttonCancel;
    private JPanel up;
    private JPanel down;
    public GroupDataAnalyzerSet() {
        super.show = right;
        setContentPane(contentPane);
        right.setLayout(cardLayout);
        right.add(defaultPage.contentPane,defaultPage.name);
        setCurrent(defaultPage);
        buttonOkay.addActionListener(ee -> {
            if (current!=defaultPage){
                current.onOkay();
                try {
                    GroupDataAnalyzerData.groupDataAnalyzerDate.write();
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
        setTitle("函数计算器设置");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setStyle();
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
        buttons.add(buttonOkay);
        buttons.add(buttonCancel);
        Style.setStyle(jPanels,buttons,null);
    }
}
