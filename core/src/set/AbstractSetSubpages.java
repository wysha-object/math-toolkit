package set;

import tools.AbstractSubpage;

import javax.swing.*;

/**
 * @author wysha
 */
public abstract class AbstractSetSubpages extends AbstractSubpage {
    protected AbstractSetSubpages(String name, JDialog jDialog) {
        super(jDialog, name);
    }

    @Override
    public void onOkay() {
        save();
        jDialog.dispose();
    }

    /**
     * 保存设置
     */
    public abstract void save();
}
