/*
 * 开发者:熊锦枫
 * 开发者邮箱:wyshazhisishen@yeah.net
 */

package data;

import tools.Prompt;

import java.io.File;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;

/**
 * @author wysha
 */
public abstract class AbstractWrittenData implements Serializable {
    public final File file;

    protected AbstractWrittenData(String name) {
        file = new File(name);
    }

    /**
     * 读取数据
     *
     * @throws Throwable 可能的异常
     */
    public abstract void read() throws Throwable;

    /**
     * 写入数据
     *
     * @throws Throwable 可能的异常
     */
    public void write() throws Throwable {
        new ObjectOutputStream(Files.newOutputStream(file.toPath())).writeObject(this);
        new Prompt("已保存至" + file).setVisible(true);
    }
}
