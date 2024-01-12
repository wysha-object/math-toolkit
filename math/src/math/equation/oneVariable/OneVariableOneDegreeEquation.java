package math.equation.oneVariable;

import math.MathGroup;
import math.equation.AbstractOneVariableEquation;
import math.math.numberObject.Formula;

/**
 * @author wysha
 */
public class OneVariableOneDegreeEquation extends AbstractOneVariableEquation {
    protected OneVariableOneDegreeEquation(String name, Formula left, Formula right, MathGroup mathGroup) throws Throwable {
        super(name, left, right, mathGroup);
    }
}
