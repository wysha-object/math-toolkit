package cn.com.wysha.math_toolkit.math.equation;

import cn.com.wysha.math_toolkit.math.MathGroup;
import cn.com.wysha.math_toolkit.math.MathObjects;
import cn.com.wysha.math_toolkit.math.math.object.Formula;

/**
 * @author wysha
 */
public class AbstractEquation extends MathObjects {
    final Formula left;
    final Formula right;

    protected AbstractEquation(
            String name,
            Formula left,
            Formula right,
            MathGroup mathGroup//所属数学组
    ) throws Throwable {
        super(name);
        this.left = left;
        this.right = right;
        mathGroup.checkName(this);
        mathGroup.equations.add(this);
    }
}
