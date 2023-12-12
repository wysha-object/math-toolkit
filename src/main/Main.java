package main;

import data.MathToolkitNecessaryData;
import tools.ErrorInterface;

public class Main {
    public static void main(String[] args) {
        try {
            MathToolkitNecessaryData.mathToolkitNecessaryData.read();
            //MainInterface.home.setVisible(true);
            new MathGroupMainInterface().setVisible(true);
        } catch (Throwable e) {
            new ErrorInterface(
                    "异常",
                    e,
                    true
            ).setVisible(true);
        }
    }
}
