import coverage.SubjectExecutor;
import org.nfunk.jep.JEP;
import org.nfunk.jep.Node;

import java.io.IOException;

public class TestExpressionParser extends SubjectExecutor {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        packagePrefix = "org.nfunk";
        if (args.length>0){
            inputFileName = args[0];
            if (args.length > 1){
                isBrief = args[1].equals("1") ? true : false;
            }
        }else {
            System.err.println("input file is not given !!!");
            System.exit(-1);
        }
        new TestExpressionParser().wrapExecute();

//        inputFileName = "src/example-expression/TestExpressionParser-TokenSymb-genTokenString-TokenLengthBound3.saveInput.csv";
//        new ExpressionDriver().wrapExecute();
    }

    @Override
    public void execute(String input) {
        try {
            running(input);
        }catch (Throwable e){}
    }

    void running(String s){
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
