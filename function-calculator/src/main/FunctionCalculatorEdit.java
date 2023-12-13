
package main;

import data.Style;
import main.edit.AbstractFunctionCalculatorEdits;
import main.edit.EditMultivariateFunction;
import main.edit.EditOneVariableOneDegreeFunction;
import math.function.AbstractFunction;
import math.function.multivariate.MultivariateFunction;
import math.function.onevariable.OneVariableOneDegreeFunction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Objects;

/**
 * @author wysha
 */
public class FunctionCalculatorEdit extends MathGroupView {
    final CardLayout cardLayout=new CardLayout();
    private JPanel contentPane;
    private JButton buttonOkay;
    private JButton buttonCancel;
    private JPanel down;
    private AbstractFunctionCalculatorEdits current;
    final EditMultivariateFunction editMultivariateFunction = new EditMultivariateFunction(this, mathGroupMainInterface);
    final EditOneVariableOneDegreeFunction editOneVariableOneDegreeFunction = new EditOneVariableOneDegreeFunction(this, mathGroupMainInterface);
    private JPanel up;

    public FunctionCalculatorEdit(MathGroupMainInterface mathGroupMainInterface, AbstractFunction abstractFunction) throws Throwable {
        super(mathGroupMainInterface);
        up.setLayout(cardLayout);
        if (abstractFunction != null) {
            comboBox.setVisible(false);
            if (abstractFunction instanceof MultivariateFunction) {
                MultivariateFunction multivariateFunction = (MultivariateFunction) abstractFunction;
                editMultivariateFunction.setFunction(multivariateFunction);
                up.add(editMultivariateFunction.contentPane,editMultivariateFunction.name);
                setCurrent(editMultivariateFunction);
            } else if (abstractFunction instanceof OneVariableOneDegreeFunction) {
                OneVariableOneDegreeFunction oneVariableOneDegreeFunction = (OneVariableOneDegreeFunction) abstractFunction;
                editOneVariableOneDegreeFunction.setFunction(oneVariableOneDegreeFunction);
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
        setTitle("创建函数");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        contentPane.registerKeyboardAction(e->current.onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        setStyle();
        buttonOkay.addActionListener(e -> current.onOkay());
        buttonCancel.addActionListener(e->current.onCancel());
    }
    private JComboBox<Functions> comboBox;

    private void setCurrent(AbstractFunctionCalculatorEdits edit) {
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
        MultivariateFunction("多元函数/非标准式"),OneVariable_OneDegree_Function("一元一次函数");
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
