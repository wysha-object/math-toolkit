
package data;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.HashSet;

/**
 * @author wysha
 */
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
                j.setBackground(MathToolkitNecessaryData.mathToolkitNecessaryData.setting.jPanelBackground);
                j.setForeground(MathToolkitNecessaryData.mathToolkitNecessaryData.setting.foreground);
                j.setFont(MathToolkitNecessaryData.mathToolkitNecessaryData.setting.font);
            }
        }
        if (buttons != null) {
            for (JComponent b : buttons) {
                b.setBackground(MathToolkitNecessaryData.mathToolkitNecessaryData.setting.background);
                b.setForeground(MathToolkitNecessaryData.mathToolkitNecessaryData.setting.foreground);
                b.setFont(MathToolkitNecessaryData.mathToolkitNecessaryData.setting.font);
            }
        }
        if (jLists != null) {
            for (JList<?> jList : jLists) {
                jList.setBackground(MathToolkitNecessaryData.mathToolkitNecessaryData.setting.background);
                jList.setForeground(MathToolkitNecessaryData.mathToolkitNecessaryData.setting.foreground);
                jList.setFont(MathToolkitNecessaryData.mathToolkitNecessaryData.setting.font);
                jList.setSelectionBackground(MathToolkitNecessaryData.mathToolkitNecessaryData.setting.foreground);
                jList.setSelectionForeground(MathToolkitNecessaryData.mathToolkitNecessaryData.setting.jPanelBackground);
            }
        }
    }
}
