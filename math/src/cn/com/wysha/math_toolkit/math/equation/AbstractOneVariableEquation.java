package cn.com.wysha.math_toolkit.math.equation;

import cn.com.wysha.math_toolkit.math.MathGroup;
import cn.com.wysha.math_toolkit.math.math.object.Formula;

/**
 * @author wysha
 */
public abstract class AbstractOneVariableEquation extends AbstractEquation {
    protected AbstractOneVariableEquation(String name, Formula left, Formula right, MathGroup mathGroup) throws Throwable {
        super(name, left, right, mathGroup);
    }
}
