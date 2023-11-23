/*
 * 开发者:熊锦枫
 * 开发者邮箱:wyshazhisishen@yeah.net
 */

package set.settings;

import tools.Subpages;

import javax.swing.*;

public abstract class Settings extends Subpages {
    protected Settings(String name, JDialog jDialog) {
        super(name, jDialog);
    }
    public void onOK(){
        save();
        jDialog.dispose();
    }
    public abstract void save();
}
