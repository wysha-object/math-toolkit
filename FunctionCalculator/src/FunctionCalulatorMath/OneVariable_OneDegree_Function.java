/*
 * 开发者:熊锦枫
 * 开发者邮箱:wyshazhisishen@yeah.net
 */
package FunctionCalulatorMath;

import math.ArithmeticOperation;
import math.Fraction;
import math.Variable;

public class OneVariable_OneDegree_Function extends OneVariable_Function {
    public OneVariable_OneDegree_Function(String name, Fraction k, Fraction b) {
        super(name);
        fractions.add(k);
        arithmeticOperations.add(ArithmeticOperation.multiply);
        fractions.add(null);
        new Variable("x",variables);
        arithmeticOperations.add(ArithmeticOperation.add);
        fractions.add(b);
    }
}
