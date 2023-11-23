/*
 * 开发者:熊锦枫
 * 开发者邮箱:wyshazhisishen@yeah.net
 */

package FunctionCalulatorMath;

import FunctionCalulatorMain.FunctionCalculatorDate;
import math.ArithmeticOperation;
import math.Fraction;
import math.Variable;

import java.util.*;

public abstract class Function extends Variable {
    protected Function(String name, List<Fraction> fractions, List<Variable> variables, List<ArithmeticOperation> arithmeticOperations) {
        super(name);
        for (Function f: FunctionCalculatorDate.functionCalculatorDate.functions){
            if (f.name.equals(name)){
                throw new RuntimeException("函数名重复");
            }
        }
        this.fractions=fractions;
        this.variables=variables;
        this.arithmeticOperations=arithmeticOperations;
        FunctionCalculatorDate.functionCalculatorDate.functions.add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Function function = (Function) o;
        return Objects.equals(TheValuesBroughtIn, function.TheValuesBroughtIn) && Objects.equals(fractions, function.fractions) && Objects.equals(variables, function.variables) && Objects.equals(arithmeticOperations, function.arithmeticOperations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(TheValuesBroughtIn, fractions, variables, arithmeticOperations);
    }

    protected Function(String name){
        this(name,new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
    }
    public final LinkedList<Fraction[]> TheValuesBroughtIn =new LinkedList<>();
    public final List<Fraction> fractions;
    public final List<Variable> variables;
    public final List<ArithmeticOperation> arithmeticOperations;
    @Override
    public String toString() {
        StringBuilder s= new StringBuilder(getName());
        s.append("=");
        s.append(halfToString());
        if (!valueLL.isEmpty() && valueLL.get(0)!=null){
            if (TheValuesBroughtIn.get(0).length!=0){
                s.append("          最近一次计算带入的值:").append(Arrays.toString(TheValuesBroughtIn.get(0)));
            }
            s.append("          最近一次计算获取的值:").append(valueLL.get(0));
        }
        return String.valueOf(s);
    }
    @Override
    public String getName() {
        StringBuilder s= new StringBuilder(name + "(");
        ArrayList<Variable> variableArrayList = new ArrayList<>(getVariables());
        for (int i = 0; i < variableArrayList.size(); i++) {
            Variable variable = variableArrayList.get(i);
            s.append(variable.getName()).append(i!=variableArrayList.size()-1?',':"");
        }
        s.append(")");
        return String.valueOf(s);
    }
    public List<Variable> getVariables(){
        ArrayList<Variable> variableArrayList=new ArrayList<>();
        for (Variable variable:variables){
            boolean add=true;
            for (Variable v:variableArrayList){
                if (v.equals(variable)) {
                    add = false;
                    break;
                }
            }
            if (add){
                variableArrayList.add(variable);
            }
        }
        return variableArrayList;
    }
    public void setValues(List<Fraction> fractions) throws Throwable {
        ArrayList<Variable> variableArrayList = new java.util.ArrayList<>(getVariables());
        for (int i = variableArrayList.size()-1; i >= 0; i--) {
            Variable variable = variableArrayList.get(i);
            if (variable instanceof MultivariateFunction) {
                MultivariateFunction function = (MultivariateFunction) variable;
                variableArrayList.remove(variable);
                ArrayList<Fraction> fractionArrayList=new ArrayList<>();
                for (int j = 0; j < fractions.size(); j++) {
                    if (j==i){
                        for (int n=0;n<function.getVariables().size();n++){
                            fractionArrayList.add(fractions.get(j));
                            fractions.remove(fractions.get(j));
                        }
                        break;
                    }
                }
                function.operation(fractionArrayList);
            }
        }
        for (int i = 0;i<variableArrayList.size();i++) {
            variableArrayList.get(i).valueLL.add(0, fractions.get(i));
        }
    }
    public Fraction operation(List<Fraction> values) throws Throwable {
        java.util.ArrayList<Fraction> arrayList = new java.util.ArrayList<>(values);
        setValues(values);
        ArrayList<Fraction> numbers=new ArrayList<>();
        int i=0;
        for (Fraction bigDecimal : fractions){
            if (bigDecimal!=null){
                numbers.add(bigDecimal);
            }else {
                numbers.add(variables.get(i).valueLL.get(0));
                i++;
            }
        }
        ArrayList<ArithmeticOperation> arithmeticOperationAL = new ArrayList<>(arithmeticOperations);

        for (int k = ArithmeticOperation.max; k>0; k--){
            for (int j = 0; j < arithmeticOperationAL.size(); j++) {
                ArithmeticOperation arithmeticOperation = arithmeticOperationAL.get(j);
                if (arithmeticOperation.Priority == k) {
                    numbers.set(j, ArithmeticOperation.operation(arithmeticOperation, numbers.get(j), numbers.get(j + 1)));
                    numbers.remove(j + 1);
                    arithmeticOperationAL.remove(arithmeticOperation);
                    j--;
                }
            }
        }
        numbers.set(0,new Fraction(numbers.get(0).numerator,numbers.get(0).denominator));
        Fraction[] s=new Fraction[arrayList.size()];
        for (int j = 0; j < arrayList.size(); j++) {
            s[j]=arrayList.get(j);
        }
        valueLL.add(0,numbers.get(0));
        TheValuesBroughtIn.add(0,s);
        return numbers.get(0);
    }
    public List<Function> getFunctions(){
        ArrayList<Function> functionArrayList=new ArrayList<>();
        for (Variable variable:getVariables()){
            if (variable instanceof MultivariateFunction){
                functionArrayList.add((MultivariateFunction) variable);
            }
        }
        return functionArrayList;
    }
    public String halfToString() {
        StringBuilder s= new StringBuilder();
        int i=0;
        for (int j = 0; j < fractions.size(); j++) {
            Fraction fraction = fractions.get(j);
            if (fraction != null) {
                s.append(fraction);
            } else {
                s.append(variables.get(i).getName());
                i++;
            }
            if (j < fractions.size() - 1) {
                ArithmeticOperation arithmeticOperation=arithmeticOperations.get(j);
                s.append(arithmeticOperation.toString());
            }
        }
        return String.valueOf(s);
    }
}
