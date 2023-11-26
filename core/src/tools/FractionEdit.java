/*
 * 开发者:熊锦枫
 * 开发者邮箱:wyshazhisishen@yeah.net
 */

package tools;

import data.Style;
import math.Fraction;

import javax.swing.*;
import java.util.HashSet;

/**
 * @author wysha
 */
public class FractionEdit extends JPanel {
    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    private JPanel contentPane;
    private JSpinner up;
    private JSpinner down;
    private JLabel jLabel;
    private JLabel left;
    private JLabel downLabel;
    private JLabel upLabel;
    public FractionEdit() throws Throwable {
        this(null,new Fraction(1,1));
    }
    public void setText(String s){
        left.setText(s);
    }
    public FractionEdit(String s,Fraction fraction) {
        if (s!=null){
            left.setText(s);
        }
        if (fraction!=null){
            setFraction(fraction);
        }
        add(contentPane);
        setStyle();
    }
    public void setFraction(Fraction fraction){
        up.setValue(fraction.numerator);
        down.setValue(fraction.denominator);
    }
    public Fraction getFraction() throws Throwable {
        return new Fraction((int) up.getValue(),(int) down.getValue());
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
        Style.setStyle(null,buttons,null);
    }
}
