/*
 * 开发者:熊锦枫
 * 开发者邮箱:wyshazhisishen@yeah.net
 */

package FunctionCalulatorMain;

import date.WrittenDate;
import FunctionCalulatorMath.Function;

import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FunctionCalculatorDate extends WrittenDate {
    public static FunctionCalculatorDate functionCalculatorDate=new FunctionCalculatorDate();
    public FunctionCalculatorDate() {super("FunctionCalculatorData");functionCalculatorDate=this;}
    public final List<Function> functions=new ArrayList<>();

    public void read() throws Throwable {
        if (file.exists()){
            functionCalculatorDate= (FunctionCalculatorDate) new ObjectInputStream(
                    Files.newInputStream(file.toPath())
            ).readObject();
        }
    }
}
