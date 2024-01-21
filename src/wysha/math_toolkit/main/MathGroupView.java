package wysha.math_toolkit.main;

import javax.swing.*;

/**
 * @author wysha
 */
public abstract class MathGroupView extends JDialog {
    public final MathGroupMainInterface mathGroupMainInterface;

    public MathGroupView(MathGroupMainInterface mathGroupMainInterface) {
        this.mathGroupMainInterface = mathGroupMainInterface;
    }
}
