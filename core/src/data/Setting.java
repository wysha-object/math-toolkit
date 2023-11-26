/*
 * 开发者:熊锦枫
 * 开发者邮箱:wyshazhisishen@yeah.net
 */

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
        NecessaryData.necessaryData.setting.style=style;
        NecessaryData.necessaryData.setting.jPanelBackground = style.jPanelBackground;
        NecessaryData.necessaryData.setting.foreground = style.foreground;
        NecessaryData.necessaryData.setting.background = style.background;
        NecessaryData.necessaryData.setting.font = style.font;
    }
}
