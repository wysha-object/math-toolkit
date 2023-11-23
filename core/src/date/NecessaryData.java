/*
 * 开发者:熊锦枫
 * 开发者邮箱:wyshazhisishen@yeah.net
 */

package date;

import java.awt.*;
import java.io.ObjectInputStream;
import java.nio.file.Files;

public class NecessaryData extends WrittenDate {
    public static NecessaryData necessaryData=new NecessaryData();

    public NecessaryData() {super("NecessaryData");necessaryData=this;}
    public final Style[] styles=new Style[4];
    public final Setting setting = new Setting();

    public void read() throws Throwable{
        if (file.exists()){
            necessaryData= (NecessaryData) new ObjectInputStream(Files.newInputStream(file.toPath())).readObject();
        }else {
            styles[0]=new Style(
                    "炫酷白",
                    new Color(0,0,0),
                    new Color(251,251,251),
                    new Color(255,255,255),
                    new Font("Microsoft YaHei UI Light",Font.PLAIN,14)
            );
            styles[1]=new Style(
                    "酷炫黑",
                    new Color(255,255,255),
                    new Color(31,31,31),
                    new Color(0,0,0),
                    new Font("Microsoft YaHei UI Light",Font.PLAIN,14)
            );
            styles[2]=new Style(
                    "酷炫灰",
                    new Color(255,255,255),
                    new Color(63,63,63),
                    new Color(127,127,127),
                    new Font("Microsoft YaHei UI Light",Font.PLAIN,14)
            );
            styles[3]=new Style(
                    "高对比度",
                    Color.WHITE,
                    Color.BLACK,
                    Color.BLACK,
                    null
            );
            Setting.setStyle(styles[0]);
        }
    }
}
