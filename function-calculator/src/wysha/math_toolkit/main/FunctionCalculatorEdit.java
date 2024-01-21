package wysha.math_toolkit.main;

import wysha.math_toolkit.data.Style;
import wysha.math_toolkit.main.edit.AbstractFunctionCalculatorEdits;
import wysha.math_toolkit.main.edit.EditMultivariateFunction;
import wysha.math_toolkit.math.function.AbstractFunction;
import wysha.math_toolkit.math.function.multivariate.MultivariateFunction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Objects;

enum Functions {
    /*

     */
    MultivariateFunction("多元函数/非标准式");
    final String name;

    Functions(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

/**
 * @author wysha
 */
public class FunctionCalculatorEdit extends MathGroupView {
    final CardLayout cardLayout = new CardLayout();
    final EditMultivariateFunction editMultivariateFunction = new EditMultivariateFunction(this, mathGroupMainInterface);
    private JPanel contentPane;
    private JButton buttonOkay;
    private JButton buttonCancel;
    private JPanel down;
    private AbstractFunctionCalculatorEdits current;
    private JPanel up;
    private JComboBox<Functions> comboBox;

    public FunctionCalculatorEdit(MathGroupMainInterface mathGroupMainInterface, AbstractFunction abstractFunction) {
        super(mathGroupMainInterface);
        up.setLayout(cardLayout);
        if (abstractFunction != null) {
            comboBox.setVisible(false);
            if (abstractFunction instanceof MultivariateFunction multivariateFunction) {
                editMultivariateFunction.setFunction(multivariateFunction);
                up.add(editMultivariateFunction.contentPane, editMultivariateFunction.name);
                setCurrent(editMultivariateFunction);
            }
        } else {
            up.add(editMultivariateFunction.contentPane, editMultivariateFunction.name);
            Functions[] functions = new Functions[]{
                    Functions.MultivariateFunction,
            };
            comboBox.setModel(new DefaultComboBoxModel<>(functions));
            comboBox.addActionListener(e -> {
                Functions requireNonNull = (Functions) Objects.requireNonNull(comboBox.getSelectedItem());
                if (requireNonNull == Functions.MultivariateFunction) {
                    setCurrent(editMultivariateFunction);
                }
            });
            comboBox.setSelectedItem(functions[0]);
        }
        setContentPane(contentPane);
        setModal(true);
        setTitle("创建函数");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        contentPane.registerKeyboardAction(e -> current.onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        setStyle();
        buttonOkay.addActionListener(e -> current.onOkay());
        buttonCancel.addActionListener(e -> current.onCancel());
    }

    private void setCurrent(AbstractFunctionCalculatorEdits edit) {
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
}