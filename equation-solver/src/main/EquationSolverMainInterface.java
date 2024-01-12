package main;

import data.MathToolkitNecessaryData;
import data.Style;
import math.equation.AbstractEquation;
import view.ErrorInterface;

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
import java.util.HashSet;

/**
 * @author wysha
 */
public class EquationSolverMainInterface extends MathGroupView {
    private JPanel contentPane;
    private JList<AbstractEquation> list;
    private AbstractEquation[] current;
    private JButton add;
    private JButton delete;
    private JButton operation;
    private JPanel left;
    private JScrollPane right;
    private JLabel jLabel;
    private JButton edit;
    private JButton out;
    private JButton in;
    private JLabel downLabel;

    public EquationSolverMainInterface(MathGroupMainInterface mathGroupMainInterface) {
        super(mathGroupMainInterface);
        delete.setEnabled(false);
        edit.setEnabled(false);
        out.setEnabled(false);
        operation.setEnabled(false);
        this.setContentPane(contentPane);
        setTitle("方程求解器");
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
                EquationSolverEdit add = new EquationSolverEdit(mathGroupMainInterface, null);
                add.setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2);
                add.setVisible(true);
            } catch (Throwable ex) {
                new ErrorInterface(
                        "方程创建失败,请检查",
                        ex,
                        true
                ).setVisible(true);
            }
            list.setListData(mathGroupMainInterface.mathGroup.equations.toArray(new AbstractEquation[0]));
        });
        delete.addActionListener(e -> {
            for (AbstractEquation abstractEquation : current) {
                mathGroupMainInterface.mathGroup.equations.remove(
                        abstractEquation
                );
            }
            list.setListData(mathGroupMainInterface.mathGroup.equations.toArray(new AbstractEquation[0]));
        });
        operation.addActionListener(e -> {
            new EquationSolverGetValue(current[0]);
            list.setListData(mathGroupMainInterface.mathGroup.equations.toArray(new AbstractEquation[0]));
        });
        edit.addActionListener(e -> {
            try {
                EquationSolverEdit add = new EquationSolverEdit(mathGroupMainInterface, current[0]);
                add.setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2);
                add.setVisible(true);
            } catch (Throwable ex) {
                new ErrorInterface(
                        "方程创建失败,请检查",
                        ex,
                        true
                ).setVisible(true);
            }
            list.setListData(mathGroupMainInterface.mathGroup.equations.toArray(new AbstractEquation[0]));
        });
        list.addListSelectionListener(e -> {
            if (list.getSelectedIndex() == -1) {
                delete.setEnabled(false);
                edit.setEnabled(false);
                out.setEnabled(false);
                operation.setEnabled(false);
            } else if (list.getSelectedIndices().length == 1) {
                current = new AbstractEquation[1];
                current[0] = list.getSelectedValue();
                delete.setEnabled(true);
                edit.setEnabled(true);
                out.setEnabled(true);
                operation.setEnabled(true);
            } else {
                current = new AbstractEquation[list.getSelectedIndices().length];
                int[] selectedIndices = list.getSelectedIndices();
                for (int j = 0; j < selectedIndices.length; j++) {
                    current[j] = mathGroupMainInterface.mathGroup.equations.get(j);
                }
                delete.setEnabled(true);
                edit.setEnabled(false);
                out.setEnabled(true);
                operation.setEnabled(false);
            }
        });
        in.addActionListener(e -> {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFont(MathToolkitNecessaryData.mathToolkitNecessaryData.setting.font);
            jFileChooser.setFileFilter(new FileNameExtensionFilter("方程文件", "Equation"));
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
                        AbstractEquation abstractEquation =
                                (AbstractEquation)
                                        new ObjectInputStream(
                                                Files.newInputStream(
                                                        file.toPath()
                                                )
                                        ).readObject();
                        for (AbstractEquation f : mathGroupMainInterface.mathGroup.equations) {
                            if (f.name.equals(abstractEquation.name)) {
                                throw new RuntimeException("列表中已有同名方程");
                            }
                        }
                        mathGroupMainInterface.mathGroup.equations.add(
                                abstractEquation
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
            list.setListData(mathGroupMainInterface.mathGroup.equations.toArray(new AbstractEquation[0]));
        });
        out.addActionListener(e -> {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFont(MathToolkitNecessaryData.mathToolkitNecessaryData.setting.font);
            jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (
                    jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION
            ) {
                try {
                    for (AbstractEquation abstractFunction : current) {
                        new ObjectOutputStream(
                                Files.newOutputStream(Paths.get(jFileChooser.getSelectedFile().getPath() + "\\" + abstractFunction.name + ".Equation"))
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
        list.setListData(mathGroupMainInterface.mathGroup.equations.toArray(new AbstractEquation[0]));
        new Thread(() -> {
            list.setListData(mathGroupMainInterface.mathGroup.equations.toArray(new AbstractEquation[0]));
            this.setStyle();
            this.repaint();
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
        buttons.add(jLabel);
        buttons.add(downLabel);
        jLists.add(list);
        Style.setStyle(jPanels, buttons, jLists);
    }
}
