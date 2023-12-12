
package main.edit;

import main.MathGroupMainInterface;
import tools.AbstractSubpages;

import javax.swing.*;

/**
 * @author wysha
 */
public abstract class AbstractFunctionCalculatorEdits extends AbstractSubpages {
    protected AbstractFunctionCalculatorEdits(String name, JDialog jDialog, MathGroupMainInterface mathGroupMainInterface) {
        super(jDialog, name, mathGroupMainInterface);
    }
}
