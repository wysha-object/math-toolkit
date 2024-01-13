package main;

import data.Style;
import math.function.AbstractFunction;
import math.math.object.Formula;
import math.math.objects.Variable;
import view.ErrorInterface;
import view.GetOutCome;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * @author wysha
 */
public class FunctionCalculatorGetValue extends JDialog {
    private final AbstractFunction abstractFunction;
    private final JLabel[] jLabels;
    private final JTextField[] textFields;
    private JPanel contentPane;
    private JButton buttonOkay;
    private JList<AbstractFunction> list;
    private JPanel down;
    private JPanel up;
    private JScrollPane jScrollPane;
    private JLabel jLabel;
    private JButton buttonCancel;
    private JPanel jTextFields;
    private JScrollPane js;

    public FunctionCalculatorGetValue(AbstractFunction abstractFunction) {
        this.abstractFunction = abstractFunction;
        List<Variable> variables = abstractFunction.getVariables();
        jLabels = new JLabel[variables.size()];
        textFields = new JTextField[variables.size()];
        if (abstractFunction.formula.variables.isEmpty()) {
            onOkay();
            return;
        }
        List<AbstractFunction> abstractFunctions = new ArrayList<>();
        abstractFunctions.add(abstractFunction);
        abstractFunctions.addAll(abstractFunction.getFunctions());
        list.setListData(abstractFunctions.toArray(new AbstractFunction[0]));
        setContentPane(contentPane);
        setModal(true);
        setTitle("计算函数");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        jTextFields.setLayout(new GridLayout());
        for (int i = 0; i < variables.size(); i++) {
            Variable variable = variables.get(i);
            JLabel j = new JLabel(variable.name + ":");
            j.setHorizontalAlignment(SwingConstants.RIGHT);
            jTextFields.add(j);
            JTextField jTextField = new JTextField();
            jTextFields.add(jTextField);
            jLabels[i] = j;
            textFields[i] = jTextField;
        }
        setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2);
        setStyle();
        buttonOkay.addActionListener(e -> onOkay());
        buttonCancel.addActionListener(e -> dispose());
        contentPane.registerKeyboardAction(e -> onOkay(), KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        contentPane.registerKeyboardAction(e -> dispose(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        setVisible(true);
    }

    public void setStyle() {
        HashSet<JComponent> jPanels = new HashSet<>();
        HashSet<JComponent> buttons = new HashSet<>();
        buttons.addAll(Arrays.asList(jLabels));
        buttons.addAll(Arrays.asList(textFields));
        HashSet<JList<?>> jLists = new HashSet<>();
        jPanels.add(js);
        jPanels.add(contentPane);
        jPanels.add(down);
        jPanels.add(up);
        jPanels.add(jScrollPane);
        jPanels.add(jTextFields);
        buttons.add(buttonOkay);
        buttons.add(jLabel);
        buttons.add(buttonCancel);
        jLists.add(list);
        Style.setStyle(jPanels, buttons, jLists);
    }

    private void onOkay() {
        try {
            ArrayList<Formula> fractions = new ArrayList<>();
            for (JTextField jTextField : textFields) {
                String s = jTextField.getText();
                fractions.add(Formula.valueOf(s,false));
            }
            GetOutCome getOutCome = new GetOutCome("计算结果", "计算结果:" + abstractFunction.operation(fractions).toString());
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
