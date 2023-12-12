package math.math.objects;

import math.MathObjects;
import math.math.object.Fraction;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @author wysha
 */
public class Variable extends MathObjects {
    public final LinkedList<Fraction> valueLL=new LinkedList<>();
    public String getName() {
        return name;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Variable variable = (Variable) o;
        return Objects.equals(name, variable.name);
    }

    protected Variable(String name) {
        super(name);
    }

    public Variable(
            String name,
            List<Variable> variables//所属变量组
    ) {
        super(name);
        for (Variable variable : variables){
            if (variable.name.equals(name)){
                variables.add(variable);
                return;
            }
        }
        variables.add(this);
    }
}
