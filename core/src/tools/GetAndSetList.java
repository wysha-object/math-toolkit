/*
 * 开发者:熊锦枫
 * 开发者邮箱:wyshazhisishen@yeah.net
 */
package tools;
import date.Style;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;

public class GetAndSetList extends JDialog {
    private final List<Object> date;
    private final List<Object> chooseDate;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel down;
    private JScrollPane right;
    private JList<Object> list;
    private JList<Object> chooseList;
    private JLabel leftLabel;
    private JLabel rightLabel;
    private JButton add;
    private JButton remove;
    private JLabel jLabel;
    private JPanel panel;

    public GetAndSetList(String text, List<Object> es,List<Object> choose) {
        setTitle(text);
        date=es;
        chooseDate=choose;
        list.setListData(date.toArray());
        if (choose!=null) {
            add.setEnabled(false);
            remove.setEnabled(false);
            list.addListSelectionListener(e -> add.setEnabled(list.getSelectedIndex() != -1));
            chooseList.addListSelectionListener(e -> remove.setEnabled(chooseList.getSelectedIndex() != -1));
            add.addActionListener(e -> {
                int[] ints=list.getSelectedIndices();
                if (ints.length==1){
                    Object o=list.getSelectedValue();
                    date.remove(o);
                    chooseDate.add(o);
                }else {
                    for (int j = ints.length-1; j >=0; j--) {
                        int i = ints[j];
                        chooseDate.add(date.remove(i));
                    }
                }
                list.setListData(date.toArray());
                chooseList.setListData(chooseDate.toArray());
            });
            remove.addActionListener(e -> {
                int[] ints=chooseList.getSelectedIndices();
                if (ints.length==1){
                    Object o=chooseList.getSelectedValue();
                    date.add(o);
                    chooseDate.remove(o);
                }else {
                    for (int j = ints.length-1; j >=0; j--) {
                        int i = ints[j];
                        date.add(chooseDate.remove(i));
                    }
                }
                list.setListData(date.toArray());
                chooseList.setListData(chooseDate.toArray());
            });
            chooseList.setListData(chooseDate.toArray());
        }else {
            leftLabel.setVisible(false);
            rightLabel.setVisible(false);
            right.setVisible(false);
            panel.setVisible(false);
        }
        setContentPane(contentPane);
        setModal(true);
        jLabel.setText(text);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setStyle();
        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        contentPane.registerKeyboardAction(e -> onOK(), KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }
    public void setStyle() {
        HashSet<JComponent> jPanels = new HashSet<>();
        HashSet<JComponent> buttons = new HashSet<>();
        HashSet<JList<?>> jLists = new HashSet<>();
        jPanels.add(contentPane);
        jPanels.add(down);
        jPanels.add(right);
        jPanels.add(panel);
        buttons.add(buttonOK);
        buttons.add(buttonCancel);
        buttons.add(leftLabel);
        buttons.add(rightLabel);
        buttons.add(add);
        buttons.add(remove);
        buttons.add(jLabel);
        jLists.add(list);
        jLists.add(chooseList);
        Style.setStyle(jPanels,buttons,jLists);
    }
    public List<Object> getChoose(){
        return chooseDate;
    }
    private void onOK() {
        dispose();
    }
    private void onCancel() {
        date.addAll(chooseDate);
        if (!chooseDate.isEmpty()) {
            chooseDate.subList(0, chooseDate.size()).clear();
        }
        dispose();
    }
}