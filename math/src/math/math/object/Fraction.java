package math.math.object;

import math.MathObject;
import math.MathTools;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

/**
 * @author wysha
 */
final public class Fraction extends MathObject {
    public final BigInteger numerator;
    public final BigInteger denominator;

    public Fraction(BigDecimal numerator, BigDecimal denominator) throws Throwable {
        if (denominator.compareTo(BigDecimal.valueOf(0)) == 0) {
            if (numerator.compareTo(BigDecimal.valueOf(0)) == 0) {
                throw new Exception("结果未定义");
            }
            throw new Exception("分母不能为0");
        }
        BigDecimal rs = MathTools.gcd(numerator, denominator);
        numerator = numerator.divide(rs);
        denominator = denominator.divide(rs);
        this.numerator = numerator.toBigInteger();
        this.denominator = denominator.toBigInteger();
    }

    public Fraction(BigInteger numerator, BigInteger denominator) throws Throwable {
        this(new BigDecimal(numerator), new BigDecimal(denominator));
    }

    public int isMoreThan(Fraction f) {
        return this.numerator.multiply(f.denominator).compareTo(this.denominator.multiply(f.numerator));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Fraction fraction = (Fraction) o;
        return Objects.equals(numerator, fraction.numerator) && Objects.equals(denominator, fraction.denominator);
    }

    @Override
    public String toString() {
        if (denominator.compareTo(BigInteger.valueOf(1)) != 0) {
            int i;
            try {
                i = MathTools.isPowerOf(new BigDecimal(denominator), BigDecimal.valueOf(10));
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(numerator));
                int k = i + 1 - stringBuilder.length();
                for (int j = 0; j < k; j++) {
                    stringBuilder.insert(0, '0');
                }
                stringBuilder.insert(stringBuilder.length() - i, '.');
                return String.valueOf(stringBuilder);
            } catch (Throwable ignored) {
            }
            return numerator + "/" + denominator;
        } else {
            return String.valueOf(numerator);
        }
    }
}
