package math.function.multivariate;

import math.MathGroup;
import math.function.AbstractFunction;
import math.math.object.Formula;

import java.io.Serializable;

/**
 * @author wysha
 */
public class MultivariateFunction extends AbstractFunction implements Serializable {
    public MultivariateFunction(String name, Formula formula, MathGroup mathGroup) throws Throwable {
        super(name, formula, mathGroup);
    }

    public static void valueOf(String function, MathGroup mathGroup) throws Throwable {
        String[] str = function.split("=");
        new MultivariateFunction(str[0], Formula.valueOf(str[1]), mathGroup);
    }
}