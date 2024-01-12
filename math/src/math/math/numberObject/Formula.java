package math.math.numberObject;

import math.Math;
import math.MathNumberObject;
import math.function.AbstractFunction;
import math.math.math.ArithmeticOperation;
import math.math.math.Braces;
import math.math.math.Operation;
import math.math.object.Brace;
import math.math.objects.Variable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

final public class Formula extends MathNumberObject<Formula> implements Operation<Formula> {
    public final List<BigInteger> values;
    public final List<Variable> variables;
    public final List<ArithmeticOperation> arithmeticOperations;
    public final List<Brace> braces;

    public Formula(BigDecimal bigDecimal) {
        this(
                Arrays.asList(
                        new BigInteger(bigDecimal.toString().replace(".", "")),
                        new BigInteger("1" + "0".repeat(bigDecimal.scale()))
                ),
                new LinkedList<>(),
                Collections.singletonList(
                        ArithmeticOperation.DIVIDE
                ),
                new LinkedList<>()
        );
    }

    public Formula(BigInteger bigInteger) {
        this(Collections.singletonList(bigInteger), new LinkedList<>(), new LinkedList<>(), new LinkedList<>());
    }

    public Formula(List<BigInteger> values, List<Variable> variables, List<ArithmeticOperation> arithmeticOperations, List<Brace> braces) {
        this.values = values;
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
                        rightBrace.add(bigIntegerList.size() - 1);
                    }
                }
                now = null;
            }
            boolean b = Objects.equals(anEnum, now) || anEnum == null;
            if (b) {
                stringBuilder.append(aChar);
            }
            if (!b) {
                if (Objects.equals(anEnum, Enum.NUMBER)) {
                    bigIntegerList.add(new BigInteger(stringBuilder.toString()));
                } else {
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
        List<Variable> variableArrayList = new LinkedList<>();
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
        List<Variable> variableArrayList = new LinkedList<>(getVariables());
        for (int i = variableArrayList.size() - 1; i >= 0; i--) {
            Variable variable = variableArrayList.get(i);
            if (variable instanceof AbstractFunction function) {
                variableArrayList.remove(variable);
                List<Fraction> fractionArrayList = new LinkedList<>();
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

    public Fraction operation() throws Throwable {
        if (values.size() == 1) {
            return new Fraction(values.get(0));
        }
        List<Fraction> numbers = new LinkedList<>();
        int i = 0;
        for (BigInteger bigInteger : values) {
            if (bigInteger != null) {
                numbers.add(new Fraction(bigInteger));
            } else {
                numbers.add(variables.get(i).valueLL.get(0));
                i++;
            }
        }
        List<ArithmeticOperation> arithmeticOperationArrayList = new LinkedList<>(arithmeticOperations);

        for (Brace brace : braces) {
            for (int k = ArithmeticOperation.MAX; k > 0; k--) {
                for (int j = brace.start; j < brace.end; j++) {
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
        numbers.set(0, numbers.get(0));
        return numbers.get(0);
    }

    public Fraction operation(List<Fraction> values) throws Throwable {
        setValues(values);
        return operation();
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        int i = 0;
        for (int j = 0; j < values.size(); j++) {
            BigInteger fraction = values.get(j);
            if (fraction != null) {
                s.append(fraction);
            } else {
                s.append(variables.get(i).getName());
                i++;
            }
            if (j < values.size() - 1) {
                ArithmeticOperation arithmeticOperation = arithmeticOperations.get(j);
                s.append(arithmeticOperation.toString());
            }
        }
        return String.valueOf(s);
    }

    @Override
    public Formula add(Formula formula) throws Throwable {
        List<BigInteger> values = new LinkedList<>(this.values);
        List<Variable> variables = new LinkedList<>(this.variables);
        List<ArithmeticOperation> arithmeticOperations = new LinkedList<>(this.arithmeticOperations);
        List<Brace> braces = new LinkedList<>(this.braces);
        values.addAll(formula.values);
        variables.addAll(formula.variables);
        arithmeticOperations.add(ArithmeticOperation.ADD);
        arithmeticOperations.addAll(formula.arithmeticOperations);
        int l = this.values.size();
        for (Brace brace : formula.braces) {
            braces.add(new Brace(brace.start + l, brace.end + l));
        }
        braces.add(new Brace(this.values.size(), values.size() - 1));
        return new Formula(values, variables, arithmeticOperations, braces);
    }

    @Override
    public Formula subtract(Formula formula) throws Throwable {
        List<BigInteger> values = new LinkedList<>(this.values);
        List<Variable> variables = new LinkedList<>(this.variables);
        List<ArithmeticOperation> arithmeticOperations = new LinkedList<>(this.arithmeticOperations);
        List<Brace> braces = new LinkedList<>(this.braces);
        values.addAll(formula.values);
        variables.addAll(formula.variables);
        arithmeticOperations.add(ArithmeticOperation.SUBTRACT);
        arithmeticOperations.addAll(formula.arithmeticOperations);
        int l = this.values.size();
        for (Brace brace : formula.braces) {
            braces.add(new Brace(brace.start + l, brace.end + l));
        }
        braces.add(new Brace(this.values.size(), values.size() - 1));
        return new Formula(values, variables, arithmeticOperations, braces);
    }

    @Override
    public Formula multiply(Formula formula) throws Throwable {
        List<BigInteger> values = new LinkedList<>(this.values);
        List<Variable> variables = new LinkedList<>(this.variables);
        List<ArithmeticOperation> arithmeticOperations = new LinkedList<>(this.arithmeticOperations);
        List<Brace> braces = new LinkedList<>(this.braces);
        values.addAll(formula.values);
        variables.addAll(formula.variables);
        arithmeticOperations.add(ArithmeticOperation.MULTIPLY);
        arithmeticOperations.addAll(formula.arithmeticOperations);
        int l = this.values.size();
        for (Brace brace : formula.braces) {
            braces.add(new Brace(brace.start + l, brace.end + l));
        }
        braces.add(new Brace(this.values.size(), values.size() - 1));
        return new Formula(values, variables, arithmeticOperations, braces);
    }

    @Override
    public Formula divide(Formula formula) throws Throwable {
        List<BigInteger> values = new LinkedList<>(this.values);
        List<Variable> variables = new LinkedList<>(this.variables);
        List<ArithmeticOperation> arithmeticOperations = new LinkedList<>(this.arithmeticOperations);
        List<Brace> braces = new LinkedList<>(this.braces);
        values.addAll(formula.values);
        variables.addAll(formula.variables);
        arithmeticOperations.add(ArithmeticOperation.DIVIDE);
        arithmeticOperations.addAll(formula.arithmeticOperations);
        int l = this.values.size();
        for (Brace brace : formula.braces) {
            braces.add(new Brace(brace.start + l, brace.end + l));
        }
        braces.add(new Brace(this.values.size(), values.size() - 1));
        return new Formula(values, variables, arithmeticOperations, braces);
    }

    @Override
    public Formula powered(Formula formula) throws Throwable {
        List<BigInteger> values = new LinkedList<>(this.values);
        List<Variable> variables = new LinkedList<>(this.variables);
        List<ArithmeticOperation> arithmeticOperations = new LinkedList<>(this.arithmeticOperations);
        List<Brace> braces = new LinkedList<>(this.braces);
        values.addAll(formula.values);
        variables.addAll(formula.variables);
        arithmeticOperations.add(ArithmeticOperation.POWERED);
        arithmeticOperations.addAll(formula.arithmeticOperations);
        int l = this.values.size();
        for (Brace brace : formula.braces) {
            braces.add(new Brace(brace.start + l, brace.end + l));
        }
        braces.add(new Brace(this.values.size(), values.size() - 1));
        return new Formula(values, variables, arithmeticOperations, braces);
    }

    @Override
    public Formula rooting(Formula formula) throws Throwable {
        List<BigInteger> values = new LinkedList<>(this.values);
        List<Variable> variables = new LinkedList<>(this.variables);
        List<ArithmeticOperation> arithmeticOperations = new LinkedList<>(this.arithmeticOperations);
        List<Brace> braces = new LinkedList<>(this.braces);
        values.addAll(formula.values);
        variables.addAll(formula.variables);
        arithmeticOperations.add(ArithmeticOperation.ROOTING);
        arithmeticOperations.addAll(formula.arithmeticOperations);
        int l = this.values.size();
        for (Brace brace : formula.braces) {
            braces.add(new Brace(brace.start + l, brace.end + l));
        }
        braces.add(new Brace(this.values.size(), values.size() - 1));
        return new Formula(values, variables, arithmeticOperations, braces);
    }

    enum Enum {
        NUMBER, LETTER
    }
}
