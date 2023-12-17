package main.edit;

import main.MathGroupMainInterface;
import tools.AbstractSubpages;

import javax.swing.*;

/**
 * @author wysha
 */
public abstract class AbstractEquationSolverEdits extends AbstractSubpages {
    protected AbstractEquationSolverEdits(String name, JDialog jDialog, MathGroupMainInterface mathGroupMainInterface) {
        super(jDialog, name, mathGroupMainInterface);
    }
}
