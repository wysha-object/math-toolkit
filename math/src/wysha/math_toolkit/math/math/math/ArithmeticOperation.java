package wysha.math_toolkit.math.math.math;

import wysha.math_toolkit.math.Math;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * @author wysha
 */

public enum ArithmeticOperation implements Math {
    /**
     * 加
     */
    ADD(1),
    /**
     * 减
     */
    SUBTRACT(1),
    /**
     * 乘
     */
    MULTIPLY(2),
    /**
     * 除
     */
    DIVIDE(2),
    /**
     * 乘方
     */
    POWERED(3),
    /**
     * 开方
     */
    ROOTING(3);
    public static final int MAX = 3;
    public final int Priority;

    ArithmeticOperation(int priority) {
        Priority = priority;
    }

    public static BigInteger operation(ArithmeticOperation arithmeticOperation, BigInteger left, BigInteger right, boolean forcedCalculations) {
        return operation(arithmeticOperation, new BigDecimal(left), new BigDecimal(right), forcedCalculations).toBigInteger();
    }

    public static BigDecimal operation(ArithmeticOperation arithmeticOperation, BigDecimal left, BigDecimal right, boolean forcedCalculations) {
        return switch (arithmeticOperation) {
            case ADD -> left.add(right);
            case SUBTRACT -> left.subtract(right);
            case MULTIPLY -> left.multiply(right);
            case DIVIDE -> {
                BigDecimal bigIntegers = left.divide(right, 10, RoundingMode.HALF_UP);
                if (forcedCalculations) {
                    yield bigIntegers;
                }
                if (bigIntegers.divideAndRemainder(BigDecimal.ONE)[1].compareTo(BigDecimal.ZERO) > 0) {
                    throw new RuntimeException();
                } else {
                    yield bigIntegers;
                }
            }
            case POWERED -> {
                double l = left.doubleValue();
                double r = right.doubleValue();
                double rs = java.lang.Math.pow(l, r);
                if (forcedCalculations) {
                    yield BigDecimal.valueOf((long) rs);
                }
                if (rs % 1 > 0) {
                    throw new RuntimeException();
                } else {
                    yield BigDecimal.valueOf((long) rs);
                }
            }
            case ROOTING -> {
                double r = right.doubleValue();
                double rs = java.lang.Math.pow(r, BigDecimal.ONE.divide(left, 10, RoundingMode.HALF_UP).doubleValue());
                if (forcedCalculations) {
                    yield BigDecimal.valueOf((long) rs);
                }
                if (rs % 1 > 0) {
                    throw new RuntimeException();
                } else {
                    yield BigDecimal.valueOf((long) rs);
                }
            }
        };
    }

    @Override
    public String toString() {
        return switch (this) {
            case ADD -> "+";
            case SUBTRACT -> "-";
            case MULTIPLY -> "*";
            case DIVIDE -> "/";
            case POWERED -> "^";
            case ROOTING -> "√";
        };
    }
}
