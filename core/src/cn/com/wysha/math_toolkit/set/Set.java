package cn.com.wysha.math_toolkit.set;

import cn.com.wysha.math_toolkit.views.Choose;

import javax.swing.*;
import java.awt.*;

/**
 * @author wysha
 * <p>
 * 继承者请单独将最终展示页面的值赋值给super.show<br/>
 * 来自set/NecessarySet.java的示例:super.show = right;
 */
public class Set extends JDialog {
    final CardLayout cardLayout = new CardLayout();
    final DefaultPage defaultPage = new DefaultPage(this);
    protected AbstractSetSubpages current;
    JPanel show;

    protected void setCurrent(AbstractSetSubpages set) {
        if (current != set) {
            if (current != defaultPage && current != null) {
                Choose choose = new Choose("是否保存设置");
                choose.setVisible(true);
                if (choose.choose) {
                    current.save();
                }
            }
            cardLayout.show(show, set.name);
            current = set;
        }
    }
}
