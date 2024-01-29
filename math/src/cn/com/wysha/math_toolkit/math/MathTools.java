package cn.com.wysha.math_toolkit.math;

import java.math.BigDecimal;

/**
 * @author wysha
 */
public class MathTools {
    public static BigDecimal gcd(BigDecimal num1, BigDecimal num2) {
        //辗转相除法(欧几里得算法)
        while (num2.compareTo(new BigDecimal(0)) != 0) {
            BigDecimal temp = num1.divideAndRemainder(num2)[1];
            num1 = num2;
            num2 = temp;
        }
        return num1;
    }

    public static boolean numIsPowerOfBase(BigDecimal num, BigDecimal base) {
        BigDecimal bigDecimal = base;
        while (bigDecimal.compareTo(num) < 0) {
            bigDecimal = bigDecimal.multiply(base);
            if (num.compareTo(bigDecimal) == 0) {
                return true;
            }
        }
        return false;
    }
}
