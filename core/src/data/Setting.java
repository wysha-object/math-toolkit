package data;

import java.awt.*;
import java.io.Serializable;

/**
 * @author wysha
 */
public class Setting implements Serializable {
    public Style style;
    public Color jPanelBackground;
    public Color background;
    public Color foreground;
    public Font font;

    public static void setStyle(Style style) {
        MathToolkitNecessaryData.mathToolkitNecessaryData.setting.style = style;
        MathToolkitNecessaryData.mathToolkitNecessaryData.setting.jPanelBackground = style.jPanelBackground;
        MathToolkitNecessaryData.mathToolkitNecessaryData.setting.foreground = style.foreground;
        MathToolkitNecessaryData.mathToolkitNecessaryData.setting.background = style.background;
        MathToolkitNecessaryData.mathToolkitNecessaryData.setting.font = style.font;
    }
}
