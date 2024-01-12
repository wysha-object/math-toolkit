package math.math.math;

import math.Math;

public interface Operation<E> extends Math {
    E add(E o) throws Throwable;

    E subtract(E o) throws Throwable;

    E multiply(E o) throws Throwable;

    E divide(E o) throws Throwable;

    E powered(E o) throws Throwable;

    E rooting(E o) throws Throwable;
}
