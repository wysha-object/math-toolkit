package data;

import java.awt.*;
import java.io.ObjectInputStream;
import java.nio.file.Files;

/**
 * @author wysha
 */
public class MathToolkitNecessaryData extends AbstractWrittenData {
    public static MathToolkitNecessaryData mathToolkitNecessaryData = new MathToolkitNecessaryData();

    public final Style[] styles = new Style[3];
    public final Setting setting = new Setting();

    public MathToolkitNecessaryData() {
        super(MathToolkitNecessaryData.class.getName());
        mathToolkitNecessaryData = this;
    }

    @Override
    public void read() {
        try {
            mathToolkitNecessaryData = (MathToolkitNecessaryData) new ObjectInputStream(Files.newInputStream(file.toPath())).readObject();
        } catch (Throwable e) {
            styles[0] = new Style(
                    "white",
                    new Color(0, 0, 0),
                    new Color(251, 251, 251),
                    new Color(255, 255, 255),
                    new Font("Microsoft YaHei", Font.PLAIN, 14)
            );
            styles[1] = new Style(
                    "black",
                    new Color(255, 255, 255),
                    new Color(31, 31, 31),
                    new Color(0, 0, 0),
                    new Font("Microsoft YaHei", Font.PLAIN, 14)
            );
            styles[2] = new Style(
                    "grey",
                    new Color(255, 255, 255),
                    new Color(63, 63, 63),
                    new Color(127, 127, 127),
                    new Font("Microsoft YaHei", Font.PLAIN, 14)
            );
            Setting.setStyle(styles[0]);
        }
    }
}
