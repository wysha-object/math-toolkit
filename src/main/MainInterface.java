/*
 * 开发者:熊锦枫
 * 开发者邮箱:wyshazhisishen@yeah.net
 */

package main;

import data.NecessaryData;
import data.Style;
import set.NecessarySet;
import tools.ErrorInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashSet;

/**
 * @author wysha
 */
public class MainInterface extends JFrame{
    private JPanel contentPane;
    private JButton runFunctionCalculator;
    private JPanel left;
    private JButton set;
    private JButton runGroupDataAnalyzer;
    private JLabel downLabel;
    private JButton flush;
    private JButton calculator;
    private JLabel v;

    public static void main(String[] args) {
        home.setVisible(true);
    }
    public static MainInterface home=new MainInterface();
    public MainInterface(){
        home=this;
        try {
            NecessaryData.necessaryData.read();
        } catch (Throwable e) {
            new ErrorInterface(
                    "读取失败",
                    e,
                    false
            ).setVisible(true);
        }
        setContentPane(contentPane);
        runFunctionCalculator.addActionListener(e -> {
            try {
                FunctionCalculatorMainInterface.main(null);
            } catch (Throwable ex) {
                throw new RuntimeException(ex);
            }
        });
        runGroupDataAnalyzer.addActionListener(e -> {
            try {
                GroupDataAnalyzerMainInterface.main(null);
            } catch (Throwable ex) {
                throw new RuntimeException(ex);
            }
        });
        setTitle("数学工具包");
        setSize(
                (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()*2/3),
                (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()*2/3)
        );
        setLocationRelativeTo(null);
        flush();
        contentPane.registerKeyboardAction(
                e -> {
                    if (getExtendedState()==MAXIMIZED_BOTH){
                        setSize(
                                (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()*2/3),
                                (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()*2/3)
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
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        set.addActionListener(e -> {
            NecessarySet set = new NecessarySet();
            set.setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2);
            set.setLocationRelativeTo(null);
            set.setVisible(true);
            flush();
        });
        contentPane.registerKeyboardAction(
                e -> {
                    if (getExtendedState()==MAXIMIZED_BOTH){
                        setSize(
                                (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()*2/3),
                                (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()*2/3)
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
        flush.addActionListener(e -> flush());
        calculator.addActionListener(e -> {
            try {
                ProcessBuilder processBuilder = new ProcessBuilder("calc.exe");
                processBuilder.start();
            } catch (Exception exception) {
                ErrorInterface errorInterface = new ErrorInterface(
                        "计算器启动失败,操作系统不支持或计算器已损坏\n",
                        exception,
                        false
                );
                errorInterface.setVisible(true);
            }
        });
    }

    public void flush(){
        setStyle();
    }

    public void setStyle() {
        HashSet<JComponent> jPanels = new HashSet<>();
        HashSet<JComponent> buttons = new HashSet<>();
        HashSet<JList<?>> jLists=new HashSet<>();
        jPanels.add(contentPane);
        jPanels.add(left);
        buttons.add(runFunctionCalculator);
        buttons.add(runGroupDataAnalyzer);
        buttons.add(calculator);
        buttons.add(set);
        buttons.add(downLabel);
        buttons.add(flush);
        buttons.add(v);
        Style.setStyle(jPanels,buttons,jLists);
    }
}
