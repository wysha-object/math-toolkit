package main.edit;

import data.Style;
import main.MathGroupMainInterface;
import math.function.multivariate.MultivariateFunction;
import view.ErrorInterface;
import view.MathInput;

import javax.swing.*;
import java.util.HashSet;

/**
 * @author wysha
 */
public class EditMultivariateFunction extends AbstractFunctionCalculatorEdits {
    public JPanel contentPane;
    private JLabel jLabel;
    private MathInput mathInput;

    public EditMultivariateFunction(JDialog jDialog, MathGroupMainInterface mathGroupMainInterface) {
        super(EditMultivariateFunction.class.toString(), jDialog, mathGroupMainInterface);
        setStyle();
        mathInput.setValue("function=");
    }

    @Override
    public void onOkay() {
        try {
            MultivariateFunction.valueOf(mathInput.getValue(), mathGroupMainInterface.mathGroup);
            jDialog.dispose();
        } catch (Throwable e) {
            ErrorInterface errorInterface = new ErrorInterface(
                    "创建失败,请检查\n",
                    e,
                    false
            );
            errorInterface.setVisible(true);
        }
    }

    @Override
    public void onCancel() {
        jDialog.dispose();
    }

    private void setStyle() {
        HashSet<JComponent> jPanels = new HashSet<>();
        HashSet<JComponent> buttons = new HashSet<>();
        jPanels.add(contentPane);
        buttons.add(jLabel);
        buttons.add(mathInput);
        Style.setStyle(jPanels, buttons, null);
    }

    public void setFunction(MultivariateFunction multivariateFunction) {
        if (multivariateFunction != null) {
            mathInput.setValue(multivariateFunction.name + "'=" + multivariateFunction.formula.toString());
        }
    }
}
