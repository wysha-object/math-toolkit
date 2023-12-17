package main;

import data.MathToolkitNecessaryData;
import data.Style;
import math.groupdata.GroupData;
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
public class GroupDataAnalyzerMainInterface extends MathGroupView {
    GroupData[] current;
    private JPanel contentPane;
    private JList<GroupData> list;
    private JButton add;
    private JLabel downLabel;
    private JPanel left;
    private JScrollPane right;
    private JButton delete;
    private JButton edit;
    private JButton in;
    private JButton out;
    private JButton get;
    private JLabel jLabel;

    public GroupDataAnalyzerMainInterface(MathGroupMainInterface mathGroupMainInterface) {
        super(mathGroupMainInterface);
        delete.setEnabled(false);
        edit.setEnabled(false);
        out.setEnabled(false);
        get.setEnabled(false);
        setTitle("数据分析器");
        setContentPane(contentPane);
        setSize(
                (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2,
                (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2
        );
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                setVisible(false);
            }
        });
        add.addActionListener(e -> {
            try {
                GroupDataEdit add = new GroupDataEdit(mathGroupMainInterface, null);
                add.setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2);
                add.setVisible(true);
            } catch (Throwable ex) {
                new ErrorInterface(
                        "数据组创建失败,请检查",
                        ex,
                        true
                ).setVisible(true);
            }
            list.setListData(mathGroupMainInterface.mathGroup.groupData.toArray(new GroupData[0]));
        });
        delete.addActionListener(e -> {
            for (GroupData groupData : current) {
                mathGroupMainInterface.mathGroup.groupData.remove(
                        groupData
                );
            }
            list.setListData(mathGroupMainInterface.mathGroup.groupData.toArray(new GroupData[0]));
        });
        edit.addActionListener(e -> {
            try {
                GroupDataEdit add = new GroupDataEdit(mathGroupMainInterface, current[0]);
                add.setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2);
                add.setVisible(true);
            } catch (Throwable ex) {
                new ErrorInterface(
                        "数据组创建失败,请检查",
                        ex,
                        true
                ).setVisible(true);
            }
            list.setListData(mathGroupMainInterface.mathGroup.groupData.toArray(new GroupData[0]));
        });
        get.addActionListener(e -> {
            try {
                GroupDataAnalyzerGet groupDataAnalyzerGet = new GroupDataAnalyzerGet(current[0]);
                groupDataAnalyzerGet.setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2);
                groupDataAnalyzerGet.setLocationRelativeTo(null);
                groupDataAnalyzerGet.setVisible(true);
            } catch (Throwable ex) {
                new ErrorInterface(
                        "数据组异常",
                        ex,
                        false
                ).setVisible(true);
                throw new RuntimeException(ex);
            }
        });
        list.addListSelectionListener(e -> {
            if (list.getSelectedIndex() == -1) {
                delete.setEnabled(false);
                edit.setEnabled(false);
                out.setEnabled(false);
                get.setEnabled(false);
            } else if (list.getSelectedIndices().length == 1) {
                current = new GroupData[1];
                current[0] = list.getSelectedValue();
                delete.setEnabled(true);
                edit.setEnabled(true);
                out.setEnabled(true);
                get.setEnabled(true);
            } else {
                current = new GroupData[list.getSelectedIndices().length];
                int[] selectedIndices = list.getSelectedIndices();
                for (int j = 0; j < selectedIndices.length; j++) {
                    current[j] = mathGroupMainInterface.mathGroup.groupData.get(selectedIndices[j]);
                }
                delete.setEnabled(true);
                edit.setEnabled(false);
                out.setEnabled(true);
                get.setEnabled(false);
            }
        });
        in.addActionListener(e -> {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFont(MathToolkitNecessaryData.mathToolkitNecessaryData.setting.font);
            jFileChooser.setFileFilter(new FileNameExtensionFilter("数据组文件", "GroupData"));
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
                        GroupData groupData =
                                (GroupData)
                                        new ObjectInputStream(
                                                Files.newInputStream(
                                                        file.toPath()
                                                )
                                        ).readObject();
                        mathGroupMainInterface.mathGroup.checkName(groupData);
                        mathGroupMainInterface.mathGroup.groupData.add(
                                groupData
                        );
                    } catch (Throwable exception) {
                        new ErrorInterface(
                                file + "读取失败",
                                exception,
                                false
                        ).setVisible(true);
                    }
                }
            }
            list.setListData(mathGroupMainInterface.mathGroup.groupData.toArray(new GroupData[0]));
        });
        out.addActionListener(e -> {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFont(MathToolkitNecessaryData.mathToolkitNecessaryData.setting.font);
            jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (
                    jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION
            ) {
                try {
                    for (GroupData groupData : current) {
                        new ObjectOutputStream(
                                Files.newOutputStream(Paths.get(jFileChooser.getSelectedFile().getPath() + "\\" + groupData.name + ".GroupData"))
                        ).writeObject(groupData);
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
        list.setListData(mathGroupMainInterface.mathGroup.groupData.toArray(new GroupData[0]));
        new Thread(() -> {
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
        buttons.add(get);
        buttons.add(downLabel);
        buttons.add(jLabel);
        jLists.add(list);
        Style.setStyle(jPanels, buttons, jLists);
    }
}
