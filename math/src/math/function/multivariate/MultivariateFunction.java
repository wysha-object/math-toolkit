package math.function.multivariate;

import math.ArithmeticOperation;
import math.Fraction;
import math.Variable;
import math.function.AbstractFunction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author wysha
 */
public class MultivariateFunction extends AbstractFunction implements Serializable {
    public static void valueOf(String function,List<AbstractFunction> functions) throws Throwable {
        String[] str=function.split("=");
        boolean subtractB=false;
        if (Objects.equals(str[1].charAt(0),'-')){
            str[1]=str[1].substring(1);
            subtractB=true;
        }
        ArrayList<String> numbers=new ArrayList<>();
        ArrayList<Fraction> fractionArrayList = new ArrayList<>();
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
                                    arithmeticOperationArrayList.add(ArithmeticOperation.ADD);
                                    break;
                                }
                                arithmeticOperationArrayList.add(ArithmeticOperation.SUBTRACT);
                                break;
                            }
                            arithmeticOperationArrayList.add(ArithmeticOperation.MULTIPLY);
                            break;
                        }
                        arithmeticOperationArrayList.add(ArithmeticOperation.DIVIDE);
                    }
                }
            }
        }
        for (String s:numbers){
            try {
                Fraction fraction= new Fraction(Double.parseDouble(s),1);
                fractionArrayList.add(fraction);
            }catch (Throwable e){
                fractionArrayList.add(null);
                boolean b=true;
                for (AbstractFunction f : functions) {
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
            fractionArrayList.add(0, new Fraction(0, 1));
            arithmeticOperationArrayList.add(0, ArithmeticOperation.SUBTRACT);
        }
        new MultivariateFunction(str[0], fractionArrayList, variableArrayList, arithmeticOperationArrayList,functions);
    }

    public MultivariateFunction(String name, ArrayList<Fraction> fractions, ArrayList<Variable> variables, ArrayList<ArithmeticOperation> arithmeticOperations, List<AbstractFunction> functions){
        super(name,fractions,variables,arithmeticOperations,functions);
    }
}