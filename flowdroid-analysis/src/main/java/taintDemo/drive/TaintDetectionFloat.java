package taintDemo.drive;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class TaintDetectionFloat {

    protected static String appPath;
    protected static String classname;
    protected static List<String> sources = new ArrayList<>();
    protected static List<String> sinks = new ArrayList<>();

    public static void main(String[] args) throws Exception {

        // for feature test
//        setTaintParameters4Float();

//        setTaintParameters4Colt();
//        setTaintParameters4ColtCD();
//        setTaintParameters4ColtED();
//        setTaintParameters4ColtLU();
//        setTaintParameters4ColtQR();
//        setTaintParameters4ColtRank();
//        setTaintParameters4ColtSolver();
//        setTaintParameters4ColtSVD();
//        setTaintParameters4ColtTVS();
//
//        setTaintParameters4CholeskyDecompositor();
//        setTaintParameters4EigenDecompositor();
//        setTaintParameters4ForwardBackSubstitutionSolver();
//        setTaintParameters4GaussianSolver();
//        setTaintParameters4GaussJordanInverter();
//        setTaintParameters4JacobiSolver();
//        setTaintParameters4LeastSquaresSolver();
//        setTaintParameters4LIA4J();
//        setTaintParameters4LUDecompositor();
//        setTaintParameters4NoPivotGaussInverter();
//        setTaintParameters4QRDecompositor();
//        setTaintParameters4SeidelSolver();
//        setTaintParameters4SingularValueDecompositor();
//        setTaintParameters4SquareRootSolver();
//        setTaintParameters4SweepSolver();

        if (args.length == 0) {
            System.out.println("length of args is 0");

            FlowDroidDemo flowDroidDemo = new FlowDroidDemo(appPath, classname, sources, sinks);
            flowDroidDemo.run();
        } else {

            appPath = args[0];
            classname = args[1];
            String intFlag = args[2];
            String charFlag = args[3];
            String floatFlag = args[4];

            if (charFlag.equals("1")) {
//                setSources();
            } else if (intFlag.equals("1")) {
//                setIntegerSources();
            } else if (floatFlag.equals("1")) {
                setFloatSources();
            }
            setFloatSinks();

            // Redirect output to a file
            String fileName = appPath.split("/")[8];
            String entryName = classname;
            try {
                PrintStream print = new PrintStream("/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/expeResult/taint/float/" + fileName + "-" + entryName + ".log");
                System.setOut(print);
            } catch (FileNotFoundException e) {
            }

            FlowDroidDemo flowDroidDemo = new FlowDroidDemo(appPath, classname, sources, sinks);
            flowDroidDemo.run();
        }

    }

    private static void setTaintParameters4SweepSolver() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j";
        classname = "test.TestSweepSolver";
        setFloatSources();
        setFloatSinks();
    }
    
    private static void setTaintParameters4SquareRootSolver() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j";
        classname = "test.TestSquareRootSolver";
        setFloatSources();
        setFloatSinks();
    }

    private static void setTaintParameters4SingularValueDecompositor() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j";
        classname = "test.TestSingularValueDecompositor";
        setFloatSources();
        setFloatSinks();
    }

    private static void setTaintParameters4SeidelSolver() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j";
        classname = "test.TestSeidelSolver";
        setFloatSources();
        setFloatSinks();
    }

    private static void setTaintParameters4QRDecompositor() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j";
        classname = "test.TestQRDecompositor";
        setFloatSources();
        setFloatSinks();
    }

    private static void setTaintParameters4NoPivotGaussInverter() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j";
        classname = "test.TestNoPivotGaussInverter";
        setFloatSources();
        setFloatSinks();
    }

    private static void setTaintParameters4LUDecompositor() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j";
        classname = "test.TestLUDecompositor";
        setFloatSources();
        setFloatSinks();
    }

    private static void setTaintParameters4LIA4J() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j";
        classname = "test.TestLIA4J";
        setFloatSources();
        setFloatSinks();
    }

    private static void setTaintParameters4LeastSquaresSolver() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j";
        classname = "test.TestLeastSquaresSolver";
        setFloatSources();
        setFloatSinks();
    }

    private static void setTaintParameters4JacobiSolver() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j";
        classname = "test.TestJacobiSolver";
        setFloatSources();
        setFloatSinks();
    }

    private static void setTaintParameters4GaussJordanInverter() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j";
        classname = "test.TestGaussJordanInverter";
        setFloatSources();
        setFloatSinks();
    }

    private static void setTaintParameters4GaussianSolver() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j";
        classname = "test.TestGaussianSolver";
        setFloatSources();
        setFloatSinks();
    }

    private static void setTaintParameters4ForwardBackSubstitutionSolver() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j";
        classname = "test.TestForwardBackSubstitutionSolver";
        setFloatSources();
        setFloatSinks();
    }

    private static void setTaintParameters4EigenDecompositor() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j";
        classname = "test.TestEigenDecompositor";
        setFloatSources();
        setFloatSinks();
    }

    private static void setTaintParameters4CholeskyDecompositor() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j";
        classname = "test.TestCholeskyDecompositor";
        setFloatSources();
        setFloatSinks();
    }

    // ------ ------
    
    private static void setTaintParameters4ColtTVS() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-colt";
        classname = "test.TestColtTVS";
        setFloatSources();
        setFloatSinks();
    }

    private static void setTaintParameters4ColtSVD() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-colt";
        classname = "test.TestColtSVD";
        setFloatSources();
        setFloatSinks();
    }

    private static void setTaintParameters4ColtSolver() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-colt";
        classname = "test.TestColtSolver";
        setFloatSources();
        setFloatSinks();
    }

    private static void setTaintParameters4ColtRank() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-colt";
        classname = "test.TestColtRank";
        setFloatSources();
        setFloatSinks();
    }

    private static void setTaintParameters4ColtQR() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-colt";
        classname = "test.TestColtQR";
        setFloatSources();
        setFloatSinks();
    }

    private static void setTaintParameters4ColtLU() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-colt";
        classname = "test.TestColtLU";
        setFloatSources();
        setFloatSinks();
    }

    private static void setTaintParameters4ColtED() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-colt";
        classname = "test.TestColtED";
        setFloatSources();
        setFloatSinks();
    }

    private static void setTaintParameters4ColtCD() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-colt";
        classname = "test.TestColtCD";
        setFloatSources();
        setFloatSinks();
    }

    private static void setTaintParameters4Colt() {
        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-colt";
        classname = "test.TestColt";
        setFloatSources();
        setFloatSinks();
    }

    private static void setTaintParameters4Float() {

        appPath = "/home/lmx/Documents/GitHub/jpf-concolic/flowdroid-analysis/target/classes";
        classname = "demoRecursion.testRecursion";

        String source1 = "<demoRecursion.testRecursion: int secret()>";
        String sink1 = "<featureDetection.InstrumentHelper: void recursionConditionHelperInt(int,int)>";

        sources.add(source1);
        sinks.add(sink1);

    }

    private static void setFloatSources() {
        String source1 = "<gov.nasa.jpf.jdart.Debug: double makeConcolicDouble(java.lang.String,java.lang.String)>";
        sources.add(source1);
    }

    private static void setFloatSinks() {
        String sink1 = "<featureDetection.InstrumentHelper: void ifBranchHelperInt(int,int)>";
        sinks.add(sink1);
    }

}
