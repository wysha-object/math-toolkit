/*
 * 开发者:熊锦枫
 * 开发者邮箱:wyshazhisishen@yeah.net
 */
package math;

import java.util.*;
import java.io.Serializable;

public class Variable implements Serializable {
    public final LinkedList<Fraction> valueLL=new LinkedList<>();
    public String name;
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
    public Variable(String name){
        this.name=name;
    }
    public Variable(String name, List<Variable> variables){
        for (Variable variable : variables){
            if (variable.name.equals(name)){
                variables.add(variable);
                return;
            }
        }
        this.name = name;
        variables.add(this);
    }
}
