/*
 * 开发者:熊锦枫
 * 开发者邮箱:wyshazhisishen@yeah.net
 */

package GroupDataAnalyzerSet;

import GroupDataAnalyzerMain.GroupDataAnalyzerDate;
import date.Style;
import set.settings.DefaultPage;
import set.settings.Settings;
import tools.Choose;
import tools.ErrorInterface;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

public class GroupDataAnalyzerSet extends JDialog {
    final CardLayout cardLayout=new CardLayout();
    private JPanel contentPane;
    private JButton buttonOK;
    private JPanel right;
    private Settings current;
    final DefaultPage defaultPage=new DefaultPage(this);
    private JButton buttonCancel;
    private JPanel up;
    private JPanel down;
    public GroupDataAnalyzerSet() {
        setContentPane(contentPane);
        right.setLayout(cardLayout);
        right.add(defaultPage.contentPane,defaultPage.name);
        setCurrent(defaultPage);
        buttonOK.addActionListener(ee -> {
            if (current!=defaultPage){
                current.onOK();
                try {
                    GroupDataAnalyzerDate.groupDataAnalyzerDate.write();
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
        buttonOK.addActionListener(e -> current.onOK());
        buttonCancel.addActionListener(e -> current.onCancel());
    }

    private void setCurrent(Settings set){
        if (current != set){
            if (current!=defaultPage&&current!=null){
                Choose choose=new Choose("是否保存设置");
                choose.setVisible(true);
                if (choose.choose){
                    current.save();
                }
            }
            cardLayout.show(right,set.name);
            current=set;
        }
    }

    public void setStyle() {
        HashSet<JComponent> jPanels = new HashSet<>();
        HashSet<JComponent> buttons = new HashSet<>();
        jPanels.add(contentPane);
        jPanels.add(up);
        jPanels.add(down);
        jPanels.add(right);
        buttons.add(buttonOK);
        buttons.add(buttonCancel);
        Style.setStyle(jPanels,buttons,null);
    }
}
