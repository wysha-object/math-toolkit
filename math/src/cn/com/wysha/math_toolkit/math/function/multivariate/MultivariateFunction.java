package cn.com.wysha.math_toolkit.math.function.multivariate;

import cn.com.wysha.math_toolkit.math.math.object.Formula;
import cn.com.wysha.math_toolkit.math.MathGroup;
import cn.com.wysha.math_toolkit.math.function.AbstractFunction;

import java.io.Serializable;

/**
 * @author wysha
 */
public class MultivariateFunction extends AbstractFunction implements Serializable {
    public MultivariateFunction(String name, Formula formula, MathGroup mathGroup) throws Throwable {
        super(name, formula, mathGroup);
    }

    public static void valueOf(String function, MathGroup mathGroup, boolean simplify) throws Throwable {
        String[] str = function.split("=");
        new MultivariateFunction(str[0], Formula.valueOf(str[1], simplify), mathGroup);
    }
}