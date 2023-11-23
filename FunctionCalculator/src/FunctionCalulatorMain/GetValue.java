/*
 * 开发者:熊锦枫
 * 开发者邮箱:wyshazhisishen@yeah.net
 */

package FunctionCalulatorMain;

import date.Style;
import math.Fraction;
import FunctionCalulatorMath.Function;
import math.Variable;
import tools.ErrorInterface;
import tools.GetOutCome;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class GetValue extends JDialog {
    private final Function function;
    private JPanel contentPane;
    private JButton buttonOK;
    private JList<Function> list;
    private JPanel down;
    private JPanel up;
    private JScrollPane jScrollPane;
    private JLabel jLabel;
    private JButton buttonCancel;
    private JPanel jTextFields;
    private final JLabel[] jLabels;
    private final JTextField[] textFields;

    public GetValue(Function function) {
        this.function = function;
        List<Variable> variables=function.getVariables();
        jLabels=new JLabel[variables.size()];
        textFields=new JTextField[variables.size()];
        if (function.variables.isEmpty()){
            onOK();
            return;
        }
        List<Function> functions = new ArrayList<>();
        functions.add(function);
        functions.addAll(function.getFunctions());
        list.setListData(functions.toArray(new Function[0]));
        setContentPane(contentPane);
        setModal(true);
        setTitle("计算函数");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        jTextFields.setLayout(new GridLayout());
        for (int i = 0; i < variables.size(); i++) {
            Variable variable = variables.get(i);
            JLabel j=new JLabel(variable.name+":");
            j.setHorizontalAlignment(SwingConstants.RIGHT);
            jTextFields.add(j);
            JTextField jTextField = new JTextField();
            jTextFields.add(jTextField);
            jLabels[i]=j;
            textFields[i]=jTextField;
        }
        setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2);
        setStyle();
        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> dispose());
        contentPane.registerKeyboardAction(e -> onOK(), KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        contentPane.registerKeyboardAction(e -> dispose(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        setVisible(true);
    }

    public void setStyle() {
        HashSet<JComponent> jPanels = new HashSet<>();
        HashSet<JComponent> buttons = new HashSet<>();
        buttons.addAll(Arrays.asList(jLabels));
        buttons.addAll(Arrays.asList(textFields));
        HashSet<JList<?>> jLists=new HashSet<>();
        jPanels.add(contentPane);
        jPanels.add(down);
        jPanels.add(up);
        jPanels.add(jScrollPane);
        jPanels.add(jTextFields);
        buttons.add(buttonOK);
        buttons.add(jLabel);
        buttons.add(buttonCancel);
        jLists.add(list);
        Style.setStyle(jPanels,buttons,jLists);
    }

    private void onOK() {
        try {
            ArrayList<Fraction> fractions = new ArrayList<>();
            for (JTextField jTextField:textFields) {
                String s=jTextField.getText();
                Fraction fraction;
                try {
                    fraction = new Fraction(Double.parseDouble(s), 1);
                } catch (Throwable e) {
                    String[] ss = s.split("/");
                    fraction = new Fraction(Double.parseDouble(ss[0]), Double.parseDouble(ss[1]));
                }
                fractions.add(fraction);
            }
            GetOutCome getOutCome = new GetOutCome("计算结果", "计算结果:" + function.operation(fractions).toString());
            getOutCome.setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 3, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 3);
            getOutCome.setVisible(true);
        } catch (Throwable exception) {
            ErrorInterface errorInterface = new ErrorInterface(
                    "函数计算失败,请检查\n",
                    exception,
                    false
            );
            errorInterface.setVisible(true);
            return;
        }
        dispose();
    }
}
