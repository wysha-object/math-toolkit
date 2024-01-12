package math.math.math;

import math.Math;

/**
 * @author wysha
 */

public enum Braces implements Math {
    /**
     * 左括号
     */
    LEFT,
    /**
     * 右括号
     */
    RIGHT;

    @Override
    public String toString() {
        return switch (this) {
            case LEFT -> "(";
            case RIGHT -> ")";
        };
    }
}
