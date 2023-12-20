package test.expression;


import org.nfunk.jep.JEP;
import org.nfunk.jep.Node;

public class TestExpression {

    public static void main(String[] args) {
        start();
    }

    public static void start(){
        try{
            driver();
        }catch (Throwable e){
            e.printStackTrace();
        }

//        if (ExpressionConfig.SYMB_FLAG && ExpressionConfig.TOKEN_SYMB)
//        if (Debug.isCharSymb() && Debug.isTokenSymb())
        {
//            Debug.splitPC();
//            Debug.printCurrentPC();
//            Debug.ExitIfBenchmark();
        }
    }

    public static void driver() throws Exception{
        String s="V40 [ V40 [ V40 [ V40 [ V40 [ V40 [ V40 / V40 ( V40 V40 [ V40 [ V40 [ V40 [ V40 [ V40 [ V40 / V40 ( V40 ) )";
//        String s="1 + 1";
//        char[] in = {0, 0, 0, 0, 0, 49, 43, 124, 124, };
//        String s=String.valueOf(in);


        System.out.println(s);
        JEP myParser = new JEP();
        myParser.addStandardConstants();
        myParser.addStandardFunctions();
        myParser.addComplex();
        myParser.setImplicitMul(true);
        myParser.setAllowAssignment(true);
        myParser.setAllowUndeclared(true);
        Node n1=myParser.parseExpression(s);
    }


}
