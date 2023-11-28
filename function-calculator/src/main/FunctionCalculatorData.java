
package main;

import data.AbstractWrittenData;
import math.AbstractFunction;

import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wysha
 */
public class FunctionCalculatorData extends AbstractWrittenData {
    public static FunctionCalculatorData functionCalculatorDate = new FunctionCalculatorData();
    public final List<AbstractFunction> abstractFunctions = new ArrayList<>();

    public FunctionCalculatorData() {
        super("FunctionCalculatorData");
        functionCalculatorDate = this;
    }

    @Override
    public void read() throws Throwable {
        if (file.exists()) {
            functionCalculatorDate = (FunctionCalculatorData) new ObjectInputStream(
                    Files.newInputStream(file.toPath())
            ).readObject();
        }
    }
}
