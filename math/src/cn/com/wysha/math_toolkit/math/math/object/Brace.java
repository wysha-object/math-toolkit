package cn.com.wysha.math_toolkit.math.math.object;

import cn.com.wysha.math_toolkit.math.MathObject;

/**
 * @author wysha
 */
public class Brace extends MathObject {
    final int start;
    final int end;

    public Brace(int start, int end) {
        this.start = start;
        this.end = end;
    }
}
