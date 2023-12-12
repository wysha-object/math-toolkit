package math.math.math;

import math.Math;
import math.math.object.Fraction;

import java.math.BigDecimal;

/**
 * @author wysha
 */

public enum ArithmeticOperation implements Math {
    /*

     */
    ADD(1),//加
    SUBTRACT(1),//减
    MULTIPLY(2),//乘
    DIVIDE(2),//除
    POWERED(3),//乘方
    ROOTING(3);//开方
    public static final int MAX = 3;
    public final int Priority;

    ArithmeticOperation(int priority) {
        Priority = priority;
    }

    public static Fraction operation(ArithmeticOperation arithmeticOperation, Fraction theValueBeingManipulated, Fraction operationValue) throws Throwable {
        BigDecimal denominator = new BigDecimal(
                theValueBeingManipulated.denominator.multiply(operationValue.denominator)
        );
        switch (arithmeticOperation) {
            case ADD:
                return new Fraction(
                        new BigDecimal((
                                theValueBeingManipulated.numerator.multiply(
                                        operationValue.denominator
                                )).add((
                                operationValue.numerator.multiply(
                                        theValueBeingManipulated.denominator
                                )))
                        ),
                        denominator
                );
            case SUBTRACT:
                return new Fraction(
                        new BigDecimal((
                                theValueBeingManipulated.numerator.multiply(
                                        operationValue.denominator
                                )).subtract((
                                operationValue.numerator.multiply(
                                        theValueBeingManipulated.denominator
                                )))
                        ),
                        denominator
                );
            case MULTIPLY:
                return new Fraction(
                        new BigDecimal(
                                theValueBeingManipulated.numerator.multiply(operationValue.numerator)
                        )
                        ,
                        denominator
                );
            case DIVIDE:
                return new Fraction(
                        new BigDecimal(theValueBeingManipulated.numerator.multiply(operationValue.denominator))
                        ,
                        new BigDecimal(theValueBeingManipulated.denominator.multiply(operationValue.numerator))
                );
            case POWERED:
            case ROOTING:
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
            case POWERED:
                return "^";
            case ROOTING:
                return "√";
            default:
                throw new NullPointerException();
        }
    }
}
