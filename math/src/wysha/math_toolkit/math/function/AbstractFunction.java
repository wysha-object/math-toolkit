package wysha.math_toolkit.math.function;

import wysha.math_toolkit.math.MathGroup;
import wysha.math_toolkit.math.function.multivariate.MultivariateFunction;
import wysha.math_toolkit.math.math.object.Formula;
import wysha.math_toolkit.math.math.objects.Variable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author wysha
 */
public abstract class AbstractFunction extends Variable {
    public final Formula formula;
    public final LinkedList<String[]> TheValuesBroughtIn = new LinkedList<>();

    protected AbstractFunction(
            String name,
            Formula formula,
            MathGroup mathGroup//所属数学组
    ) throws Throwable {
        super(name);
        this.formula = formula;
        mathGroup.checkName(this);
        mathGroup.functions.add(this);
    }

    protected AbstractFunction(String name, MathGroup mathGroup) throws Throwable {
        this(name, new Formula(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), false), mathGroup);
    }

    public List<Variable> getVariables() {
        return formula.getVariables();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(getName());
        s.append("=");
        s.append(formula.toString());
        if (!valueLL.isEmpty() && valueLL.get(0) != null) {
            if (TheValuesBroughtIn.get(0).length != 0) {
                s.append("          最近一次计算带入的值:").append(Arrays.toString(TheValuesBroughtIn.get(0)));
            }
            s.append("          最近一次计算获取的值:").append(valueLL.get(0));
        }
        return String.valueOf(s);
    }

    @Override
    public String getName() {
        ArrayList<Variable> variableArrayList = new ArrayList<>(formula.getVariables());
        StringBuilder s = new StringBuilder(name);
        if (!variableArrayList.isEmpty()) {
            s.append("(");
            for (int i = 0; i < variableArrayList.size(); i++) {
                Variable variable = variableArrayList.get(i);
                s.append(variable.getName()).append(i != variableArrayList.size() - 1 ? ',' : "");
            }
            s.append(")");
        }
        return String.valueOf(s);
    }

    public BigDecimal forcedCalculations(List<Formula> values) throws Throwable {
        BigDecimal rs = formula.forcedCalculations(values);
        Formula[] s = new Formula[values.size()];
        for (int j = 0; j < values.size(); j++) {
            s[j] = values.get(j);
        }
        valueLL.add(0, String.valueOf(rs));
        String[] ss = new String[s.length];
        for (int i = 0; i < s.length; i++) {
            Formula f = s[i];
            ss[i] = f.toString();
        }
        TheValuesBroughtIn.add(0, ss);
        return rs;
    }

    public Formula operation(List<Formula> values) throws Throwable {
        Formula rs = formula.operation(values);
        Formula[] s = new Formula[values.size()];
        for (int j = 0; j < values.size(); j++) {
            s[j] = values.get(j);
        }
        valueLL.add(0, rs.toString());
        String[] ss = new String[s.length];
        for (int i = 0; i < s.length; i++) {
            Formula f = s[i];
            ss[i] = f.toString();
        }
        TheValuesBroughtIn.add(0, ss);
        return rs;
    }

    public List<AbstractFunction> getFunctions() {
        ArrayList<AbstractFunction> abstractFunctionArrayList = new ArrayList<>();
        for (Variable variable : formula.getVariables()) {
            if (variable instanceof MultivariateFunction) {
                abstractFunctionArrayList.add((MultivariateFunction) variable);
            }
        }
        return abstractFunctionArrayList;
    }
}
