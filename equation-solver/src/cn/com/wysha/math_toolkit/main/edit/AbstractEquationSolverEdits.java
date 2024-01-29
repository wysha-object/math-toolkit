package cn.com.wysha.math_toolkit.main.edit;

import cn.com.wysha.math_toolkit.main.MathGroupMainInterface;
import cn.com.wysha.math_toolkit.tools.AbstractSubpages;

import javax.swing.*;

/**
 * @author wysha
 */
public abstract class AbstractEquationSolverEdits extends AbstractSubpages {
    protected AbstractEquationSolverEdits(String name, JDialog jDialog, MathGroupMainInterface mathGroupMainInterface) {
        super(jDialog, name, mathGroupMainInterface);
    }
}
