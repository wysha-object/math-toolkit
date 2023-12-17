package view;

import data.Style;
import math.math.numberObject.Fraction;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.HashSet;

/**
 * @author wysha
 */
public class FractionEdit extends JPanel {
    private JPanel contentPane;
    private JSpinner up;
    private JSpinner down;
    private JLabel jLabel;
    private JLabel left;
    private JLabel downLabel;
    private JLabel upLabel;

    public FractionEdit() throws Throwable {
        this(null, new Fraction(BigDecimal.ONE));
    }

    public FractionEdit(String s, Fraction fraction) {
        if (s != null) {
            left.setText(s);
        }
        if (fraction != null) {
            setFraction(fraction);
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
        left.setText(s);
    }

    public Fraction getFraction() throws Throwable {
        return new Fraction(new BigDecimal(String.valueOf(up.getValue())), new BigDecimal(String.valueOf(down.getValue())));
    }

    public void setFraction(Fraction fraction) {
        up.setValue(fraction.numerator);
        down.setValue(fraction.denominator);
    }

    private void setStyle() {
        HashSet<JComponent> buttons = new HashSet<>();
        buttons.add(contentPane);
        buttons.add(up);
        buttons.add(down);
        buttons.add(jLabel);
        buttons.add(left);
        buttons.add(upLabel);
        buttons.add(downLabel);
        Style.setStyle(null, buttons, null);
    }
}
