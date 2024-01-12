package math.math.object;

import math.MathObject;
import math.MathTools;
import math.math.math.ArithmeticOperation;
import math.math.math.Braces;

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
        StringBuilder string;
        if (denominator.compareTo(BigInteger.valueOf(1)) != 0) {
            string = new StringBuilder(Braces.LEFT.toString() + numerator + ArithmeticOperation.DIVIDE + denominator + Braces.RIGHT);
            if (string.length() > 9) {
                //科学计数法
                if (MathTools.numIsPowerOfBase(new BigDecimal(denominator), BigDecimal.valueOf(10))) {
                    BigDecimal bigDecimal = new BigDecimal(numerator).divide(new BigDecimal(denominator));
                    string = new StringBuilder(bigDecimal.toString());
                    if (bigDecimal.compareTo(BigDecimal.valueOf(-1)) > 0 && bigDecimal.compareTo(BigDecimal.valueOf(1)) < 0) {
                        int n = -1;
                        for (int i = 2; i < string.length(); ++i) {
                            if (Objects.equals(string.charAt(i), '0')) {
                                --n;
                            } else {
                                string = new StringBuilder(string.substring(i));
                                break;
                            }
                            ;
                        }
                        string.insert(1, ".");
                        string.append("E").append(n);
                    }
                } else {
                    StringBuilder n = new StringBuilder(numerator.toString());
                    n.insert(1, ".");
                    n.append("E").append(n.length() - 3);
                    StringBuilder d = new StringBuilder(denominator.toString());
                    d.insert(1, ".");
                    d.append("E").append(d.length() - 3);
                    string = new StringBuilder(Braces.LEFT.toString() + n + ArithmeticOperation.DIVIDE + d + Braces.RIGHT);
                }
            }
        } else {
            string = new StringBuilder(String.valueOf(numerator));
        }
        return string.toString();
    }
}
