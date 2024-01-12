package tools;

import main.MathGroupMainInterface;

import javax.swing.*;

/**
 * @author wysha
 */
public abstract class AbstractSubpages extends AbstractSubpage {
    public final MathGroupMainInterface mathGroupMainInterface;

    protected AbstractSubpages(JDialog jDialog, String name, MathGroupMainInterface mathGroupMainInterface) {
        super(jDialog, name);
        this.mathGroupMainInterface = mathGroupMainInterface;
    }

    /**
     * 确认时执行的操作
     */
    @Override
    public abstract void onOkay();

    /**
     * 取消时执行的操作
     */
    @Override
    public abstract void onCancel();
}
