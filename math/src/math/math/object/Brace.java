package math.math.object;

import math.MathObject;

public class Brace extends MathObject {
    public final int start;
    public final int end;

    public Brace(int start, int end) {
        this.start = start;
        this.end = end;
    }
}
