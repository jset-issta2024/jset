package test;

//import gov.nasa.jpf.jdart.Debug;
//import gov.nasa.jpf.jdart.SymbolicString;
import org.mariuszgromada.math.mxparser.Expression;

public class TestmXparser {
    public static void main(String[] args) throws Exception{
        String src = "1 + 1";
        char[] data = src.toCharArray();
        start(data);
    }

    public static void start(char[] data) throws Exception {
        String src = data.toString();

        Expression e1;

//        src = SymbolicString.makeConcolicString(src);
        System.out.println(src);
        e1 = new Expression(src);
        e1.getBeforeCal();

//        Debug.setValidInputTrue();
//        Debug.prepareForStage3();
//        Debug.printCurrentPC();
        try {
            e1.remainCal();
        }catch (Throwable e){}
//        Debug.printCurrentPC();
//        Debug.finishStage3();
    }

}
