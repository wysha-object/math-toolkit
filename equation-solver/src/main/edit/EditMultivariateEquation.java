
package main.edit;

import data.Style;
import main.MathGroupMainInterface;
import math.equation.multivariate.MultivariateEquation;

import javax.swing.*;
import java.util.HashSet;

/**
 * @author wysha
 */
public class EditMultivariateEquation extends AbstractEquationSolverEdits {
    public JPanel contentPane;

    public EditMultivariateEquation(JDialog jDialog, MathGroupMainInterface mathGroupMainInterface) {
        super(EditMultivariateEquation.class.toString(), jDialog, mathGroupMainInterface);
        setStyle();
    }

    @Override
    public void onOkay() {
        jDialog.dispose();
    }

    @Override
    public void onCancel() {
        jDialog.dispose();
    }

    private void setStyle() {
        HashSet<JComponent> jPanels = new HashSet<>();
        HashSet<JComponent> buttons = new HashSet<>();
        jPanels.add(contentPane);
        Style.setStyle(jPanels,buttons,null);
    }

    public void setEquation(MultivariateEquation multivariateEquation) {
    }
}
