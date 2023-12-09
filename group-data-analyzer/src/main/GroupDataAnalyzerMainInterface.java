
package main;

import data.MathToolkitNecessaryData;
import data.Style;
import math.groupdata.GroupData;
import set.GroupDataAnalyzerSet;
import tools.ErrorInterface;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.KeyEvent;
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
public class GroupDataAnalyzerMainInterface extends JFrame{
    GroupData[] current;
    private JPanel contentPane;
    private JList<GroupData> list;
    private JButton add;
    private JLabel downLabel;
    private JPanel left;
    private JScrollPane right;
    private JButton set;
    private JButton delete;
    private JButton edit;
    private JButton flush;
    private JButton in;
    private JButton out;
    private JButton get;
    private JLabel jLabel;

    public static void main(String[] args) {
        home.setVisible(true);
    }
    public GroupDataAnalyzerMainInterface(){
        home=this;
        try {
            MathToolkitNecessaryData.mathToolkitNecessaryData.read();
            GroupDataAnalyzerData.groupDataAnalyzerDate.read();
        } catch (Throwable e) {
            new ErrorInterface(
                    "读取失败",
                    e,
                    false
            ).setVisible(true);
        }
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
        flush();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                try {
                    GroupDataAnalyzerData.groupDataAnalyzerDate.write();
                } catch (Throwable e) {
                    new ErrorInterface(
                            "写入失败",
                            e,
                            false
                    ).setVisible(true);
                }
                dispose();
            }
        });
        home=this;
        flush.addActionListener(e -> flush());
        add.addActionListener(e -> {
            try {
                GroupDataEdit add = new GroupDataEdit(null);
                add.setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2);
                add.setVisible(true);
            } catch (Throwable ex) {
                new ErrorInterface(
                        "数据组创建失败,请检查",
                        ex,
                        true
                ).setVisible(true);
            }
            list.setListData(GroupDataAnalyzerData.groupDataAnalyzerDate.groupData.toArray(new GroupData[0]));
        });
        delete.addActionListener(e -> {
            for (GroupData groupData : current) {
                GroupDataAnalyzerData.groupDataAnalyzerDate.groupData.remove(
                        groupData
                );
            }
            list.setListData(GroupDataAnalyzerData.groupDataAnalyzerDate.groupData.toArray(new GroupData[0]));
        });
        edit.addActionListener(e -> {
            try {
                GroupDataEdit add = new GroupDataEdit(current[0]);
                add.setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2);
                add.setVisible(true);
            } catch (Throwable ex) {
                new ErrorInterface(
                        "数据组创建失败,请检查",
                        ex,
                        true
                ).setVisible(true);
            }
            list.setListData(GroupDataAnalyzerData.groupDataAnalyzerDate.groupData.toArray(new GroupData[0]));
        });
        set.addActionListener(e -> {
            GroupDataAnalyzerSet groupDataAnalyzerSet=new GroupDataAnalyzerSet();
            groupDataAnalyzerSet.setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2);
            groupDataAnalyzerSet.setLocationRelativeTo(null);
            groupDataAnalyzerSet.setVisible(true);
            flush();
        });
        get.addActionListener(e -> {
            try {
                GroupDataAnalyzerGet groupDataAnalyzerGet = new GroupDataAnalyzerGet(current[0]);
                groupDataAnalyzerGet.setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2);
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
            } else if (list.getSelectedIndices().length==1){
                current = new GroupData[1];
                current[0] = list.getSelectedValue();
                delete.setEnabled(true);
                edit.setEnabled(true);
                out.setEnabled(true);
                get.setEnabled(true);
            }else {
                current = new GroupData[list.getSelectedIndices().length];
                int[] selectedIndices = list.getSelectedIndices();
                for (int j = 0; j < selectedIndices.length; j++) {
                    current[j] = GroupDataAnalyzerData.groupDataAnalyzerDate.groupData.get(selectedIndices[j]);
                }
                delete.setEnabled(true);
                edit.setEnabled(false);
                out.setEnabled(true);
                get.setEnabled(false);
            }
        });
        in.addActionListener(e -> {
            JFileChooser jFileChooser=new JFileChooser();
            jFileChooser.setFont(MathToolkitNecessaryData.mathToolkitNecessaryData.setting.font);
            jFileChooser.setFileFilter(new FileNameExtensionFilter("数据组文件", "GroupData"));
            jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            jFileChooser.setMultiSelectionEnabled(true);
            if (
                    jFileChooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION
            ){
                File[] selectedFiles=jFileChooser.getSelectedFiles();
                if (selectedFiles.length==0){
                    selectedFiles=new File[]{jFileChooser.getSelectedFile()};
                }
                for (File file:selectedFiles){
                    try {
                        GroupData groupData =
                                (GroupData)
                                        new ObjectInputStream(
                                                Files.newInputStream(
                                                        file.toPath()
                                                )
                                        ).readObject();
                        for (GroupData f : GroupDataAnalyzerData.groupDataAnalyzerDate.groupData) {
                            if (f.name.equals(groupData.name)) {
                                throw new RuntimeException("数据组列表中已有同名数据组");
                            }
                        }
                        GroupDataAnalyzerData.groupDataAnalyzerDate.groupData.add(
                                groupData
                        );
                    }catch (Exception exception){
                        new ErrorInterface(
                                file+"读取失败",
                                exception,
                                false
                        ).setVisible(true);
                    }
                }
                list.setListData(GroupDataAnalyzerData.groupDataAnalyzerDate.groupData.toArray(new GroupData[0]));
            }
        });
        out.addActionListener(e -> {
            JFileChooser jFileChooser=new JFileChooser();
            jFileChooser.setFont(MathToolkitNecessaryData.mathToolkitNecessaryData.setting.font);
            jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (
                    jFileChooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION
            ){
                try {
                    for (GroupData groupData : current) {
                        new ObjectOutputStream(
                                Files.newOutputStream(Paths.get(jFileChooser.getSelectedFile().getPath() + "\\" + groupData.name + ".GroupData"))
                        ).writeObject(groupData);
                    }
                    ProcessBuilder processBuilder = new ProcessBuilder("explorer", jFileChooser.getSelectedFile().getPath());
                    processBuilder.start();
                }catch (Exception exception){
                    new ErrorInterface(
                            "写入失败",
                            exception,
                            false
                    ).setVisible(true);
                }
            }
        });
        contentPane.registerKeyboardAction(
                e -> {
                    if (getExtendedState()==MAXIMIZED_BOTH){
                        setSize(
                                (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2,
                                (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2
                        );
                        setLocationRelativeTo(null);
                    }else {
                        setVisible(false);
                        setExtendedState(MAXIMIZED_BOTH);
                        setLocationRelativeTo(null);
                        setVisible(true);
                    }
                },
                KeyStroke.getKeyStroke(KeyEvent.VK_F11,0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
        );
    }
    public static GroupDataAnalyzerMainInterface home=new GroupDataAnalyzerMainInterface();
    public void flush(){
        setStyle();
        list.setListData(GroupDataAnalyzerData.groupDataAnalyzerDate.groupData.toArray(new GroupData[0]));
    }
    public void setStyle() {
        HashSet<JComponent> jPanels = new HashSet<>();
        HashSet<JComponent> buttons = new HashSet<>();
        HashSet<JList<?>> jLists=new HashSet<>();
        jPanels.add(contentPane);
        jPanels.add(left);
        jPanels.add(right);
        buttons.add(flush);
        buttons.add(add);
        buttons.add(delete);
        buttons.add(edit);
        buttons.add(in);
        buttons.add(out);
        buttons.add(get);
        buttons.add(set);
        buttons.add(downLabel);
        buttons.add(jLabel);
        jLists.add(list);
        Style.setStyle(jPanels,buttons,jLists);
    }
}
