package math.function.onevariable;

import math.MathGroup;
import math.function.AbstractOneVariableFunction;
import math.math.math.ArithmeticOperation;
import math.math.numberObject.Fraction;
import math.math.objects.Variable;

/**
 * @author wysha
 */
public class OneVariableOneDegreeFunction extends AbstractOneVariableFunction {
    public OneVariableOneDegreeFunction(String name, Fraction k, Fraction b, MathGroup mathGroup) throws Throwable {
        super(name, mathGroup);
        formula.add(k.numerator);
        formula.divide(k.denominator);
        formula.arithmeticOperations.add(ArithmeticOperation.MULTIPLY);
        formula.values.add(null);
        new Variable("x", formula.variables);
        formula.arithmeticOperations.add(ArithmeticOperation.ADD);
        formula.add(b.numerator);
        formula.divide(b.denominator);
    }
}
