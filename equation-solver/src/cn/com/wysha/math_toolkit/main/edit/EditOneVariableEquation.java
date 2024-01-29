package cn.com.wysha.math_toolkit.main.edit;

import cn.com.wysha.math_toolkit.data.Style;
import cn.com.wysha.math_toolkit.main.MathGroupMainInterface;
import cn.com.wysha.math_toolkit.math.equation.oneVariable.OneVariableOneDegreeEquation;

import javax.swing.*;
import java.util.HashSet;

/**
 * @author wysha
 */
public class EditOneVariableEquation extends AbstractEquationSolverEdits {
    public JPanel contentPane;

    public EditOneVariableEquation(JDialog jDialog, MathGroupMainInterface mathGroupMainInterface) {
        super(EditOneVariableEquation.class.toString(), jDialog, mathGroupMainInterface);
        setStyle();
    }

    @Override
    public void onOkay() {
    }

    @Override
    public void onCancel() {
        mathGroupMainInterface.dispose();
    }

    private void setStyle() {
        HashSet<JComponent> jPanels = new HashSet<>();
        HashSet<JComponent> buttons = new HashSet<>();
        jPanels.add(contentPane);
        Style.setStyle(jPanels, buttons, null);
    }

    public void setEquation(OneVariableOneDegreeEquation oneVariableOneDegreeEquation) {
    }
}
