package math.function.onevariable;

import math.ArithmeticOperation;
import math.Fraction;
import math.Variable;
import math.function.AbstractFunction;
import math.function.AbstractOneVariableFunction;

import java.util.List;

/**
 * @author wysha
 */
public class OneVariableOneDegreeFunction extends AbstractOneVariableFunction {
    public OneVariableOneDegreeFunction(String name, Fraction k, Fraction b, List<AbstractFunction> functions) {
        super(name,functions);
        fractions.add(k);
        arithmeticOperations.add(ArithmeticOperation.MULTIPLY);
        fractions.add(null);
        new Variable("x", variables);
        arithmeticOperations.add(ArithmeticOperation.ADD);
        fractions.add(b);
    }
}
