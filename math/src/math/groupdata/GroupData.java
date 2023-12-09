package math.groupdata;

import math.ArithmeticOperation;
import math.Fraction;
import math.MathObject;

import java.util.*;

/**
 * @author wysha
 */
public class GroupData extends MathObject {
    public final Fraction[] fractions;
    transient private ArrayList<Fraction> all;
    public final GroupData[] groupData;

    public GroupData(Fraction[] fractions, GroupData[] groupDataArray, String name, List<GroupData> groupDataList) {
        if (groupDataArray != null) {
            this.groupData = groupDataArray;
        }else {
            this.groupData = new GroupData[]{};
        }
        for (GroupData groupData : groupDataList) {
            if (name.equals(groupData.name)) {
                throw new RuntimeException("数据组名重复");
            }
        }
        this.name = name;
        List<Fraction> list=Arrays.asList(fractions);
        list.sort(Fraction::isMoreThan);
        this.fractions= list.toArray(new Fraction[0]);
        groupDataList.add(this);
    }

    @Override
    public String toString() {
        flush();
        return "数据组名:"+name+"    包含的数据数量:"+all.size();
    }
    private void flush(){
        all=new ArrayList<>(Arrays.asList(fractions));
        for (GroupData g : groupData) {
            all.addAll(Arrays.asList(g.fractions));
        }
    }
    Fraction average;
    public Fraction getAverage() throws Throwable {
        flush();
        Fraction fraction=new Fraction(0,1);
        for (Fraction f:all){
            fraction = ArithmeticOperation.operation(ArithmeticOperation.ADD, fraction, f);
        }
        fraction=new Fraction(fraction.numerator,fraction.denominator*all.size());
        average=fraction;
        return fraction;
    }
    Fraction variance;
    public Fraction getVariance() throws Throwable {
        Fraction rs=new Fraction(0,1);
        for (Fraction f:all){
            Fraction s = ArithmeticOperation.operation(ArithmeticOperation.SUBTRACT, f, average);
            rs=ArithmeticOperation.operation(
                    ArithmeticOperation.ADD,
                    rs,
                    ArithmeticOperation.operation(ArithmeticOperation.MULTIPLY, s, s)
            );
        }
        rs=new Fraction(rs.numerator,rs.denominator*all.size());
        variance=rs;
        return rs;
    }
    public Fraction getMedian() throws Throwable {
        flush();
        Fraction fraction;
        if (all.size() % 2 == 0){
            fraction=ArithmeticOperation.operation(
                    ArithmeticOperation.DIVIDE,
                    ArithmeticOperation.operation(
                            ArithmeticOperation.ADD,
                            all.get((all.size() - 1) / 2 - 1),
                            all.get((all.size() + 1) / 2 - 1)
                    ),
                    new Fraction(2, 1)
            );
        }else {
            fraction= all.get((all.size() + 1) / 2 - 1);
        }
        return fraction;
    }
}
