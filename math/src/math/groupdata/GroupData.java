package math.groupdata;

import math.MathGroup;
import math.MathObjects;
import math.math.math.ArithmeticOperation;
import math.math.object.Formula;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author wysha
 */
public class GroupData extends MathObjects {
    public final Formula[] fractions;
    public final GroupData[] groupData;
    public final Formula sum;
    public final Formula average;
    public final Formula variance;
    public final Formula median;
    public final ArrayList<Formula> all;

    public GroupData(Formula[] formulas, GroupData[] groupDataArray, String name, MathGroup mathGroup) throws Throwable {
        super(name);
        this.groupData = Objects.requireNonNullElseGet(groupDataArray, () -> new GroupData[]{});
        mathGroup.checkName(this);
        List<Formula> list = Arrays.asList(formulas);
        list.sort(Formula::compareTo);
        this.fractions = list.toArray(new Formula[0]);

        all = new ArrayList<>(Arrays.asList(formulas));
        for (GroupData g : groupData) {
            all.addAll(Arrays.asList(g.fractions));
        }

        Formula formula = new Formula(new ArrayList<>(List.of(BigInteger.ZERO)), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), false);
        for (Formula f : all) {
            formula = formula.add(ArithmeticOperation.ADD, f);
        }
        sum = formula.operation(new ArrayList<>());

        average = sum.add(ArithmeticOperation.DIVIDE, new Formula(List.of(BigInteger.valueOf(all.size())), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), false));

        Formula rs = new Formula(new ArrayList<>(List.of(BigInteger.ZERO)), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), false);
        for (Formula f : all) {
            Formula s = f.add(ArithmeticOperation.SUBTRACT, average);
            rs = rs.add(ArithmeticOperation.ADD, s.add(ArithmeticOperation.MULTIPLY, s));
        }
        rs = rs.add(ArithmeticOperation.DIVIDE, new Formula(List.of(BigInteger.valueOf(all.size())), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), false));
        variance = rs;

        if (all.size() % 2 == 0) {
            int s = all.size() / 2;
            median = all.get(s).add(ArithmeticOperation.ADD, all.get(s - 1)).add(
                    ArithmeticOperation.DIVIDE,
                    new Formula(List.of(BigInteger.valueOf(2)), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), false)
            );
        } else {
            median = all.get((all.size() - 1) / 2);
        }

        mathGroup.groupData.add(this);
    }

    @Override
    public String toString() {
        return "数据组名:" + name + "    包含的数据数量:" + all.size();
    }
}
