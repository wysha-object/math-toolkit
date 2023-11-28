
package main;

import data.Style;
import math.Fraction;
import math.GroupData;
import tools.ErrorInterface;
import tools.FractionEdit;
import tools.GetAndSetList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author wysha
 */
public class GroupDataEdit extends JDialog {
    List<Object> groupDates=new ArrayList<>();
    final List<Object> remove = new ArrayList<>(GroupDataAnalyzerData.groupDataAnalyzerDate.groupData);
    final GridLayout gridLayout=new GridLayout(
            -1,
            (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()/100)
    );
    private JPanel contentPane;
    private JButton buttonOkay;
    private JButton buttonCancel;
    private JPanel down;
    private JLabel jLabel;
    private JSpinner spinner;
    private JPanel rightPanel;
    private JPanel left;
    private JScrollPane right;
    private JTextField textField;
    private JLabel nameLabel;
    private FractionEdit edit;
    private JButton setAdd;
    private JTextArea textArea;
    FractionEdit[] fractionEdits;

    public GroupDataEdit(GroupData groupData) throws Throwable {
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        edit.setText("默认添加:");
        setContentPane(contentPane);
        setModal(true);

        buttonOkay.addActionListener(e -> onOkay());

        buttonCancel.addActionListener(e -> onCancel());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        spinner.setModel(new SpinnerNumberModel(1,1,Integer.MAX_VALUE,1));
        fractionEdits=new FractionEdit[1];
        fractionEdits[0]=new FractionEdit("第1个数据:",edit.getFraction());
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        rightPanel.setLayout(gridLayout);
        rightPanel.add(fractionEdits[0]);
        if (groupData != null) {
            for (FractionEdit fractionEdit:fractionEdits){
                rightPanel.remove(fractionEdit);
            }
            textField.setText(groupData.name + "'");
            spinner.setValue(groupData.fractions.length);
            fractionEdits = new FractionEdit[groupData.fractions.length];
            for (int i = 0; i < groupData.fractions.length; i++) {
                fractionEdits[i] = new FractionEdit("第" + (i + 1) + "个数据:", groupData.fractions[i]);
            }
            for (FractionEdit fractionEdit : fractionEdits) {
                rightPanel.add(fractionEdit);
            }
            pack();
            setSize(getWidth(),getHeight());
        }
        spinner.addChangeListener(e -> {
            try {
                for (FractionEdit fractionEdit:fractionEdits){
                    rightPanel.remove(fractionEdit);
                }
                int v=(int)spinner.getValue();
                FractionEdit[] f=new FractionEdit[v];
                for (int i = 0; i < f.length; i++) {
                    f[i]= i<fractionEdits.length?fractionEdits[i]:new FractionEdit("第"+(i+1)+"个数据:",edit.getFraction());
                }
                fractionEdits=f;
                for (FractionEdit fractionEdit : fractionEdits) {
                    rightPanel.add(fractionEdit);
                }
                int w=getWidth();
                int h=getHeight();
                pack();
                setSize(w,h);
            } catch (Throwable ex) {
                new ErrorInterface(
                        "值异常",
                        ex,
                        false
                ).setVisible(true);
            }
        });
        setStyle();
        setAdd.addActionListener(e -> {
            GetAndSetList getAndSetList=new GetAndSetList("选择需要的数据组",remove, groupDates);
            getAndSetList.setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2);
            getAndSetList.setVisible(true);
            groupDates=getAndSetList.getChoose();
            StringBuilder s=new StringBuilder();
            for (Object g:groupDates){
                s.append(g.toString()).append("\n");
            }
            textArea.setText(String.valueOf(s));
        });
    }

    private void onOkay() {
        try {
            Fraction[] fractions=new Fraction[fractionEdits.length];
            for (int i = 0; i < fractions.length; i++) {
                fractions[i]=fractionEdits[i].getFraction();
            }
            GroupData[] groupDataArray=new GroupData[groupDates.size()];
            for (int i=0;i<groupDates.size();i++){
                groupDataArray[i]=(GroupData) groupDates.get(i);
            }
            new GroupData(fractions, groupDataArray, textField.getText());
            dispose();
        } catch (Throwable e) {
            ErrorInterface errorInterface = new ErrorInterface(
                    "数据组创建失败,请检查\n",
                    e,
                    false);
            errorInterface.setVisible(true);
        }
    }

    private void onCancel() {
        dispose();
    }
    public void setStyle() {
        HashSet<JComponent> jPanels = new HashSet<>();
        HashSet<JComponent> buttons = new HashSet<>();
        HashSet<JList<?>> jLists=new HashSet<>();
        jPanels.add(contentPane);
        jPanels.add(left);
        jPanels.add(right);
        jPanels.add(rightPanel);
        jPanels.add(down);
        buttons.add(jLabel);
        buttons.add(nameLabel);
        buttons.add(textField);
        buttons.add(spinner);
        buttons.add(edit);
        buttons.add(buttonOkay);
        buttons.add(buttonCancel);
        buttons.add(textArea);
        buttons.add(setAdd);
        Style.setStyle(jPanels,buttons,jLists);
    }
}
