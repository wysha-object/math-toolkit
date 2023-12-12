package math;

import java.math.BigDecimal;

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
}
