package math;

import java.math.BigDecimal;

public class MathTools {
    public static int isPowerOf(BigDecimal num, BigDecimal n) throws Throwable {
        int i = 1;
        while (num.compareTo(n) > 0) {
            num = num.divide(n);
            i++;
        }
        if (i == 1 || num.divideAndRemainder(BigDecimal.valueOf(1))[1].compareTo(BigDecimal.valueOf(0)) != 0 || num.compareTo(n) < 0) {
            throw new Throwable();
        }
        return i;
    }

    public static BigDecimal gcd(BigDecimal num1, BigDecimal num2) {
        //辗转相除法(欧几里得算法)
        while (num2.compareTo(new BigDecimal(0)) != 0) {
            BigDecimal temp = num1.divideAndRemainder(num2)[1];
            num1 = num2;
            num2 = temp;
        }
        return num1;
    }
}
