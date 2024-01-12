package math.equation.multivariate;

import math.MathGroup;
import math.equation.AbstractEquation;
import math.math.numberObject.Formula;

import java.io.Serializable;

/**
 * @author wysha
 */
public class MultivariateEquation extends AbstractEquation implements Serializable {
    protected MultivariateEquation(String name, Formula left, Formula right, MathGroup mathGroup) throws Throwable {
        super(name, left, right, mathGroup);
    }
}