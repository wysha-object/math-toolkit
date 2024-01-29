package cn.com.wysha.math_toolkit.math.equation.multivariate;

import cn.com.wysha.math_toolkit.math.MathGroup;
import cn.com.wysha.math_toolkit.math.equation.AbstractEquation;
import cn.com.wysha.math_toolkit.math.math.object.Formula;

import java.io.Serializable;

/**
 * @author wysha
 */
public class MultivariateEquation extends AbstractEquation implements Serializable {
    protected MultivariateEquation(String name, Formula left, Formula right, MathGroup mathGroup) throws Throwable {
        super(name, left, right, mathGroup);
    }
}