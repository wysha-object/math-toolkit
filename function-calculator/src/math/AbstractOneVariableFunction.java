
package math;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wysha
 */
public abstract class AbstractOneVariableFunction extends AbstractFunction {
    public AbstractOneVariableFunction(String name) {
        super(name);
    }

    @Override
    public final List<Variable> getVariables() {
        ArrayList<Variable> variableArrayList = new ArrayList<>();
        variableArrayList.add(variables.get(0));
        return variableArrayList;
    }
}
