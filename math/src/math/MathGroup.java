package math;

import math.equation.AbstractEquation;
import math.function.AbstractFunction;
import math.groupdata.GroupData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MathGroup extends MathObjects {
    public final List<AbstractFunction> functions;
    public final List<AbstractEquation> equations;
    public final List<GroupData> groupData;

    public MathGroup(String name, List<AbstractFunction> functions, List<AbstractEquation> equations, List<GroupData> groupData) {
        super(name);
        this.functions = functions;
        this.equations = equations;
        this.groupData = groupData;
    }

    public void checkName(MathObjects mathObject) throws Throwable {
        ArrayList<MathObjects> mathObjects = new ArrayList<>();
        mathObjects.addAll(functions);
        mathObjects.addAll(equations);
        mathObjects.addAll(groupData);
        for (MathObjects mo : mathObjects) {
            if (Objects.equals(mo.name, mathObject.name)) {
                throw new Throwable(mo.getClass() + "\t" + mo + "," + mathObject.getClass() + "\t" + mathObject + "重名");
            }
        }
    }
}
