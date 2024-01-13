package math.math.object;

import math.Math;
import math.MathObject;
import math.function.AbstractFunction;
import math.math.math.ArithmeticOperation;
import math.math.math.Braces;
import math.math.objects.Variable;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @author wysha
 */
final public class Formula extends MathObject implements Comparable<Formula>{
    public final List<BigInteger> bigIntegers;
    public final List<Variable> variables;
    public final List<ArithmeticOperation> arithmeticOperations;
    public final List<Brace> braces;
    public Formula(){
        this(new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),false);
    }
    public Formula(List<BigInteger> bigIntegers, List<Variable> variables, List<ArithmeticOperation> arithmeticOperations, List<Brace> braces, boolean simplify) {
        List<BigInteger> numbers;
        List<ArithmeticOperation> arithmeticOperationArrayList;
        if (simplify) {
            arithmeticOperationArrayList=new ArrayList<>(arithmeticOperations);
            numbers = new ArrayList<>(bigIntegers);
            ArrayList<Boolean> booleans = new ArrayList<>();
            for (int i = 0; i < arithmeticOperationArrayList.size(); i++) {
                booleans.add(true);
            }
            for (Brace b : braces) {
                for (int k = ArithmeticOperation.MAX; k > 0; k--) {
                    for (int j = b.start; j < b.end && j < arithmeticOperationArrayList.size(); j++) {
                        ArithmeticOperation arithmeticOperation = arithmeticOperationArrayList.get(j);
                        if (arithmeticOperation.Priority == k) {
                            if (booleans.get(j)) {
                                if (booleans.size()==j+1||booleans.get(j+1)){
                                    try {
                                        numbers.set(j, ArithmeticOperation.operation(arithmeticOperation, numbers.get(j), numbers.get(j + 1)));
                                        booleans.remove(j);
                                        numbers.remove(j+1);
                                        arithmeticOperationArrayList.remove(arithmeticOperation);
                                        j--;
                                    } catch (Throwable throwable) {
                                        booleans.set(j, false);
                                    }
                                }else {
                                    booleans.set(j,false);
                                }
                            }
                        }
                    }
                }
            }
            for (int k = ArithmeticOperation.MAX; k > 0; k--) {
                for (int j = 0; j < arithmeticOperationArrayList.size();++j) {
                    ArithmeticOperation arithmeticOperation = arithmeticOperationArrayList.get(j);
                    if (arithmeticOperation.Priority == k) {
                        if (booleans.get(j)) {
                            if (booleans.size()==j+1||booleans.get(j+1)){
                                try {
                                    numbers.set(j, ArithmeticOperation.operation(arithmeticOperation, numbers.get(j), numbers.get(j + 1)));
                                    booleans.remove(j);
                                    numbers.remove(j+1);
                                    arithmeticOperationArrayList.remove(arithmeticOperation);
                                    j--;
                                } catch (Throwable throwable) {
                                    booleans.set(j, false);
                                }
                            }else {
                                booleans.set(j,false);
                            }
                        }
                    }
                }
            }
            if (arithmeticOperationArrayList.size()<=1){
                braces=new ArrayList<>();
            }
        }else {
            numbers=bigIntegers;
            arithmeticOperationArrayList=arithmeticOperations;
        }
        this.bigIntegers = numbers;
        this.variables = variables;
        this.arithmeticOperations = arithmeticOperationArrayList;
        this.braces = braces;
    }

    public static Formula valueOf(String string, boolean simplify) {
        if (string.isEmpty()){
            return new Formula();
        }
        boolean subtractB = false;
        if (Objects.equals(string.charAt(0), '-')) {
            string = string.substring(1);
            subtractB = true;
        }
        List<BigInteger> bigIntegerList = new LinkedList<>();
        List<Variable> variableArrayList = new LinkedList<>();
        List<ArithmeticOperation> arithmeticOperationArrayList = new LinkedList<>();
        List<Brace> bracesArrayList = new LinkedList<>();

        LinkedList<Integer> leftBrace = new LinkedList<>();
        LinkedList<Integer> rightBrace = new LinkedList<>();

        char[] chars = string.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();

        Enum anEnum = null;
        for (char aChar : chars) {
            Enum now;
            if (Character.isDigit(aChar)) {
                now = Enum.NUMBER;
            } else if (Character.isLetter(aChar)) {
                now = Enum.LETTER;
            } else {
                Math math = Math.valueOf(String.valueOf(aChar));
                if (math instanceof ArithmeticOperation) {
                    arithmeticOperationArrayList.add((ArithmeticOperation) math);
                } else if (math instanceof Braces) {
                    if (Objects.equals(math, Braces.LEFT)) {
                        leftBrace.add(bigIntegerList.size());
                    } else {
                        rightBrace.add(bigIntegerList.size());
                    }
                }
                now = null;
            }
            boolean b = (Objects.equals(anEnum, now) || anEnum == null) && now != null;
            if (b) {
                stringBuilder.append(aChar);
            }
            if (!b) {
                if (Objects.equals(anEnum, Enum.NUMBER)) {
                    bigIntegerList.add(new BigInteger(stringBuilder.toString()));
                } else if (Objects.equals(anEnum, Enum.LETTER)) {
                    bigIntegerList.add(null);
                    new Variable(stringBuilder.toString(), variableArrayList);
                }
                stringBuilder = new StringBuilder();
            }
            anEnum = now;
        }
        if (!stringBuilder.toString().isEmpty()) {
            if (Objects.equals(anEnum, Enum.NUMBER)) {
                bigIntegerList.add(new BigInteger(stringBuilder.toString()));
            } else {
                bigIntegerList.add(null);
                new Variable(stringBuilder.toString(), variableArrayList);
            }
        }
        int i = 0;
        if (subtractB) {
            i = 1;
            bigIntegerList.add(0, BigInteger.valueOf(0));
            arithmeticOperationArrayList.add(0, ArithmeticOperation.SUBTRACT);
        }
        for (int j = 0; j < leftBrace.size(); j++) {
            bracesArrayList.add(new Brace(leftBrace.get(j) + i, rightBrace.get(j) + i));
        }
        return new Formula(bigIntegerList, variableArrayList, arithmeticOperationArrayList, bracesArrayList,simplify);
    }

    public List<Variable> getVariables() {
        ArrayList<Variable> variableArrayList = new ArrayList<>();
        for (Variable variable : variables) {
            boolean add = true;
            for (Variable v : variableArrayList) {
                if (v.equals(variable)) {
                    add = false;
                    break;
                }
            }
            if (add) {
                variableArrayList.add(variable);
            }
        }
        return variableArrayList;
    }

    public void setValues(List<Formula> bigIntegers) throws Throwable {
        ArrayList<Variable> variableArrayList = new ArrayList<>(getVariables());
        for (int i = variableArrayList.size() - 1; i >= 0; i--) {
            Variable variable = variableArrayList.get(i);
            if (variable instanceof AbstractFunction function) {
                variableArrayList.remove(variable);
                    ArrayList<Formula> bigIntegerArrayList = new ArrayList<>();
                for (int j = 0; j < bigIntegers.size(); j++) {
                    if (j == i) {
                        for (int n = 0; n < function.formula.getVariables().size(); n++) {
                            bigIntegerArrayList.add(bigIntegers.get(j));
                            bigIntegers.remove(bigIntegers.get(j));
                        }
                        break;
                    }
                }
                function.operation(bigIntegerArrayList);
            }
        }
        for (int i = 0; i < variableArrayList.size(); i++) {
            variableArrayList.get(i).valueLL.add(0, bigIntegers.get(i));
        }
    }

    public Formula add(ArithmeticOperation arithmeticOperation,Formula formula){
        ArrayList<BigInteger> numbers=new ArrayList<>();
        numbers.addAll(this.bigIntegers);
        numbers.addAll(formula.bigIntegers);
        ArrayList<ArithmeticOperation> arithmeticOperationArrayList = new ArrayList<>(this.arithmeticOperations);
        arithmeticOperationArrayList.add(arithmeticOperation);
        arithmeticOperationArrayList.addAll(formula.arithmeticOperations);
        ArrayList<Variable> variableArrayList=new ArrayList<>();
        variableArrayList.addAll(this.variables);
        variableArrayList.addAll(formula.variables);
        ArrayList<Brace> braceArrayList=new ArrayList<>(this.braces);
        braceArrayList.add(new Brace(0,bigIntegers.size()));
        int i=this.arithmeticOperations.size();
        for (Brace brace:formula.braces){
            braceArrayList.add(new Brace(brace.start+i, brace.end+i));
        }
        braceArrayList.add(new Brace(0,i));
        braceArrayList.add(new Brace(i+1,i+formula.arithmeticOperations.size()));
        return new Formula(numbers,variableArrayList,arithmeticOperationArrayList,braceArrayList,true);
    }

    public Formula operation(List<Formula> values) throws Throwable {
        setValues(values);
        ArrayList<BigInteger> numbers = new ArrayList<>();
        int i = 0;
        for (int j = 0, bigIntegersSize = bigIntegers.size(); j < bigIntegersSize; j++) {
            BigInteger bigInteger = bigIntegers.get(j);
            if (bigInteger != null) {
                numbers.add(bigInteger);
            } else {
                Formula formula = variables.get(i).valueLL.get(0);
                numbers.add(formula.bigIntegers.get(0));
                i++;
            }
        }
        return new Formula(numbers,new ArrayList<>(),arithmeticOperations,braces,true);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        int i = 0;
        int[] l = new int[braces.size()];
        int[] r = new int[braces.size()];
        for (int j = 0; j < braces.size(); j++) {
            Brace brace = braces.get(j);
            l[j] = brace.start;
            r[j] = brace.end;
        }
        for (int j = 0; j < bigIntegers.size(); j++) {
            BigInteger fraction = bigIntegers.get(j);
            if (fraction != null) {
                s.append(fraction);
            } else {
                s.append(variables.get(i).getName());
                i++;
            }
            for (int k : r) {
                if (k == j) {
                    s.append(')');
                }
            }
            if (j < bigIntegers.size() - 1) {
                ArithmeticOperation arithmeticOperation = arithmeticOperations.get(j);
                s.append(arithmeticOperation.toString());
            }
            for (int k : l) {
                if (k == j + 1) {
                    s.append('(');
                }
            }
        }
        return String.valueOf(s);
    }

    @Override
    public int compareTo(Formula o) {
        return 0;
    }

    enum Enum {
        NUMBER, LETTER
    }
}
