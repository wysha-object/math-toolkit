
package math;

import main.GroupDataAnalyzerData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author wysha
 */
public class GroupData implements Serializable {
    public final String name;
    public final Fraction[] fractions;
    transient private ArrayList<Fraction> all;
    public final GroupData[] groupData;

    public GroupData(Fraction[] fractions, GroupData[] groupDataArray, String name) {
        if (groupDataArray != null) {
            this.groupData = groupDataArray;
        }else {
            this.groupData = new GroupData[]{};
        }
        for (GroupData groupData : GroupDataAnalyzerData.groupDataAnalyzerDate.groupData) {
            if (name.equals(groupData.name)) {
                throw new RuntimeException("数据组名重复");
            }
        }
        Fraction[] array=new Fraction[fractions.length];
        for (int i = 0; i < array.length; i++) {
            Fraction f=fractions[0];
            int n=i;
            for (int j = 0; j < fractions.length; j++) {
                Fraction value = fractions[j];
                if (value != null) {
                    f = value;
                    n=j;
                    break;
                }
            }
            for (int j = 0; j < fractions.length; j++) {
                Fraction fraction = fractions[j];
                if (f != null && fraction != null && f.isMoreThan(fraction)) {
                    f = fraction;
                    n = j;
                }
            }
            fractions[n]=null;
            array[i]=f;
        }
        this.name = name;
        this.fractions=array;
        GroupDataAnalyzerData.groupDataAnalyzerDate.groupData.add(this);
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
    public Fraction getAverage() throws Throwable {
        flush();
        Fraction fraction=new Fraction(0,1);
        for (Fraction f:all){
            fraction = ArithmeticOperation.operation(ArithmeticOperation.ADD, fraction, f);
        }
        fraction=new Fraction(fraction.numerator,fraction.denominator*all.size());
        return fraction;
    }
    public Fraction getVariance() throws Throwable {
        Fraction fraction=getAverage();
        Fraction rs=new Fraction(0,1);
        for (Fraction f:all){
            Fraction s = ArithmeticOperation.operation(ArithmeticOperation.SUBTRACT, f, fraction);
            rs=ArithmeticOperation.operation(
                    ArithmeticOperation.ADD,
                    rs,
                    ArithmeticOperation.operation(ArithmeticOperation.MULTIPLY, s, s)
            );
        }
        rs=new Fraction(rs.numerator,rs.denominator*all.size());
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
