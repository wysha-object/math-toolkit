/*
 * 开发者:熊锦枫
 * 开发者邮箱:wyshazhisishen@yeah.net
 */
package math;

/**
 * @author wysha
 */
public class OneVariableOneDegreeFunction extends AbstractOneVariableFunction {
    public OneVariableOneDegreeFunction(String name, Fraction k, Fraction b) {
        super(name);
        fractions.add(k);
        arithmeticOperations.add(ArithmeticOperation.MULTIPLY);
        fractions.add(null);
        new Variable("x", variables);
        arithmeticOperations.add(ArithmeticOperation.ADD);
        fractions.add(b);
    }
}
