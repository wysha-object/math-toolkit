/*
 * 开发者:熊锦枫
 * 开发者邮箱:wyshazhisishen@yeah.net
 */
package FunctionCalulatorMath;

import math.Variable;

import java.util.ArrayList;
import java.util.List;

public abstract class OneVariable_Function extends Function{
    public OneVariable_Function(String name) {
        super(name);
    }

    @Override
    public final List<Variable> getVariables() {
        ArrayList<Variable> variableArrayList = new ArrayList<>();
        variableArrayList.add(variables.get(0));
        return variableArrayList;
    }
}
