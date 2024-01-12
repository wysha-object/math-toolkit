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
final public class Formula extends MathObject {
    public final List<BigInteger> bigIntegers;
    public final List<Variable> variables;
    public final List<ArithmeticOperation> arithmeticOperations;
    public final List<Brace> braces;

    public Formula(List<BigInteger> bigIntegers, List<Variable> variables, List<ArithmeticOperation> arithmeticOperations, List<Brace> braces) {
        this.bigIntegers = bigIntegers;
        this.variables = variables;
        this.arithmeticOperations = arithmeticOperations;
        this.braces = braces;
    }

    public static Formula valueOf(String string) throws Throwable {
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
        return new Formula(bigIntegerList, variableArrayList, arithmeticOperationArrayList, bracesArrayList);
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

    public void setValues(List<Fraction> fractions) throws Throwable {
        ArrayList<Variable> variableArrayList = new ArrayList<>(getVariables());
        for (int i = variableArrayList.size() - 1; i >= 0; i--) {
            Variable variable = variableArrayList.get(i);
            if (variable instanceof AbstractFunction function) {
                variableArrayList.remove(variable);
                ArrayList<Fraction> fractionArrayList = new ArrayList<>();
                for (int j = 0; j < fractions.size(); j++) {
                    if (j == i) {
                        for (int n = 0; n < function.formula.getVariables().size(); n++) {
                            fractionArrayList.add(fractions.get(j));
                            fractions.remove(fractions.get(j));
                        }
                        break;
                    }
                }
                function.operation(fractionArrayList);
            }
        }
        for (int i = 0; i < variableArrayList.size(); i++) {
            variableArrayList.get(i).valueLL.add(0, fractions.get(i));
        }
    }

    public Fraction operation(List<Fraction> values) throws Throwable {
        setValues(values);
        ArrayList<Fraction> numbers = new ArrayList<>();

        int i = 0;
        for (BigInteger bigInteger : bigIntegers) {
            if (bigInteger != null) {
                numbers.add(new Fraction(bigInteger, BigInteger.valueOf(1)));
            } else {
                numbers.add(variables.get(i).valueLL.get(0));
                i++;
            }
        }
        ArrayList<ArithmeticOperation> arithmeticOperationArrayList = new ArrayList<>(arithmeticOperations);

        for (Brace b : braces) {
            for (int k = ArithmeticOperation.MAX; k > 0; k--) {
                for (int j = b.start; j < b.end && j < arithmeticOperationArrayList.size(); j++) {
                    ArithmeticOperation arithmeticOperation = arithmeticOperationArrayList.get(j);
                    if (arithmeticOperation.Priority == k) {
                        numbers.set(j, ArithmeticOperation.operation(arithmeticOperation, numbers.get(j), numbers.get(j + 1)));
                        numbers.remove(j + 1);
                        arithmeticOperationArrayList.remove(arithmeticOperation);
                        j--;
                    }
                }
            }
        }
        for (int k = ArithmeticOperation.MAX; k > 0; k--) {
            for (int j = 0; j < arithmeticOperationArrayList.size(); j++) {
                ArithmeticOperation arithmeticOperation = arithmeticOperationArrayList.get(j);
                if (arithmeticOperation.Priority == k) {
                    numbers.set(j, ArithmeticOperation.operation(arithmeticOperation, numbers.get(j), numbers.get(j + 1)));
                    numbers.remove(j + 1);
                    arithmeticOperationArrayList.remove(arithmeticOperation);
                    j--;
                }
            }
        }
        numbers.set(0, new Fraction(numbers.get(0).numerator, numbers.get(0).denominator));
        return numbers.get(0);
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

    enum Enum {
        NUMBER, LETTER
    }
}
