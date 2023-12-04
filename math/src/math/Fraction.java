package math;

import java.io.Serializable;

/**
 * @author wysha
 */
public class Fraction implements Serializable {
    public final int numerator;
    public final int denominator;
    public Fraction(double numerator, double denominator) throws Throwable{
        if (denominator==0){
            if (numerator==0){
                throw new Exception("结果未定义");
            }
            throw new Exception("分母不能为0");
        }
        double rs= Math.gcd(numerator,denominator);
        numerator=numerator/rs;
        denominator=denominator/rs;
        this.numerator= (int) numerator;
        this.denominator= (int) denominator;
    }
    public boolean isMoreThan(Fraction f){
        return  this.numerator*f.denominator>this.denominator*f.numerator;
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
        if (denominator!=1){
            return numerator+"/"+denominator;
        }else {
            return String.valueOf(numerator);
        }
    }
}
