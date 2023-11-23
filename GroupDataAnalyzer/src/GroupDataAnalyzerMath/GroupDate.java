/*
 * 开发者:熊锦枫
 * 开发者邮箱:wyshazhisishen@yeah.net
 */

package GroupDataAnalyzerMath;

import GroupDataAnalyzerMain.GroupDataAnalyzerDate;
import math.ArithmeticOperation;
import math.Fraction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class GroupDate implements Serializable {
    public final String name;
    public final Fraction[] fractions;
    transient private ArrayList<Fraction> all;
    public final GroupDate[] groupDates;
    public GroupDate(Fraction[] fractions, GroupDate[] groupDates,String name) {
        if (groupDates!=null){
            this.groupDates=groupDates;
        }else {
            this.groupDates=new GroupDate[]{};
        }
        for (GroupDate groupDate : GroupDataAnalyzerDate.groupDataAnalyzerDate.groupDates){
            if (name.equals(groupDate.name)){
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
        GroupDataAnalyzerDate.groupDataAnalyzerDate.groupDates.add(this);
    }

    @Override
    public String toString() {
        flush();
        return "数据组名:"+name+"    包含的数据数量:"+all.size();
    }
    private void flush(){
        all=new ArrayList<>(Arrays.asList(fractions));
        for (GroupDate g:groupDates){
            all.addAll(Arrays.asList(g.fractions));
        }
    }
    public Fraction getAverage() throws Throwable {
        flush();
        Fraction fraction=new Fraction(0,1);
        for (Fraction f:all){
            fraction= ArithmeticOperation.operation(ArithmeticOperation.add,fraction,f);
        }
        fraction=new Fraction(fraction.numerator,fraction.denominator*all.size());
        return fraction;
    }
    public Fraction getVariance() throws Throwable {
        Fraction fraction=getAverage();
        Fraction rs=new Fraction(0,1);
        for (Fraction f:all){
            Fraction s=ArithmeticOperation.operation(ArithmeticOperation.subtract,f,fraction);
            rs=ArithmeticOperation.operation(
                    ArithmeticOperation.add,
                    rs,
                    ArithmeticOperation.operation(ArithmeticOperation.multiply,s,s)
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
                    ArithmeticOperation.divide,
                    ArithmeticOperation.operation(
                            ArithmeticOperation.add,
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
