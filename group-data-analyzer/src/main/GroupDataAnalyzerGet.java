
package main;

import data.Style;
import math.groupdata.GroupData;

import javax.swing.*;
import java.util.Arrays;
import java.util.HashSet;

/**
 * @author wysha
 */
public class GroupDataAnalyzerGet extends JDialog {
    private JLabel name;
    private JTextArea values;
    private JPanel contentPane;
    private JLabel average;
    private JButton button;
    private JLabel variance;
    private JLabel median;

    public GroupDataAnalyzerGet(GroupData groupData) throws Throwable {
        setTitle("详情");
        values.setLineWrap(true);
        values.setWrapStyleWord(true);
        values.setEditable(false);
        setContentPane(contentPane);
        button.addActionListener(e -> dispose());
        name.setText(groupData.toString());
        StringBuilder stringBuilder=new StringBuilder("所选数组包含的数据:" + Arrays.toString(groupData.fractions));
        stringBuilder.append("\n已添加数组列表:").append(Arrays.toString(groupData.groupData)).append('\n');
        for (GroupData g:groupData.groupData){
            stringBuilder.append(g.name).append("包含的数据:").append(Arrays.toString(g.fractions)).append('\n');
        }
        values.setText(stringBuilder.toString());
        average.setText("平均数:" + groupData.getAverage().toString());
        variance.setText("方差:" + groupData.getVariance().toString());
        median.setText("中位数:" + groupData.getMedian().toString());
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
