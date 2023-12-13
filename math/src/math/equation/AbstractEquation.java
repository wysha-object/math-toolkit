package math.equation;

import math.MathGroup;
import math.MathObjects;
import math.math.object.Formula;

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
