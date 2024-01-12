package math.equation;

import math.MathGroup;
import math.math.numberObject.Formula;

/**
 * @author wysha
 */
public abstract class AbstractOneVariableEquation extends AbstractEquation {
    protected AbstractOneVariableEquation(String name, Formula left, Formula right, MathGroup mathGroup) throws Throwable {
        super(name, left, right, mathGroup);
    }
}
