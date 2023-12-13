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
        formula.bigIntegers.add(k.numerator);
        formula.arithmeticOperations.add(ArithmeticOperation.DIVIDE);
        formula.bigIntegers.add(k.denominator);
        formula.arithmeticOperations.add(ArithmeticOperation.MULTIPLY);
        formula.bigIntegers.add(null);
        new Variable("x", formula.variables);
        formula.arithmeticOperations.add(ArithmeticOperation.ADD);
        formula.bigIntegers.add(b.numerator);
        formula.arithmeticOperations.add(ArithmeticOperation.DIVIDE);
        formula.bigIntegers.add(b.denominator);
    }
}
