package test;

//import gov.nasa.jpf.jdart.Debug;
//import gov.nasa.jpf.jdart.SymbolicString;
import gov.nasa.jpf.symbc.Debug;
import org.mariuszgromada.math.mxparser.Expression;

public class TestmXparser {
    public static void main(String[] args) throws Exception{
        start();
    }

    public static void start() throws Exception {
        stage1_2();

//        Debug.setValidInputTrue();
//        Debug.prepareForStage3();
//        Debug.printCurrentPC();
        try {
            stage3();
        }catch (Throwable e){}
//        Debug.printCurrentPC();
//        Debug.finishStage3();
    }

    static String src = "1 + 1";
    static Expression e1;
    private static void stage1_2() {
//        src = SymbolicString.makeConcolicString(src);
        src = Debug.makeSymbolicString(src);
        System.out.println(src);
        e1 = new Expression(src);
        e1.getBeforeCal();
    }

    static void stage3() {
        e1.remainCal();
    }

}
