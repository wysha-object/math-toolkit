package main;

import data.MathToolkitNecessaryData;
import data.Style;
import math.function.AbstractFunction;
import math.math.object.Formula;
import view.ErrorInterface;
import view.GetAndSetList;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * @author wysha
 */
public class FunctionCalculatorMainInterface extends MathGroupView {
    private JPanel contentPane;
    private JList<AbstractFunction> list;
    private AbstractFunction[] current;
    private JButton add;
    private JButton delete;
    private JButton operation;
    private JPanel left;
    private JScrollPane right;
    private JLabel jLabel;
    private JButton getValueList;
    private JButton edit;
    private JButton out;
    private JButton in;
    private JLabel downLabel;

    public FunctionCalculatorMainInterface(MathGroupMainInterface mathGroupMainInterface) {
        super(mathGroupMainInterface);
        delete.setEnabled(false);
        edit.setEnabled(false);
        out.setEnabled(false);
        operation.setEnabled(false);
        getValueList.setEnabled(false);
        this.setContentPane(contentPane);
        setTitle("函数计算器");
        setSize(
                (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2,
                (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2
        );
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                setVisible(false);
            }
        });
        setLocationRelativeTo(null);
        add.addActionListener(e -> {
            try {
                FunctionCalculatorEdit add = new FunctionCalculatorEdit(mathGroupMainInterface, null);
                add.setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2);
                add.setVisible(true);
            } catch (Throwable ex) {
                new ErrorInterface(
                        "函数创建失败,请检查",
                        ex,
                        true
                ).setVisible(true);
            }
            list.setListData(mathGroupMainInterface.mathGroup.functions.toArray(new AbstractFunction[0]));
        });
        delete.addActionListener(e -> {
            for (AbstractFunction abstractFunction : current) {
                mathGroupMainInterface.mathGroup.functions.remove(
                        abstractFunction
                );
            }
            list.setListData(mathGroupMainInterface.mathGroup.functions.toArray(new AbstractFunction[0]));
        });
        operation.addActionListener(e -> {
            new FunctionCalculatorGetValue(current[0]);
            list.setListData(mathGroupMainInterface.mathGroup.functions.toArray(new AbstractFunction[0]));
        });
        edit.addActionListener(e -> {
            try {
                FunctionCalculatorEdit add = new FunctionCalculatorEdit(mathGroupMainInterface, current[0]);
                add.setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2);
                add.setVisible(true);
            } catch (Throwable ex) {
                new ErrorInterface(
                        "函数创建失败,请检查",
                        ex,
                        true
                ).setVisible(true);
            }
            list.setListData(mathGroupMainInterface.mathGroup.functions.toArray(new AbstractFunction[0]));
        });
        getValueList.addActionListener(e -> {
            ArrayList<Object> arrayList = new ArrayList<>();
            for (int i = 0; i < current[0].valueLL.size(); i++) {
                arrayList.add(new ValueAndFractions(current[0].valueLL.get(i), current[0].TheValuesBroughtIn.get(i)));
            }
            GetAndSetList getAndSetList = new GetAndSetList("选择要删除的值", arrayList, new ArrayList<>());
            getAndSetList.setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2);
            getAndSetList.setVisible(true);
            java.util.List<Object> date = getAndSetList.getChoose();
            for (int i = date.size() - 1; i >= 0; i--) {
                ValueAndFractions valueAndFractions = (ValueAndFractions) date.get(i);
                current[0].valueLL.remove(valueAndFractions.value);
                current[0].TheValuesBroughtIn.remove(valueAndFractions.fractions);
            }
            list.setListData(mathGroupMainInterface.mathGroup.functions.toArray(new AbstractFunction[0]));
        });
        list.addListSelectionListener(e -> {
            if (list.getSelectedIndex() == -1) {
                delete.setEnabled(false);
                edit.setEnabled(false);
                out.setEnabled(false);
                operation.setEnabled(false);
                getValueList.setEnabled(false);
            } else if (list.getSelectedIndices().length == 1) {
                current = new AbstractFunction[1];
                current[0] = list.getSelectedValue();
                delete.setEnabled(true);
                edit.setEnabled(true);
                out.setEnabled(true);
                operation.setEnabled(true);
                if (current[0].valueLL.size() > 1) {
                    getValueList.setEnabled(true);
                }
            } else {
                current = new AbstractFunction[list.getSelectedIndices().length];
                int[] selectedIndices = list.getSelectedIndices();
                for (int j = 0; j < selectedIndices.length; j++) {
                    current[j] = mathGroupMainInterface.mathGroup.functions.get(selectedIndices[j]);
                }
                delete.setEnabled(true);
                edit.setEnabled(false);
                out.setEnabled(true);
                operation.setEnabled(false);
                getValueList.setEnabled(false);
            }
        });
        in.addActionListener(e -> {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFont(MathToolkitNecessaryData.mathToolkitNecessaryData.setting.font);
            jFileChooser.setFileFilter(new FileNameExtensionFilter("函数文件", "Function"));
            jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            jFileChooser.setMultiSelectionEnabled(true);
            if (
                    jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION
            ) {
                File[] selectedFiles = jFileChooser.getSelectedFiles();
                if (selectedFiles.length == 0) {
                    selectedFiles = new File[]{jFileChooser.getSelectedFile()};
                }
                for (File file : selectedFiles) {
                    try {
                        AbstractFunction abstractFunction =
                                (AbstractFunction)
                                        new ObjectInputStream(
                                                Files.newInputStream(
                                                        file.toPath()
                                                )
                                        ).readObject();
                        for (AbstractFunction f : mathGroupMainInterface.mathGroup.functions) {
                            if (f.name.equals(abstractFunction.name)) {
                                throw new RuntimeException("列表中已有同名函数");
                            }
                        }
                        mathGroupMainInterface.mathGroup.functions.add(
                                abstractFunction
                        );
                    } catch (Exception exception) {
                        new ErrorInterface(
                                file + "读取失败",
                                exception,
                                false
                        ).setVisible(true);
                    }
                }
            }
            list.setListData(mathGroupMainInterface.mathGroup.functions.toArray(new AbstractFunction[0]));
        });
        out.addActionListener(e -> {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFont(MathToolkitNecessaryData.mathToolkitNecessaryData.setting.font);
            jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (
                    jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION
            ) {
                try {
                    for (AbstractFunction abstractFunction : current) {
                        new ObjectOutputStream(
                                Files.newOutputStream(Paths.get(jFileChooser.getSelectedFile().getPath() + "\\" + abstractFunction.name + ".Function"))
                        ).writeObject(abstractFunction);
                    }
                    ProcessBuilder processBuilder = new ProcessBuilder("explorer", jFileChooser.getSelectedFile().getPath());
                    processBuilder.start();
                } catch (Exception exception) {
                    new ErrorInterface(
                            "写入失败",
                            exception,
                            false
                    ).setVisible(true);
                }
            }
        });
        list.setListData(mathGroupMainInterface.mathGroup.functions.toArray(new AbstractFunction[0]));
        new Thread(() -> {
            while (true) {
                this.setStyle();
                this.repaint();
            }
        }).start();
    }

    public void setStyle() {
        HashSet<JComponent> jPanels = new HashSet<>();
        HashSet<JComponent> buttons = new HashSet<>();
        HashSet<JList<?>> jLists = new HashSet<>();
        jPanels.add(contentPane);
        jPanels.add(left);
        jPanels.add(right);
        buttons.add(add);
        buttons.add(delete);
        buttons.add(edit);
        buttons.add(in);
        buttons.add(out);
        buttons.add(operation);
        buttons.add(getValueList);
        buttons.add(jLabel);
        buttons.add(downLabel);
        jLists.add(list);
        Style.setStyle(jPanels, buttons, jLists);
    }
}

class ValueAndFractions {
    final Formula value;
    final Formula[] fractions;

    ValueAndFractions(Formula value, Formula[] fraction) {
        this.value = value;
        this.fractions = fraction;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (fractions.length != 0) {
            stringBuilder.append("带入的值:").append(Arrays.toString(fractions));
        }
        return stringBuilder + "得到的值" + value.toString();
    }
}