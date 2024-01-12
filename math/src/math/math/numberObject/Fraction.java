package math.math.numberObject;

import math.MathNumberObject;
import math.math.math.ArithmeticOperation;
import math.math.math.Braces;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

/**
 * @author wysha
 */
final public class Fraction extends MathNumberObject<Fraction> implements Comparable<Fraction> {
    public final Formula numerator;//分子
    public final Formula denominator;//分母
    public final Formula exponent;//指数

    private Fraction(Fraction fraction) {
        this.numerator = fraction.denominator;
        this.denominator = fraction.numerator;
        this.exponent = fraction.exponent;
    }

    public Fraction(Formula numerator, Formula denominator, Formula exponent) throws Throwable {
        /*double n;
        double d;
        double e;
        n = numerator.values.size() == 1 ? numerator.values.get(0).doubleValue() : numerator.operation().toDouble();
        d = denominator.values.size() == 1 ? denominator.values.get(0).doubleValue() : denominator.operation().toDouble();
        e = exponent.values.size() == 1 ? exponent.values.get(0).doubleValue() : exponent.operation().toDouble();
        if (n == 0) {
            if (d == 0) {
                throw new Exception("结果未定义");
            }
            throw new Exception("分母不能为0");
        }
        if (e == 0) {
            this.numerator = new Formula(BigInteger.ONE);
            this.denominator = new Formula(BigInteger.ONE);
            this.exponent = new Formula(BigInteger.ONE);
            return;
        }
        BigDecimal rs = MathTools.gcd(BigDecimal.valueOf(n), BigDecimal.valueOf(d));
        numerator = numerator.divide(new Formula(rs));
        denominator = denominator.divide(new Formula(rs));*/
        this.numerator = numerator;
        this.denominator = denominator;
        this.exponent = exponent;
    }

    public Fraction(Formula numerator, Formula denominator) throws Throwable {
        this(numerator, denominator, new Formula(BigInteger.ONE));
    }

    public Fraction(Formula numerator) throws Throwable {
        this(numerator, new Formula(BigInteger.ONE));
    }

    public Fraction(BigDecimal numerator, BigDecimal denominator, Formula exponent) throws Throwable {
        this(new Formula(numerator), new Formula(denominator), exponent);
    }

    public Fraction(BigDecimal numerator, BigDecimal denominator) throws Throwable {
        this(numerator, denominator, new Formula(BigInteger.ONE));
    }

    public Fraction(BigDecimal numerator) throws Throwable {
        this(numerator, BigDecimal.ONE);
    }

    public Fraction(BigInteger numerator, BigInteger denominator, Formula exponent) throws Throwable {
        this(new Formula(numerator), new Formula(denominator), exponent);
    }

    public Fraction(BigInteger numerator, BigInteger denominator) throws Throwable {
        this(numerator, denominator, new Formula(BigInteger.ONE));
    }

    public Fraction(BigInteger numerator) throws Throwable {
        this(numerator, BigInteger.ONE);
    }

    public Fraction toReciprocal() {
        return new Fraction(this);
    }

    public int isMoreThan(Fraction fraction) throws Throwable {
        return Double.compare(this.numerator.multiply(fraction.denominator).operation().toDouble(), this.denominator.multiply(fraction.numerator).operation().toDouble());
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
        try {
            StringBuilder stringBuilder = new StringBuilder();
            boolean b = exponent.operation().toDouble() == 1;

            stringBuilder.append(Braces.LEFT);

            if (b) {
                stringBuilder.append(Braces.LEFT);
            }

            if (numerator.values.size() == 1) {
                stringBuilder.append(numerator.values.get(0).toString());
            } else {
                stringBuilder.append(Braces.LEFT).append(numerator.operation().toString()).append(Braces.RIGHT);
            }

            if (denominator.operation().toDouble() == 1) {
                stringBuilder.append(ArithmeticOperation.DIVIDE);
                if (denominator.values.size() == 1) {
                    stringBuilder.append(denominator.values.get(0).toString());
                } else {
                    stringBuilder.append(Braces.LEFT).append(denominator.operation().toString()).append(Braces.RIGHT);
                }
            }

            if (b) {
                stringBuilder.append(Braces.RIGHT);
                stringBuilder.append(ArithmeticOperation.POWERED);
                if (exponent.values.size() == 1) {
                    stringBuilder.append(exponent.values.get(0).toString());
                } else {
                    stringBuilder.append(Braces.LEFT).append(exponent.operation().toString()).append(Braces.RIGHT);
                }
            }

            stringBuilder.append(Braces.RIGHT);

            return stringBuilder.toString();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public double toDouble() {
        try {
            return numerator.divide(denominator).powered(exponent).operation().toDouble();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Fraction add(Fraction fraction) throws Throwable {
        Formula lu = this.numerator.powered(this.exponent);
        Formula ld = this.denominator.powered(this.exponent);
        Formula ru = fraction.numerator.powered(fraction.exponent);
        Formula rd = fraction.denominator.powered(fraction.exponent);
        return new Fraction(
                (
                        lu.multiply(rd)
                ).add(
                        ru.multiply(ld)
                ),
                ld.multiply(rd)
        );
    }

    @Override
    public Fraction subtract(Fraction fraction) throws Throwable {
        Formula lu = this.numerator.powered(this.exponent);
        Formula ld = this.denominator.powered(this.exponent);
        Formula ru = fraction.numerator.powered(fraction.exponent);
        Formula rd = fraction.denominator.powered(fraction.exponent);
        return new Fraction(
                (
                        lu.multiply(rd)
                ).subtract(
                        ru.multiply(ld)
                ),
                ld.multiply(rd)
        );
    }

    @Override
    public Fraction multiply(Fraction fraction) throws Throwable {
        Formula lu = this.numerator.powered(this.exponent);
        Formula ld = this.denominator.powered(this.exponent);
        Formula ru = fraction.numerator.powered(fraction.exponent);
        Formula rd = fraction.denominator.powered(fraction.exponent);
        return new Fraction(
                lu.multiply(ru),
                ld.multiply(rd)
        );
    }

    @Override
    public Fraction divide(Fraction fraction) throws Throwable {
        return this.multiply(fraction.toReciprocal());
    }

    @Override
    public Fraction powered(Fraction fraction) throws Throwable {
        return new Fraction(
                numerator,
                denominator,
                exponent.multiply(new Formula(BigDecimal.valueOf(fraction.toDouble())))
        );
    }

    @Override
    public Fraction rooting(Fraction fraction) throws Throwable {
        return new Fraction(
                numerator,
                denominator,
                new Formula(BigDecimal.ONE).divide(exponent.multiply(new Formula(BigDecimal.valueOf(fraction.toDouble()))))
        );
    }

    @Override
    public int compareTo(Fraction o) {
        try {
            return Double.compare(this.toDouble(), o.toDouble());
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
