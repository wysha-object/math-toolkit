package math.math.math;

import math.Math;

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

    public static <E extends Operation<E>> E operation(ArithmeticOperation arithmeticOperation, E left, E right) throws Throwable {
        return switch (arithmeticOperation) {
            case ADD -> left.add(right);
            case SUBTRACT -> left.subtract(right);
            case MULTIPLY -> left.multiply(right);
            case DIVIDE -> left.divide(right);
            case POWERED -> left.powered(right);
            case ROOTING -> left.rooting(right);
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
