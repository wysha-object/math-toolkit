/*
 * 开发者:熊锦枫
 * 开发者邮箱:wyshazhisishen@yeah.net
 */

package FunctionCalulatorMain;

import FunctionCalulatorMain.edit.EditMultivariateFunction;
import FunctionCalulatorMain.edit.EditOneVariable_OneDegree_Function;
import FunctionCalulatorMain.edit.Edits;
import date.Style;
import FunctionCalulatorMath.Function;
import FunctionCalulatorMath.MultivariateFunction;
import FunctionCalulatorMath.OneVariable_OneDegree_Function;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Objects;

public class Edit extends JDialog {
    final CardLayout cardLayout=new CardLayout();
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel down;
    private Edits current;
    final EditMultivariateFunction editMultivariateFunction=new EditMultivariateFunction(this);
    final EditOneVariable_OneDegree_Function editOneVariableOneDegreeFunction=new EditOneVariable_OneDegree_Function(this);
    private JPanel up;
    enum Functions {
        MultivariateFunction("多元函数/非标准式"),OneVariable_OneDegree_Function("一元一次函数");
        final String name;
        Functions(String name){
            this.name=name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
    private JComboBox<Functions> comboBox;
    public Edit(Function function) {
        up.setLayout(cardLayout);
        if (function!=null){
            comboBox.setVisible(false);
            if (function instanceof MultivariateFunction){
                MultivariateFunction multivariateFunction=(MultivariateFunction) function;
                editMultivariateFunction.setFunction(multivariateFunction);
                up.add(editMultivariateFunction.contentPane,editMultivariateFunction.name);
                setCurrent(editMultivariateFunction);
            }else if (function instanceof OneVariable_OneDegree_Function){
                OneVariable_OneDegree_Function oneVariableOneDegreeFunction=(OneVariable_OneDegree_Function) function;
                editOneVariableOneDegreeFunction.setFunction(oneVariableOneDegreeFunction);
                up.add(editOneVariableOneDegreeFunction.contentPane,editOneVariableOneDegreeFunction.name);
                setCurrent(editOneVariableOneDegreeFunction);
            }
        }else {
            up.add(editMultivariateFunction.contentPane,editMultivariateFunction.name);
            up.add(editOneVariableOneDegreeFunction.contentPane,editOneVariableOneDegreeFunction.name);
            Functions[] functions = new Functions[]{
                    Functions.MultivariateFunction,
                    Functions.OneVariable_OneDegree_Function
            };
            comboBox.setModel(new DefaultComboBoxModel<>(functions));
            comboBox.addActionListener(e ->{
                Functions requireNonNull = (Functions) Objects.requireNonNull(comboBox.getSelectedItem());
                if (requireNonNull == Functions.MultivariateFunction) {
                    setCurrent(editMultivariateFunction);
                } else if (requireNonNull == Functions.OneVariable_OneDegree_Function) {
                    setCurrent(editOneVariableOneDegreeFunction);
                }
            });
            comboBox.setSelectedItem(functions[0]);
        }
        setContentPane(contentPane);
        setModal(true);
        setTitle("创建函数");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        contentPane.registerKeyboardAction(e->current.onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        setStyle();
        buttonOK.addActionListener(e->current.onOK());
        buttonCancel.addActionListener(e->current.onCancel());
    }

    private void setCurrent(Edits edit){
        if (current != edit){
            cardLayout.show(up,edit.name);
            current=edit;
        }
    }

    public void setStyle() {
        HashSet<JComponent> jPanels = new HashSet<>();
        HashSet<JComponent> buttons = new HashSet<>();
        jPanels.add(contentPane);
        jPanels.add(down);
        jPanels.add(up);
        buttons.add(buttonOK);
        buttons.add(buttonCancel);
        buttons.add(comboBox);
        Style.setStyle(jPanels,buttons,null);
    }
}
