/*
 * 开发者:熊锦枫
 * 开发者邮箱:wyshazhisishen@yeah.net
 */

package data;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.HashSet;

public final class Style implements Serializable {
    private final String name;
    final Color foreground;
    final Color background;
    final Color jPanelBackground;
    final Font font;

    public Style(String name, Color foreground, Color background, Color jPanelBackground, Font font) {
        this.name = name;
        this.foreground = foreground;
        this.background = background;
        this.jPanelBackground = jPanelBackground;
        this.font = font;
    }

    @Override
    public String toString() {
        return name;
    }

    public static void setStyle(HashSet<JComponent> jPanels, HashSet<JComponent> buttons, HashSet<JList<?>> jLists) {
        if (jPanels != null) {
            for (JComponent j : jPanels) {
                j.setBackground(NecessaryData.necessaryData.setting.jPanelBackground);
                j.setForeground(NecessaryData.necessaryData.setting.foreground);
                j.setFont(NecessaryData.necessaryData.setting.font);
            }
        }
        if (buttons != null) {
            for (JComponent b : buttons) {
                b.setBackground(NecessaryData.necessaryData.setting.background);
                b.setForeground(NecessaryData.necessaryData.setting.foreground);
                b.setFont(NecessaryData.necessaryData.setting.font);
            }
        }
        if (jLists != null) {
            for (JList<?> jList : jLists) {
                jList.setBackground(NecessaryData.necessaryData.setting.background);
                jList.setForeground(NecessaryData.necessaryData.setting.foreground);
                jList.setFont(NecessaryData.necessaryData.setting.font);
                jList.setSelectionBackground(NecessaryData.necessaryData.setting.foreground);
                jList.setSelectionForeground(NecessaryData.necessaryData.setting.jPanelBackground);
            }
        }
    }
}
