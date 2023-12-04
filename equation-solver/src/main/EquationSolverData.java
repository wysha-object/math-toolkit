
package main;

import data.AbstractWrittenData;
import math.equation.AbstractEquation;
import math.equation.multivariate.MultivariateEquation;
import math.equation.oneVariable.OneVariableOneDegreeEquation;

import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wysha
 */
public class EquationSolverData extends AbstractWrittenData {
    public static EquationSolverData equationSolverData = new EquationSolverData();
    public final List<AbstractEquation> abstractEquations = new ArrayList<>(Arrays.asList(new MultivariateEquation(),new OneVariableOneDegreeEquation()));

    public EquationSolverData() {
        super("EquationSolverData");
        equationSolverData = this;
    }

    @Override
    public void read() throws Throwable {
        if (file.exists()) {
            equationSolverData = (EquationSolverData) new ObjectInputStream(
                    Files.newInputStream(file.toPath())
            ).readObject();
        }
    }
}
