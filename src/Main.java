import cn.com.wysha.math_toolkit.data.MathToolkitNecessaryData;
import cn.com.wysha.math_toolkit.main.MainInterface;
import cn.com.wysha.math_toolkit.main.MathGroupMainInterface;
import cn.com.wysha.math_toolkit.views.ErrorInterface;

public class Main {
    public static void main(String[] args) {
        try {
            MathToolkitNecessaryData.mathToolkitNecessaryData.read();
            MainInterface.home.setVisible(true);
            new Thread(() -> new MathGroupMainInterface().setVisible(true)).start();
        } catch (Throwable e) {
            new ErrorInterface(
                    "异常",
                    e,
                    true
            ).setVisible(true);
        }
    }
}
