package math.math.object;

import math.MathObject;

public class Brace extends MathObject {
    final int start;
    final int end;

    public Brace(int start, int end) {
        this.start = start;
        this.end = end;
    }
}
