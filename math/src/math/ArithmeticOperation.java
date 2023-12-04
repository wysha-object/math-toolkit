package math;

/**
 * @author wysha
 */

public enum ArithmeticOperation {
    /*

     */
    ADD(1), SUBTRACT(1), MULTIPLY(2), DIVIDE(2);
    public final int Priority;
    public static final int MAX = 2;
    ArithmeticOperation(int priority){
        Priority=priority;
    }

    public static Fraction operation(ArithmeticOperation arithmeticOperation, Fraction theValueBeingManipulated, Fraction operationValue) throws Throwable {
        switch (arithmeticOperation) {
            case ADD:
                return new Fraction(theValueBeingManipulated.numerator * operationValue.denominator + operationValue.numerator * theValueBeingManipulated.denominator, theValueBeingManipulated.denominator * operationValue.denominator);
            case SUBTRACT:
                return new Fraction(theValueBeingManipulated.numerator * operationValue.denominator - operationValue.numerator * theValueBeingManipulated.denominator, theValueBeingManipulated.denominator * operationValue.denominator);
            case MULTIPLY:
                return new Fraction(theValueBeingManipulated.numerator * operationValue.numerator, theValueBeingManipulated.denominator * operationValue.denominator);
            case DIVIDE:
                return new Fraction(theValueBeingManipulated.numerator * operationValue.denominator, theValueBeingManipulated.denominator * operationValue.numerator);
            default:
                return theValueBeingManipulated;
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case ADD:
                return "+";
            case SUBTRACT:
                return "-";
            case MULTIPLY:
                return "*";
            case DIVIDE:
                return "/";
            default:
                throw new NullPointerException();
        }
    }
}
