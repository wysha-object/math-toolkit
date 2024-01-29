package cn.com.wysha.math_toolkit.main.edit;

import cn.com.wysha.math_toolkit.data.Style;
import cn.com.wysha.math_toolkit.main.MathGroupMainInterface;
import cn.com.wysha.math_toolkit.math.function.multivariate.MultivariateFunction;
import cn.com.wysha.math_toolkit.views.ErrorInterface;
import cn.com.wysha.math_toolkit.views.MathInput;

import javax.swing.*;
import java.util.HashSet;

/**
 * @author wysha
 */
public class EditMultivariateFunction extends AbstractFunctionCalculatorEdits {
    public JPanel contentPane;
    private JLabel jLabel;
    private MathInput mathInput;
    private JCheckBox checkBox;

    public EditMultivariateFunction(JDialog jDialog, MathGroupMainInterface mathGroupMainInterface) {
        super(EditMultivariateFunction.class.toString(), jDialog, mathGroupMainInterface);
        setStyle();
        mathInput.setValue("function=");
    }

    @Override
    public void onOkay() {
        try {
            MultivariateFunction.valueOf(mathInput.getValue(), mathGroupMainInterface.mathGroup, checkBox.isSelected());
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
