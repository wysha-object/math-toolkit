
package main;

import data.Style;
import main.edit.AbstractEquationSolverEdits;
import main.edit.EditMultivariateEquation;
import main.edit.EditOneVariableEquation;
import math.equation.AbstractEquation;
import math.equation.multivariate.MultivariateEquation;
import math.equation.oneVariable.OneVariableOneDegreeEquation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Objects;

/**
 * @author wysha
 */
public class EquationSolverEdit extends JDialog {
    final CardLayout cardLayout=new CardLayout();
    private JPanel contentPane;
    private JButton buttonOkay;
    private JButton buttonCancel;
    private JPanel down;
    private AbstractEquationSolverEdits current;
    final EditMultivariateEquation editMultivariateFunction=new EditMultivariateEquation(this);
    final EditOneVariableEquation editOneVariableOneDegreeFunction=new EditOneVariableEquation(this);
    private JPanel up;
    public EquationSolverEdit(AbstractEquation abstractFunction) {
        up.setLayout(cardLayout);
        if (abstractFunction != null) {
            comboBox.setVisible(false);
            if (abstractFunction instanceof MultivariateEquation) {
                MultivariateEquation multivariateFunction = (MultivariateEquation) abstractFunction;
                editMultivariateFunction.setEquation(multivariateFunction);
                up.add(editMultivariateFunction.contentPane,editMultivariateFunction.name);
                setCurrent(editMultivariateFunction);
            } else if (abstractFunction instanceof OneVariableOneDegreeEquation) {
                OneVariableOneDegreeEquation oneVariableOneDegreeFunction = (OneVariableOneDegreeEquation) abstractFunction;
                editOneVariableOneDegreeFunction.setEquation(oneVariableOneDegreeFunction);
                up.add(editOneVariableOneDegreeFunction.contentPane,editOneVariableOneDegreeFunction.name);
                setCurrent(editOneVariableOneDegreeFunction);
            }
        }else {
            up.add(editMultivariateFunction.contentPane,editMultivariateFunction.name);
            up.add(editOneVariableOneDegreeFunction.contentPane,editOneVariableOneDegreeFunction.name);
            Functions[] functions = new Functions[]{
                    Functions.MultivariateFunction,
                    Functions.OneVariable_OneDegree_Function
            };
            comboBox.setModel(new DefaultComboBoxModel<>(functions));
            comboBox.addActionListener(e ->{
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
        contentPane.registerKeyboardAction(e->current.onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        setStyle();
        buttonOkay.addActionListener(e -> current.onOkay());
        buttonCancel.addActionListener(e->current.onCancel());
    }
    private JComboBox<Functions> comboBox;

    private void setCurrent(AbstractEquationSolverEdits edit) {
        if (current != edit){
            cardLayout.show(up,edit.name);
            current=edit;
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
        Style.setStyle(jPanels,buttons,null);
    }

    enum Functions {
        /*

         */
        MultivariateFunction("多元方程"),OneVariable_OneDegree_Function("一元方程");
        final String name;
        Functions(String name){
            this.name=name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
