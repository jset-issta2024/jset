import coverage.SubjectExecutor;
import javascriptInterpreter.parser.Javascript;
import javascriptInterpreter.parser.ParseException;
import javascriptInterpreter.tree.SimpleNode;
import javascriptInterpreter.visitors.Context;
import javascriptInterpreter.visitors.ExecutionVisitor;

import java.io.IOException;
import java.io.StringReader;

public class TestJsijccDriver extends SubjectExecutor {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        packagePrefix = "javascriptInterpreter";
        inputFileName = args[0];
        if (args.length > 1){
            isBrief = args[1].equals("1") ? true : false;
        }

        new TestJsijccDriver().wrapExecute();
    }

    @Override
    public void execute(String input) throws Throwable {
        try {
            stage1_2(input);
            stage3();
        }catch (Throwable e){}
    }
    SimpleNode sn;
    void stage1_2(String s) throws ParseException {
        //		String s = "5 % 3";
//		s = SymbolicString.makeConcolicString(s);
//        System.out.println(s);
        StringReader reader = new StringReader(s);

        Javascript parser = new Javascript(reader);
//		SimpleNode sn = null;
        sn = parser.program();
    }

    void stage3(){
        ExecutionVisitor v = new ExecutionVisitor();
        Context scope = new Context();
        sn.jjtAccept(v, scope);
//		System.out.println("Successfully executed the program");
    }
}
