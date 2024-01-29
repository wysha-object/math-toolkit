package cn.com.wysha.math_toolkit.math.equation.oneVariable;

import cn.com.wysha.math_toolkit.math.MathGroup;
import cn.com.wysha.math_toolkit.math.equation.AbstractOneVariableEquation;
import cn.com.wysha.math_toolkit.math.math.object.Formula;

/**
 * @author wysha
 */
public class OneVariableOneDegreeEquation extends AbstractOneVariableEquation {
    protected OneVariableOneDegreeEquation(String name, Formula left, Formula right, MathGroup mathGroup) throws Throwable {
        super(name, left, right, mathGroup);
    }
}
