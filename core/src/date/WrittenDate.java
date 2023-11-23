/*
 * 开发者:熊锦枫
 * 开发者邮箱:wyshazhisishen@yeah.net
 */

package date;

import tools.Prompt;

import java.io.File;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;

public abstract class WrittenDate implements Serializable {
    public final File file;
    public abstract void read() throws Throwable;
    public void write() throws Throwable{
        new ObjectOutputStream(Files.newOutputStream(
                file.toPath()
        )).writeObject(this);
        new Prompt("已保存至"+ file.getAbsolutePath()).setVisible(true);
    }
    protected WrittenDate(String name) {
        file=new File(name);
    }
}
