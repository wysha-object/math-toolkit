package math.math.object;

import math.MathObject;
import math.MathTools;

import java.math.BigDecimal;
import java.math.BigInteger;

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
        return numerator == fraction.numerator && denominator == fraction.denominator;
    }

    @Override
    public String toString() {
        if (denominator.compareTo(BigInteger.valueOf(1)) != 0) {
            return numerator + "/" + denominator;
        } else {
            return String.valueOf(numerator);
        }
    }
}
