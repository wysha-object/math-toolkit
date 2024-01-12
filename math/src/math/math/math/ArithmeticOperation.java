package math.math.math;

import math.Math;
import math.math.object.Fraction;

import java.math.BigDecimal;
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

    public static Fraction operation(ArithmeticOperation arithmeticOperation, Fraction left, Fraction right) throws Throwable {
        BigDecimal denominator;
        BigDecimal bigDecimal = new BigDecimal(
                left.denominator.multiply(right.denominator)
        );
        double d;
        return switch (arithmeticOperation) {
            case ADD -> {
                denominator = bigDecimal;
                yield new Fraction(
                        new BigDecimal((
                                left.numerator.multiply(
                                        right.denominator
                                )).add((
                                right.numerator.multiply(
                                        left.denominator
                                )))
                        ),
                        denominator
                );
            }
            case SUBTRACT -> {
                denominator = bigDecimal;
                yield new Fraction(
                        new BigDecimal((
                                left.numerator.multiply(
                                        right.denominator
                                )).subtract((
                                right.numerator.multiply(
                                        left.denominator
                                )))
                        ),
                        denominator
                );
            }
            case MULTIPLY -> {
                denominator = bigDecimal;
                yield new Fraction(
                        new BigDecimal(
                                left.numerator.multiply(right.numerator)
                        )
                        ,
                        denominator
                );
            }
            case DIVIDE -> new Fraction(
                    new BigDecimal(
                            left.numerator.multiply(right.denominator)
                    )
                    ,
                    new BigDecimal(
                            left.denominator.multiply(right.numerator)
                    )
            );
            case POWERED -> {
                d = Double.parseDouble(String.valueOf(new BigDecimal(right.numerator).divide(new BigDecimal(right.denominator), 10, RoundingMode.HALF_DOWN)));
                yield new Fraction(
                        BigDecimal.valueOf(java.lang.Math.pow(
                                Double.parseDouble(String.valueOf(left.numerator)),
                                d
                        ))
                        ,
                        BigDecimal.valueOf(java.lang.Math.pow(
                                Double.parseDouble(String.valueOf(left.denominator)),
                                d
                        ))
                );
            }
            case ROOTING -> {
                d = Double.parseDouble(String.valueOf(new BigDecimal(left.numerator).divide(new BigDecimal(left.denominator), 10, RoundingMode.HALF_DOWN)));
                yield new Fraction(
                        BigDecimal.valueOf(java.lang.Math.pow(
                                Double.parseDouble(String.valueOf(right.numerator)),
                                1.0 / d
                        ))
                        ,
                        BigDecimal.valueOf(java.lang.Math.pow(
                                Double.parseDouble(String.valueOf(right.denominator)),
                                1.0 / d
                        ))
                );
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
