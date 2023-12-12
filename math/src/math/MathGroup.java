package math;

import math.equation.AbstractEquation;
import math.function.AbstractFunction;
import math.groupdata.GroupData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MathGroup {
    public final List<AbstractFunction> functions;
    public final List<AbstractEquation> equations;
    public final List<GroupData> groupData;

    public MathGroup(List<AbstractFunction> functions, List<AbstractEquation> equations, List<GroupData> groupData) {
        this.functions = functions;
        this.equations = equations;
        this.groupData = groupData;
    }

    public MathGroup() {
        this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    /**
     * @return "true"则合规 "false"则不合规
     */
    public boolean checkName(MathObjects mathObject) throws Throwable {
        ArrayList<MathObjects> mathObjects = new ArrayList<>();
        mathObjects.addAll(functions);
        mathObjects.addAll(equations);
        mathObjects.addAll(groupData);
        for (MathObjects mo : mathObjects) {
            if (!Objects.equals(mo.name, mathObject.name)) {
                return false;
            }
        }
        return true;
    }
}
