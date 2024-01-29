package cn.com.wysha.math_toolkit.main;

import cn.com.wysha.math_toolkit.data.CuringConfiguration;
import cn.com.wysha.math_toolkit.data.Style;
import cn.com.wysha.math_toolkit.math.MathGroup;
import cn.com.wysha.math_toolkit.views.ErrorInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashSet;

/**
 * @author wysha
 */
public class MathGroupMainInterface extends JFrame {
    public final MathGroup mathGroup = new MathGroup();
    public final FunctionCalculatorMainInterface functionCalculatorMainInterface = new FunctionCalculatorMainInterface(this);
    public final GroupDataAnalyzerMainInterface groupDataAnalyzerMainInterface = new GroupDataAnalyzerMainInterface(this);
    public final EquationSolverMainInterface equationSolverMainInterface = new EquationSolverMainInterface(this);
    private JPanel contentPane;
    private JButton runFunctionCalculator;
    private JPanel left;
    private JButton set;
    private JButton runGroupDataAnalyzer;
    private JLabel downLabel;
    private JButton calculator;
    private JLabel v;
    private JScrollPane r;
    private JTextArea textArea;
    private JLabel show;
    private JScrollPane l;
    private JButton runEquationSolver;
    private JPanel center;
    private JPanel right;

    public MathGroupMainInterface() {
        v.setText(CuringConfiguration.VERSION);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                functionCalculatorMainInterface.dispose();
                groupDataAnalyzerMainInterface.dispose();
                equationSolverMainInterface.dispose();
            }
        });
        setContentPane(contentPane);
        runFunctionCalculator.addActionListener(e -> functionCalculatorMainInterface.setVisible(true));
        runGroupDataAnalyzer.addActionListener(e -> groupDataAnalyzerMainInterface.setVisible(true));
        runEquationSolver.addActionListener(e -> equationSolverMainInterface.setVisible(true));
        setTitle("数学工具包");
        setSize(
                (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()) * 3 / 4,
                (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()) * 3 / 4
        );
        new Thread(() -> {
            while (true) {
                this.setStyle();
                this.repaint();
            }
        }).start();
        setLocationRelativeTo(null);
        contentPane.registerKeyboardAction(
                e -> {
                    if (getExtendedState() == MAXIMIZED_BOTH) {
                        setSize(
                                (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 2 / 3),
                                (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 2 / 3)
                        );
                        setLocationRelativeTo(null);
                    } else {
                        setVisible(false);
                        setExtendedState(MAXIMIZED_BOTH);
                        setLocationRelativeTo(null);
                        setVisible(true);
                    }
                },
                KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
        );
        contentPane.registerKeyboardAction(
                e -> {
                    if (getExtendedState() == MAXIMIZED_BOTH) {
                        setSize(
                                (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()),
                                (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight())
                        );
                        setLocationRelativeTo(null);
                    } else {
                        setVisible(false);
                        setExtendedState(MAXIMIZED_BOTH);
                        setLocationRelativeTo(null);
                        setVisible(true);
                    }
                },
                KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
        );
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

    public void setStyle() {
        HashSet<JComponent> jPanels = new HashSet<>();
        HashSet<JComponent> buttons = new HashSet<>();
        HashSet<JList<?>> jLists = new HashSet<>();
        jPanels.add(right);
        buttons.add(contentPane);
        jPanels.add(center);
        jPanels.add(left);
        jPanels.add(r);
        jPanels.add(l);
        buttons.add(runFunctionCalculator);
        buttons.add(runGroupDataAnalyzer);
        buttons.add(runEquationSolver);
        buttons.add(calculator);
        buttons.add(set);
        buttons.add(downLabel);
        buttons.add(v);
        buttons.add(textArea);
        buttons.add(show);
        Style.setStyle(jPanels, buttons, jLists);
    }
}
