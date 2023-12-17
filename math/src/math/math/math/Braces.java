package math.math.math;

import math.Math;

public enum Braces implements Math {
    /*

     */
    LEFT, RIGHT;

    @Override
    public String toString() {
        return switch (this) {
            case LEFT -> "(";
            case RIGHT -> ")";
        };
    }
}
