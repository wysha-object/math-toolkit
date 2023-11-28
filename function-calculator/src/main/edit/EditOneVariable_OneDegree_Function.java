
package main.edit;

import data.Style;
import math.OneVariableOneDegreeFunction;
import tools.ErrorInterface;
import tools.FractionEdit;

import javax.swing.*;
import java.util.HashSet;

/**
 * @author wysha
 */
public class EditOneVariable_OneDegree_Function extends AbstractFunctionCalculatorEdits {
    public JPanel contentPane;
    private JLabel leftLabel;
    private JLabel rightLabel;
    private JTextField textField;
    private JLabel nameLabel;
    private JLabel kLabel;
    private JLabel bLabel;
    private FractionEdit kEdit;
    private FractionEdit bEdit;
    public EditOneVariable_OneDegree_Function(JDialog jDialog) {
        super(EditOneVariable_OneDegree_Function.class.toString(), jDialog);
        kEdit.setText("k:");
        bEdit.setText("b:");
        setStyle();
    }
    @Override
    public void onOkay() {
        try {
            new OneVariableOneDegreeFunction(
                    textField.getText(),
                    kEdit.getFraction(),
                    bEdit.getFraction()
            );
            jDialog.dispose();
        } catch (Throwable e) {
            ErrorInterface errorInterface = new ErrorInterface(
                    "函数创建失败,请检查\n",
                    e,
                    false);
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
        buttons.add(textField);
        buttons.add(leftLabel);
        buttons.add(rightLabel);
        buttons.add(kLabel);
        buttons.add(bLabel);
        buttons.add(nameLabel);
        Style.setStyle(jPanels,buttons,null);
    }

    public void setFunction(OneVariableOneDegreeFunction oneVariableOneDegreeFunction) {
        textField.setText(oneVariableOneDegreeFunction.name+"'");
        kEdit.setFraction(oneVariableOneDegreeFunction.fractions.get(0));
        bEdit.setFraction(oneVariableOneDegreeFunction.fractions.get(2));
    }
}
