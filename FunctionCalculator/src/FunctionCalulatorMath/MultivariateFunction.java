/*
 * 开发者:熊锦枫
 * 开发者邮箱:wyshazhisishen@yeah.net
 */
package FunctionCalulatorMath;

import FunctionCalulatorMain.FunctionCalculatorDate;
import math.ArithmeticOperation;
import math.Fraction;
import math.Variable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class MultivariateFunction extends Function implements Serializable {
    public static void valueOf(String function) throws Throwable {
        String[] str=function.split("=");
        boolean subtractB=false;
        if (Objects.equals(str[1].charAt(0),'-')){
            str[1]=str[1].substring(1);
            subtractB=true;
        }
        ArrayList<String> numbers=new ArrayList<>();
        ArrayList<Fraction> fractionAL=new ArrayList<>();
        ArrayList<Variable> variableArrayList=new ArrayList<>();
        ArrayList<ArithmeticOperation> arithmeticOperationArrayList=new ArrayList<>();
        String[] add;
        try {
            add =str[1].split("\\+");
        }catch (Exception ignored){
            add = new String[]{str[1]};
        }
        for (int i = 0; i < add.length; i++) {
            String s = add[i];
            String[] subtract = s.split("-");
            for (int k = 0; k < subtract.length; k++) {
                String m = subtract[k];
                String[] multiply;
                try {
                    multiply = m.split("\\*");
                }catch (Exception ignored){
                    multiply = new String[]{m};
                }
                for (int j = 0; j < multiply.length; j++) {
                    String d = multiply[j];
                    String[] divide;
                    try {
                        divide = d.split("/");
                    }catch (Exception ignored){
                        divide = new String[]{d};
                    }
                    for (int l = 0; l < divide.length; l++) {
                        String string = divide[l];
                        numbers.add(string);
                        if (l == divide.length - 1){
                            if (j == multiply.length - 1){
                                if (k == subtract.length - 1){
                                    if (i == add.length - 1){
                                        break;
                                    }
                                    arithmeticOperationArrayList.add(ArithmeticOperation.add);
                                    break;
                                }
                                arithmeticOperationArrayList.add(ArithmeticOperation.subtract);
                                break;
                            }
                            arithmeticOperationArrayList.add(ArithmeticOperation.multiply);
                            break;
                        }
                        arithmeticOperationArrayList.add(ArithmeticOperation.divide);
                    }
                }
            }
        }
        for (String s:numbers){
            try {
                Fraction fraction= new Fraction(Double.parseDouble(s),1);
                fractionAL.add(fraction);
            }catch (Throwable e){
                fractionAL.add(null);
                boolean b=true;
                for (Function f: FunctionCalculatorDate.functionCalculatorDate.functions){
                    if (f.name.equals(s)){
                        variableArrayList.add(f);
                        b=false;
                    }
                }
                if (b){
                    new Variable(s,variableArrayList);
                }
            }
        }
        if (subtractB){
            fractionAL.add(0,new Fraction(0,1));
            arithmeticOperationArrayList.add(0,ArithmeticOperation.subtract);
        }
        new MultivariateFunction(str[0], fractionAL, variableArrayList, arithmeticOperationArrayList);
    }

    public MultivariateFunction(String name, ArrayList<Fraction> fractions, ArrayList<Variable> variables, ArrayList<ArithmeticOperation> arithmeticOperations){
        super(name,fractions,variables,arithmeticOperations);
    }
}