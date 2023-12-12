package math.math.math;

import math.Math;

public enum Braces implements Math {
    /*

     */
    LEFT, RIGHT;

    @Override
    public String toString() {
        switch (this) {
            case LEFT:
                return "(";
            case RIGHT:
                return ")";
            default:
                throw new NullPointerException();
        }
    }
}
