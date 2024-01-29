package cn.com.wysha.math_toolkit.tools;

import javax.swing.*;

public abstract class AbstractSubpage {

    public final JDialog jDialog;
    public final String name;

    protected AbstractSubpage(JDialog jDialog, String name) {
        this.jDialog = jDialog;
        this.name = name;
    }

    /**
     * 确认时执行的操作
     */
    public abstract void onOkay();

    /**
     * 取消时执行的操作
     */
    public abstract void onCancel();
}
