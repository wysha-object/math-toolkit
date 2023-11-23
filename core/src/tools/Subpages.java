/*
 * 开发者:熊锦枫
 * 开发者邮箱:wyshazhisishen@yeah.net
 */

package tools;

import javax.swing.*;

public abstract class Subpages {
    public final String name;
    public final JDialog jDialog;

    protected Subpages(String name, JDialog jDialog) {
        this.name=name;
        this.jDialog = jDialog;
    }

    public abstract void onOK();
    public abstract void onCancel();
}
