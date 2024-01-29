package cn.com.wysha.math_toolkit.math;

import cn.com.wysha.math_toolkit.math.math.math.ArithmeticOperation;
import cn.com.wysha.math_toolkit.math.math.math.Braces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author wysha
 */
public interface Math extends Serializable {
    static Math valueOf(String string) {
        ArrayList<Math> mathArrayList = new ArrayList<>();
        mathArrayList.addAll(Arrays.asList(ArithmeticOperation.values()));
        mathArrayList.addAll(Arrays.asList(Braces.values()));
        for (Math math : mathArrayList) {
            if (Objects.equals(math.toString(), string)) {
                return math;
            }
        }
        return null;
    }
}
