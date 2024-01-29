package cn.com.wysha.math_toolkit.views;

import javax.swing.*;

public class MathInput extends JPanel {
    private JPanel panel;
    private JPanel number;
    private JButton but0;
    private JButton but1;
    private JButton but2;
    private JButton but3;
    private JButton but4;
    private JButton but5;
    private JButton but6;
    private JButton but7;
    private JButton but8;
    private JButton but9;
    private JPanel ao;
    private JButton butAdd;
    private JButton butSub;
    private JButton butMul;
    private JButton butDiv;
    private JButton butPow;
    private JButton butPoo;
    private JTextField textField;
    private JPanel tools;
    private JButton delButton;
    private JButton fluButton;
    private JPanel letter;
    private JButton xButton;
    private JButton yButton;
    private JButton zButton;
    private JButton left;
    private JButton right;

    public MathInput() {
        this.add(panel);
        but0.addActionListener(e -> textField.setText(textField.getText() + "0"));
        but1.addActionListener(e -> textField.setText(textField.getText() + "1"));
        but2.addActionListener(e -> textField.setText(textField.getText() + "2"));
        but3.addActionListener(e -> textField.setText(textField.getText() + "3"));
        but4.addActionListener(e -> textField.setText(textField.getText() + "4"));
        but5.addActionListener(e -> textField.setText(textField.getText() + "5"));
        but6.addActionListener(e -> textField.setText(textField.getText() + "6"));
        but7.addActionListener(e -> textField.setText(textField.getText() + "7"));
        but8.addActionListener(e -> textField.setText(textField.getText() + "8"));
        but9.addActionListener(e -> textField.setText(textField.getText() + "9"));
        butAdd.addActionListener(e -> textField.setText(textField.getText() + "+"));
        butSub.addActionListener(e -> textField.setText(textField.getText() + "-"));
        butMul.addActionListener(e -> textField.setText(textField.getText() + "*"));
        butDiv.addActionListener(e -> textField.setText(textField.getText() + "/"));
        butPow.addActionListener(e -> textField.setText(textField.getText() + "^"));
        butPoo.addActionListener(e -> textField.setText(textField.getText() + "√"));
        left.addActionListener(e -> textField.setText(textField.getText() + "("));
        right.addActionListener(e -> textField.setText(textField.getText() + ")"));
        delButton.addActionListener(e -> {
            String string = textField.getText();
            if (!string.isEmpty()) {
                textField.setText(string.substring(0, string.length() - 1));
            }
        });
        fluButton.addActionListener(e -> textField.setText(""));
        xButton.addActionListener(e -> textField.setText(textField.getText() + "x"));
        yButton.addActionListener(e -> textField.setText(textField.getText() + "y"));
        zButton.addActionListener(e -> textField.setText(textField.getText() + "z"));
    }

    public String getValue() {
        return textField.getText();
    }

    public void setValue(String value) {
        textField.setText(value);
    }
}
