package cn.com.wysha.math_toolkit.views;

import cn.com.wysha.math_toolkit.data.Style;
import cn.com.wysha.math_toolkit.math.math.object.Formula;

import javax.swing.*;
import java.util.HashSet;

/**
 * @author wysha
 */
public class FormulaEdit extends JPanel {
    private JPanel contentPane;
    private JLabel jLabel;
    private JTextField textField;

    public FormulaEdit() throws Throwable {
        this(null, new Formula());
    }

    public FormulaEdit(String s, Formula formula) {
        if (s != null) {
            jLabel.setText(s);
        }
        if (formula != null) {
            setFormula(formula);
        }
        add(contentPane);
        setStyle();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public void setText(String s) {
        jLabel.setText(s);
    }

    public Formula getFormula() {
        return Formula.valueOf(textField.getText(), false);
    }

    public void setFormula(Formula formula) {
        textField.setText(formula.toString());
    }

    private void setStyle() {
        HashSet<JComponent> buttons = new HashSet<>();
        buttons.add(contentPane);
        buttons.add(jLabel);
        buttons.add(jLabel);
        Style.setStyle(null, buttons, null);
    }
}
