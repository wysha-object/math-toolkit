/*
 * 开发者:熊锦枫
 * 开发者邮箱:wyshazhisishen@yeah.net
 */
package GroupDataAnalyzerMain;

import GroupDataAnalyzerSet.GroupDataAnalyzerSet;
import date.NecessaryData;
import date.Style;
import GroupDataAnalyzerMath.GroupDate;
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

public class GroupDataAnalyzerMainInterface extends JFrame{
    GroupDate[] current;
    private JPanel contentPane;
    private JList<GroupDate> list;
    private JButton add;
    private JLabel downJLabel;
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
            NecessaryData.necessaryData.read();
            GroupDataAnalyzerDate.groupDataAnalyzerDate.read();
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
            public void windowClosing(WindowEvent event) {
                try {
                    GroupDataAnalyzerDate.groupDataAnalyzerDate.write();
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
                Edit add = new Edit(null);
                add.setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2);
                add.setVisible(true);
            } catch (Throwable ex) {
                new ErrorInterface(
                        "数据组创建失败,请检查",
                        ex,
                        false
                ).setVisible(true);
            }
            list.setListData(GroupDataAnalyzerDate.groupDataAnalyzerDate.groupDates.toArray(new GroupDate[0]));
        });
        delete.addActionListener(e -> {
            for (GroupDate groupDate:current){
                GroupDataAnalyzerDate.groupDataAnalyzerDate.groupDates.remove(
                        groupDate
                );
            }
            list.setListData(GroupDataAnalyzerDate.groupDataAnalyzerDate.groupDates.toArray(new GroupDate[0]));
        });
        edit.addActionListener(e -> {
            try {
                Edit add = new Edit(current[0]);
                add.setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2);
                add.setVisible(true);
            } catch (Throwable ex) {
                new ErrorInterface(
                        "数据组创建失败,请检查",
                        ex,
                        false
                ).setVisible(true);
            }
            list.setListData(GroupDataAnalyzerDate.groupDataAnalyzerDate.groupDates.toArray(new GroupDate[0]));
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
                Get get = new Get(current[0]);
                get.setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2);
                get.setLocationRelativeTo(null);
                get.setVisible(true);
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
                current=new GroupDate[1];
                current[0] = list.getSelectedValue();
                delete.setEnabled(true);
                edit.setEnabled(true);
                out.setEnabled(true);
                get.setEnabled(true);
            }else {
                current=new GroupDate[list.getSelectedIndices().length];
                int[] selectedIndices = list.getSelectedIndices();
                for (int j = 0; j < selectedIndices.length; j++) {
                    current[j] = GroupDataAnalyzerDate.groupDataAnalyzerDate.groupDates.get(j);
                }
                delete.setEnabled(true);
                edit.setEnabled(false);
                out.setEnabled(true);
                get.setEnabled(false);
            }
        });
        in.addActionListener(e -> {
            JFileChooser jFileChooser=new JFileChooser();
            jFileChooser.setFont(NecessaryData.necessaryData.setting.font);
            jFileChooser.setFileFilter(new FileNameExtensionFilter("数据组文件" , "GroupDate"));
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
                        GroupDate groupDate=
                                (GroupDate)
                                        new ObjectInputStream(
                                                Files.newInputStream(
                                                        file.toPath()
                                                )
                                        ).readObject();
                        for (GroupDate f: GroupDataAnalyzerDate.groupDataAnalyzerDate.groupDates){
                            if (f.name.equals(groupDate.name)){
                                throw new RuntimeException("数据组列表中已有同名函数");
                            }
                        }
                        GroupDataAnalyzerDate.groupDataAnalyzerDate.groupDates.add(
                                groupDate
                        );
                    }catch (Exception exception){
                        new ErrorInterface(
                                file+"读取失败",
                                exception,
                                false
                        ).setVisible(true);
                    }
                }
                list.setListData(GroupDataAnalyzerDate.groupDataAnalyzerDate.groupDates.toArray(new GroupDate[0]));
            }
        });
        out.addActionListener(e -> {
            JFileChooser jFileChooser=new JFileChooser();
            jFileChooser.setFont(NecessaryData.necessaryData.setting.font);
            jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (
                    jFileChooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION
            ){
                try {
                    for (GroupDate groupDate:current){
                        new ObjectOutputStream(
                                Files.newOutputStream(Paths.get(jFileChooser.getSelectedFile().getPath() + "\\" + groupDate.name + ".GroupDate"))
                        ).writeObject(groupDate);
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
        list.setListData(GroupDataAnalyzerDate.groupDataAnalyzerDate.groupDates.toArray(new GroupDate[0]));
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
        buttons.add(downJLabel);
        buttons.add(jLabel);
        jLists.add(list);
        Style.setStyle(jPanels,buttons,jLists);
    }
}
