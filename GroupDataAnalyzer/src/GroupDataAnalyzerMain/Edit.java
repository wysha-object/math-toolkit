/*
 * 开发者:熊锦枫
 * 开发者邮箱:wyshazhisishen@yeah.net
 */

package GroupDataAnalyzerMain;

import GroupDataAnalyzerMath.GroupDate;
import date.Style;
import math.Fraction;
import tools.ErrorInterface;
import tools.FractionEdit;
import tools.GetAndSetList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Edit extends JDialog {
    List<Object> groupDates=new ArrayList<>();
    final List<Object> remove=new ArrayList<>(GroupDataAnalyzerDate.groupDataAnalyzerDate.groupDates);
    final GridLayout gridLayout=new GridLayout(
            -1,
            (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()/100)
    );
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel down;
    private JLabel jLabel;
    private JSpinner spinner;
    private JPanel rightJPanel;
    private JPanel left;
    private JScrollPane right;
    private JTextField textField;
    private JLabel nameJLabel;
    private FractionEdit edit;
    private JButton setAdd;
    private JTextArea textArea;
    FractionEdit[] fractionEdits;
    public Edit(GroupDate groupDate) throws Throwable {
        edit.setText("默认添加:");
        setContentPane(contentPane);
        setModal(true);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        spinner.setModel(new SpinnerNumberModel(1,1,Integer.MAX_VALUE,1));
        fractionEdits=new FractionEdit[1];
        fractionEdits[0]=new FractionEdit("第1个数据:",edit.getFraction());
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        rightJPanel.setLayout(gridLayout);
        rightJPanel.add(fractionEdits[0]);
        if (groupDate!=null){
            for (FractionEdit fractionEdit:fractionEdits){
                rightJPanel.remove(fractionEdit);
            }
            textField.setText(groupDate.name+"'");
            spinner.setValue(groupDate.fractions.length);
            fractionEdits=new FractionEdit[groupDate.fractions.length];
            for (int i=0;i<groupDate.fractions.length;i++){
                fractionEdits[i]=new FractionEdit("第"+(i+1)+"个数据:",groupDate.fractions[i]);
            }
            for (FractionEdit fractionEdit : fractionEdits) {
                rightJPanel.add(fractionEdit);
            }
            pack();
            setSize(getWidth(),getHeight());
        }
        spinner.addChangeListener(e -> {
            try {
                for (FractionEdit fractionEdit:fractionEdits){
                    rightJPanel.remove(fractionEdit);
                }
                int v=(int)spinner.getValue();
                FractionEdit[] f=new FractionEdit[v];
                for (int i = 0; i < f.length; i++) {
                    f[i]= i<fractionEdits.length?fractionEdits[i]:new FractionEdit("第"+(i+1)+"个数据:",edit.getFraction());
                }
                fractionEdits=f;
                for (FractionEdit fractionEdit : fractionEdits) {
                    rightJPanel.add(fractionEdit);
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
            GetAndSetList getAndSetList=new GetAndSetList("选择需要的数据组",remove,groupDates);
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

    private void onOK() {
        try {
            Fraction[] fractions=new Fraction[fractionEdits.length];
            for (int i = 0; i < fractions.length; i++) {
                fractions[i]=fractionEdits[i].getFraction();
            }
            new GroupDate(fractions,groupDates.toArray(new GroupDate[0]),textField.getText());
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
        jPanels.add(rightJPanel);
        jPanels.add(down);
        buttons.add(jLabel);
        buttons.add(nameJLabel);
        buttons.add(textField);
        buttons.add(spinner);
        buttons.add(edit);
        buttons.add(buttonOK);
        buttons.add(buttonCancel);
        buttons.add(textArea);
        buttons.add(setAdd);
        Style.setStyle(jPanels,buttons,jLists);
    }
}
