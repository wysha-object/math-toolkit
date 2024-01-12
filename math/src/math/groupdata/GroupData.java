package math.groupdata;

import math.MathGroup;
import math.MathObjects;
import math.math.math.ArithmeticOperation;
import math.math.object.Fraction;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author wysha
 */
public class GroupData extends MathObjects {
    public final Fraction[] fractions;
    public final GroupData[] groupData;
    Fraction average;
    Fraction variance;
    transient private ArrayList<Fraction> all;

    public GroupData(Fraction[] fractions, GroupData[] groupDataArray, String name, MathGroup mathGroup) throws Throwable {
        super(name);
        this.groupData = Objects.requireNonNullElseGet(groupDataArray, () -> new GroupData[]{});
        mathGroup.checkName(this);
        List<Fraction> list = Arrays.asList(fractions);
        list.sort(Fraction::isMoreThan);
        this.fractions = list.toArray(new Fraction[0]);
        mathGroup.groupData.add(this);
    }

    @Override
    public String toString() {
        flush();
        return "数据组名:" + name + "    包含的数据数量:" + all.size();
    }

    private void flush() {
        all = new ArrayList<>(Arrays.asList(fractions));
        for (GroupData g : groupData) {
            all.addAll(Arrays.asList(g.fractions));
        }
    }

    public Fraction getAverage() throws Throwable {
        flush();
        Fraction fraction = new Fraction(BigDecimal.valueOf(0), BigDecimal.valueOf(1));
        for (Fraction f : all) {
            fraction = ArithmeticOperation.operation(ArithmeticOperation.ADD, fraction, f);
        }
        fraction = new Fraction(new BigDecimal(fraction.numerator), new BigDecimal(fraction.denominator.multiply(BigInteger.valueOf(all.size()))));
        average = fraction;
        return fraction;
    }

    public Fraction getVariance() throws Throwable {
        Fraction rs = new Fraction(BigDecimal.valueOf(0), BigDecimal.valueOf(1));
        for (Fraction f : all) {
            Fraction s = ArithmeticOperation.operation(ArithmeticOperation.SUBTRACT, f, average);
            rs = ArithmeticOperation.operation(
                    ArithmeticOperation.ADD,
                    rs,
                    ArithmeticOperation.operation(ArithmeticOperation.MULTIPLY, s, s)
            );
        }
        rs = new Fraction(new BigDecimal(rs.numerator), new BigDecimal(rs.denominator.multiply(BigInteger.valueOf(all.size()))));
        variance = rs;
        return rs;
    }

    public Fraction getMedian() throws Throwable {
        flush();
        Fraction fraction;
        if (all.size() % 2 == 0) {
            fraction = ArithmeticOperation.operation(
                    ArithmeticOperation.DIVIDE,
                    ArithmeticOperation.operation(
                            ArithmeticOperation.ADD,
                            all.get((all.size() - 1) / 2 - 1),
                            all.get((all.size() + 1) / 2 - 1)
                    ),
                    new Fraction(BigDecimal.valueOf(2), BigDecimal.valueOf(1))
            );
        } else {
            fraction = all.get((all.size() + 1) / 2 - 1);
        }
        return fraction;
    }
}
