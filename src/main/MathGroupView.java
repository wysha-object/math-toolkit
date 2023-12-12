package main;

import javax.swing.*;

public abstract class MathGroupView extends JDialog {
    public final MathGroupMainInterface mathGroupMainInterface;

    public MathGroupView(MathGroupMainInterface mathGroupMainInterface) {
        this.mathGroupMainInterface = mathGroupMainInterface;
    }
}
