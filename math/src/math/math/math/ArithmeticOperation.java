package math.math.math;

import math.Math;

import java.math.BigDecimal;
import java.math.BigInteger;

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

    public static BigInteger operation(ArithmeticOperation arithmeticOperation, BigInteger left, BigInteger right) throws Throwable {
        return switch (arithmeticOperation) {
            case ADD -> left.add(right);
            case SUBTRACT -> left.subtract(right);
            case MULTIPLY -> left.multiply(right);
            case DIVIDE -> {
                BigInteger[] bigIntegers = left.divideAndRemainder(right);
                if (bigIntegers[1].compareTo(BigInteger.ZERO)>0){
                    throw new Throwable();
                }else {
                    yield bigIntegers[0];
                }
            }
            case POWERED -> {
                double l=left.doubleValue();
                double r=right.doubleValue();
                double rs= java.lang.Math.pow(l,r);
                if (rs%1>0){
                    throw new Throwable();
                }else {
                    yield BigInteger.valueOf((long) rs);
                }
            }
            case ROOTING -> {
                BigDecimal l=new BigDecimal(left);
                double r=right.doubleValue();
                double rs= java.lang.Math.pow(r,BigDecimal.ONE.divide(l).doubleValue());
                if (rs%1>0){
                    throw new Throwable();
                }else {
                    yield BigInteger.valueOf((long) rs);
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
