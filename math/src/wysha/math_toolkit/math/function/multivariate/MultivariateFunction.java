package wysha.math_toolkit.math.function.multivariate;

import wysha.math_toolkit.math.MathGroup;
import wysha.math_toolkit.math.function.AbstractFunction;
import wysha.math_toolkit.math.math.object.Formula;

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