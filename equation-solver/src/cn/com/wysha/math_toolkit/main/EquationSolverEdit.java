package cn.com.wysha.math_toolkit.main;

import cn.com.wysha.math_toolkit.data.Style;
import cn.com.wysha.math_toolkit.main.edit.AbstractEquationSolverEdits;
import cn.com.wysha.math_toolkit.main.edit.EditMultivariateEquation;
import cn.com.wysha.math_toolkit.main.edit.EditOneVariableEquation;
import cn.com.wysha.math_toolkit.math.equation.AbstractEquation;
import cn.com.wysha.math_toolkit.math.equation.multivariate.MultivariateEquation;
import cn.com.wysha.math_toolkit.math.equation.oneVariable.OneVariableOneDegreeEquation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Objects;

/**
 * @author wysha
 */
public class EquationSolverEdit extends MathGroupView {
    final CardLayout cardLayout = new CardLayout();
    final EditMultivariateEquation editMultivariateFunction = new EditMultivariateEquation(this, mathGroupMainInterface);
    final EditOneVariableEquation editOneVariableOneDegreeFunction = new EditOneVariableEquation(this, mathGroupMainInterface);
    private JPanel contentPane;
    private JButton buttonOkay;
    private JButton buttonCancel;
    private JPanel down;
    private AbstractEquationSolverEdits current;
    private JPanel up;
    private JComboBox<Functions> comboBox;

    public EquationSolverEdit(MathGroupMainInterface mathGroupMainInterface, AbstractEquation abstractFunction) {
        super(mathGroupMainInterface);
        up.setLayout(cardLayout);
        if (abstractFunction != null) {
            comboBox.setVisible(false);
            if (abstractFunction instanceof MultivariateEquation multivariateFunction) {
                editMultivariateFunction.setEquation(multivariateFunction);
                up.add(editMultivariateFunction.contentPane, editMultivariateFunction.name);
                setCurrent(editMultivariateFunction);
            } else if (abstractFunction instanceof OneVariableOneDegreeEquation oneVariableOneDegreeFunction) {
                editOneVariableOneDegreeFunction.setEquation(oneVariableOneDegreeFunction);
                up.add(editOneVariableOneDegreeFunction.contentPane, editOneVariableOneDegreeFunction.name);
                setCurrent(editOneVariableOneDegreeFunction);
            }
        } else {
            up.add(editMultivariateFunction.contentPane, editMultivariateFunction.name);
            up.add(editOneVariableOneDegreeFunction.contentPane, editOneVariableOneDegreeFunction.name);
            Functions[] functions = new Functions[]{
                    Functions.MultivariateFunction,
                    Functions.OneVariable_OneDegree_Function
            };
            comboBox.setModel(new DefaultComboBoxModel<>(functions));
            comboBox.addActionListener(e -> {
                Functions requireNonNull = (Functions) Objects.requireNonNull(comboBox.getSelectedItem());
                if (requireNonNull == Functions.MultivariateFunction) {
                    setCurrent(editMultivariateFunction);
                } else if (requireNonNull == Functions.OneVariable_OneDegree_Function) {
                    setCurrent(editOneVariableOneDegreeFunction);
                }
            });
            comboBox.setSelectedItem(functions[0]);
        }
        setContentPane(contentPane);
        setModal(true);
        setTitle("创建方程");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        contentPane.registerKeyboardAction(e -> current.onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        setStyle();
        buttonOkay.addActionListener(e -> current.onOkay());
        buttonCancel.addActionListener(e -> current.onCancel());
    }

    private void setCurrent(AbstractEquationSolverEdits edit) {
        if (current != edit) {
            cardLayout.show(up, edit.name);
            current = edit;
        }
    }

    public void setStyle() {
        HashSet<JComponent> jPanels = new HashSet<>();
        HashSet<JComponent> buttons = new HashSet<>();
        jPanels.add(contentPane);
        jPanels.add(down);
        jPanels.add(up);
        buttons.add(buttonOkay);
        buttons.add(buttonCancel);
        buttons.add(comboBox);
        Style.setStyle(jPanels, buttons, null);
    }

    enum Functions {
        /*

         */
        MultivariateFunction("多元方程"), OneVariable_OneDegree_Function("一元方程");
        final String name;

        Functions(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
