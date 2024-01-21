package wysha.math_toolkit.math;

import wysha.math_toolkit.math.equation.AbstractEquation;
import wysha.math_toolkit.math.function.AbstractFunction;
import wysha.math_toolkit.math.groupdata.GroupData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author wysha
 */
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
