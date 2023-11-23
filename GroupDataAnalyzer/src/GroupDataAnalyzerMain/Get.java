/*
 * 开发者:熊锦枫
 * 开发者邮箱:wyshazhisishen@yeah.net
 */

package GroupDataAnalyzerMain;

import GroupDataAnalyzerMath.GroupDate;
import date.Style;

import javax.swing.*;
import java.util.Arrays;
import java.util.HashSet;

public class Get extends JDialog {
    private JLabel name;
    private JLabel values;
    private JPanel contentPane;
    private JLabel average;
    private JButton button;
    private JLabel variance;
    private JLabel median;

    public Get(GroupDate groupDate) throws Throwable {
        setContentPane(contentPane);
        button.addActionListener(e -> dispose());
        name.setText(groupDate.toString());
        values.setText("包含的数据:"+Arrays.toString(groupDate.fractions));
        average.setText("平均数:"+groupDate.getAverage().toString());
        variance.setText("方差:"+groupDate.getVariance().toString());
        median.setText("中位数:"+groupDate.getMedian().toString());
        setStyle();
    }
    public void setStyle() {
        HashSet<JComponent> jPanels = new HashSet<>();
        HashSet<JComponent> buttons = new HashSet<>();
        jPanels.add(contentPane);
        buttons.add(name);
        buttons.add(values);
        buttons.add(average);
        buttons.add(variance);
        buttons.add(median);
        buttons.add(button);
        Style.setStyle(jPanels,buttons,null);
    }
}
