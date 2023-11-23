/*
 * 开发者:熊锦枫
 * 开发者邮箱:wyshazhisishen@yeah.net
 */

package math;

public enum ArithmeticOperation {
    add(1),subtract(1),multiply(2),divide(2);
    public final int Priority;
    public static final int max=2;
    ArithmeticOperation(int priority){
        Priority=priority;
    }
    @Override
    public String toString() {
        switch (this) {
            case add:
                return "+";
            case subtract:
                return "-";
            case multiply:
                return "*";
            case divide:
                return "/";
            default:
                throw new NullPointerException();
        }
    }

    public static Fraction operation(ArithmeticOperation arithmeticOperation, Fraction TheValueBeingManipulated, Fraction OperationValue) throws Throwable {
        switch (arithmeticOperation) {
            case add:
                return new Fraction(TheValueBeingManipulated.numerator * OperationValue.denominator + OperationValue.numerator * TheValueBeingManipulated.denominator, TheValueBeingManipulated.denominator * OperationValue.denominator);
            case subtract:
                return new Fraction(TheValueBeingManipulated.numerator * OperationValue.denominator - OperationValue.numerator * TheValueBeingManipulated.denominator, TheValueBeingManipulated.denominator * OperationValue.denominator);
            case multiply:
                return new Fraction(TheValueBeingManipulated.numerator * OperationValue.numerator, TheValueBeingManipulated.denominator * OperationValue.denominator);
            case divide:
                return new Fraction(TheValueBeingManipulated.numerator * OperationValue.denominator, TheValueBeingManipulated.denominator * OperationValue.numerator);
        }
        return TheValueBeingManipulated;
    }
}
