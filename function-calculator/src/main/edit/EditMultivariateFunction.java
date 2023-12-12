
package main.edit;

import data.Style;
import main.MathGroupMainInterface;
import math.function.multivariate.MultivariateFunction;
import tools.ErrorInterface;

import javax.swing.*;
import java.util.HashSet;

/**
 * @author wysha
 */
public class EditMultivariateFunction extends AbstractFunctionCalculatorEdits {
    private JTextField textField;
    private JLabel jLabel;
    public JPanel contentPane;
    private JScrollPane jScrollPane;
    private JTextArea jTextArea;

    public EditMultivariateFunction(JDialog jDialog, MathGroupMainInterface mathGroupMainInterface) {
        super(EditMultivariateFunction.class.toString(), jDialog, mathGroupMainInterface);
        setStyle();
        jTextArea.setLineWrap(true);
        jTextArea.setEditable(false);
    }

    @Override
    public void onOkay() {
        try {
            MultivariateFunction.valueOf(textField.getText(), mathGroupMainInterface.mathGroup);
            jDialog.dispose();
        } catch (Throwable e) {
            ErrorInterface errorInterface = new ErrorInterface(
                    "函数创建失败,请检查\n",
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
        jPanels.add(jScrollPane);
        buttons.add(jLabel);
        buttons.add(textField);
        buttons.add(jTextArea);
        Style.setStyle(jPanels,buttons,null);
    }

    public void setFunction(MultivariateFunction multivariateFunction) {
        if (multivariateFunction!=null){
            textField.setText(multivariateFunction.name + "'=" + multivariateFunction.formula.toString());
        }
    }
}
