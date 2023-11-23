/*
 * 开发者:熊锦枫
 * 开发者邮箱:wyshazhisishen@yeah.net
 */

package FunctionCalulatorMain;

import date.NecessaryData;
import date.Style;
import math.Fraction;
import FunctionCalulatorMath.Function;
import FunctionCalulatorSet.FunctionCalculatorSet;
import tools.ErrorInterface;
import tools.GetAndSetList;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class FunctionCalculatorMainInterface extends JFrame {
    public static FunctionCalculatorMainInterface home=new FunctionCalculatorMainInterface();
    private JPanel contentPane;
    private JList<Function> list;
    private Function[] current;
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
    private JButton set;
    private JButton flush;

    public FunctionCalculatorMainInterface() {
        home=this;
        try {
            NecessaryData.necessaryData.read();
            FunctionCalculatorDate.functionCalculatorDate.read();
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
            public void windowClosing(WindowEvent event) {
                try {
                    FunctionCalculatorDate.functionCalculatorDate.write();
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
        setLocationRelativeTo(null);
        home = this;
        flush();
        add.addActionListener(e -> {
            Edit add = new Edit(null);
            add.setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2);
            add.setVisible(true);
            list.setListData(FunctionCalculatorDate.functionCalculatorDate.functions.toArray(new Function[0]));
        });
        delete.addActionListener(e -> {
            for (Function function:current){
                FunctionCalculatorDate.functionCalculatorDate.functions.remove(
                        function
                );
            }
            list.setListData(FunctionCalculatorDate.functionCalculatorDate.functions.toArray(new Function[0]));
        });
        operation.addActionListener(e -> {
            new GetValue(current[0]);
            list.setListData(FunctionCalculatorDate.functionCalculatorDate.functions.toArray(new Function[0]));
        });
        edit.addActionListener(e -> {
            Edit add = new Edit(current[0]);
            add.setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2);
            add.setVisible(true);
            list.setListData(FunctionCalculatorDate.functionCalculatorDate.functions.toArray(new Function[0]));
        });
        getValueList.addActionListener(e -> {
            class ValueAndFractions {
                final Fraction value;
                final Fraction[] fractions;

                ValueAndFractions(Fraction value, Fraction[] fraction) {
                    this.value = value;
                    this.fractions = fraction;
                }

                @Override
                public String toString() {
                    StringBuilder stringBuilder=new StringBuilder();
                    if (fractions.length!=0){
                        stringBuilder.append("带入的值:").append(Arrays.toString(fractions));
                    }
                    return stringBuilder + "得到的值" + value.toString();
                }
            }
            ArrayList<Object> arrayList = new ArrayList<>();
            for (int i = 0; i < current[0].valueLL.size(); i++) {
                arrayList.add(new ValueAndFractions(current[0].valueLL.get(i), current[0].TheValuesBroughtIn.get(i)));
            }
            GetAndSetList getAndSetList = new GetAndSetList("选择要删除的值", arrayList,new ArrayList<>());
            getAndSetList.setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2);
            getAndSetList.setVisible(true);
            java.util.List<Object> date = getAndSetList.getChoose();
            for (int i = date.size() - 1; i >= 0; i--) {
                ValueAndFractions valueAndFractions = (ValueAndFractions) date.get(i);
                current[0].valueLL.remove(valueAndFractions.value);
                current[0].TheValuesBroughtIn.remove(valueAndFractions.fractions);
            }
            list.setListData(FunctionCalculatorDate.functionCalculatorDate.functions.toArray(new Function[0]));
        });
        list.addListSelectionListener(e -> {
            if (list.getSelectedIndex() == -1) {
                delete.setEnabled(false);
                edit.setEnabled(false);
                out.setEnabled(false);
                operation.setEnabled(false);
                getValueList.setEnabled(false);
            } else if (list.getSelectedIndices().length==1){
                current=new Function[1];
                current[0] = list.getSelectedValue();
                delete.setEnabled(true);
                edit.setEnabled(true);
                out.setEnabled(true);
                operation.setEnabled(true);
                if (current[0].valueLL.size() > 1) {
                    getValueList.setEnabled(true);
                }
            }else {
                current=new Function[list.getSelectedIndices().length];
                int[] selectedIndices = list.getSelectedIndices();
                for (int j = 0; j < selectedIndices.length; j++) {
                    current[j] = FunctionCalculatorDate.functionCalculatorDate.functions.get(j);
                }
                delete.setEnabled(true);
                edit.setEnabled(false);
                out.setEnabled(true);
                operation.setEnabled(false);
                getValueList.setEnabled(false);
            }
        });
        in.addActionListener(e -> {
            JFileChooser jFileChooser=new JFileChooser();
            jFileChooser.setFont(NecessaryData.necessaryData.setting.font);
            jFileChooser.setFileFilter(new FileNameExtensionFilter("函数文件" , "Function"));
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
                        Function function=
                        (Function)
                                new ObjectInputStream(
                                        Files.newInputStream(
                                                file.toPath()
                                        )
                                ).readObject();
                        for (Function f: FunctionCalculatorDate.functionCalculatorDate.functions){
                            if (f.name.equals(function.name)){
                                throw new RuntimeException("列表中已有同名函数");
                            }
                        }
                        FunctionCalculatorDate.functionCalculatorDate.functions.add(
                                function
                        );
                    }catch (Exception exception){
                        new ErrorInterface(
                                file+"读取失败",
                                exception,
                                false
                        ).setVisible(true);
                    }
                }
                list.setListData(FunctionCalculatorDate.functionCalculatorDate.functions.toArray(new Function[0]));
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
                    for (Function function:current){
                        new ObjectOutputStream(
                                Files.newOutputStream(Paths.get(jFileChooser.getSelectedFile().getPath() + "\\" + function.name + ".Function"))
                        ).writeObject(function);
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
        set.addActionListener(e -> {
            FunctionCalculatorSet functionCalculatorSet = new FunctionCalculatorSet();
            functionCalculatorSet.setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2);
            functionCalculatorSet.setLocationRelativeTo(null);
            functionCalculatorSet.setVisible(true);
            flush();
        });
        flush.addActionListener(e -> flush());
    }

    public void flush(){
        list.setListData(FunctionCalculatorDate.functionCalculatorDate.functions.toArray(new Function[0]));
        setStyle();
    }
    public static void main(String[] args) {
        home.setVisible(true);
    }

    public void setStyle() {
        HashSet<JComponent> jPanels = new HashSet<>();
        HashSet<JComponent> buttons = new HashSet<>();
        HashSet<JList<?>> jLists=new HashSet<>();
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
        buttons.add(set);
        buttons.add(flush);
        jLists.add(list);
        Style.setStyle(jPanels,buttons,jLists);
    }
}
