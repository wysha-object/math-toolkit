package math.function.onevariable;

import math.MathGroup;
import math.function.AbstractOneVariableFunction;
import math.math.math.ArithmeticOperation;
import math.math.object.Fraction;
import math.math.objects.Variable;

/**
 * @author wysha
 */
public class OneVariableOneDegreeFunction extends AbstractOneVariableFunction {
    public OneVariableOneDegreeFunction(String name, Fraction k, Fraction b, MathGroup mathGroup) throws Throwable {
        super(name, mathGroup);
        formula.fractions.add(k);
        formula.arithmeticOperations.add(ArithmeticOperation.MULTIPLY);
        formula.fractions.add(null);
        new Variable("x", formula.variables);
        formula.arithmeticOperations.add(ArithmeticOperation.ADD);
        formula.fractions.add(b);
    }
}
