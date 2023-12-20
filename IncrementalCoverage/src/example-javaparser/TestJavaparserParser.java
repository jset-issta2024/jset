import coverage.SubjectExecutor;
import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;

import java.io.IOException;
import java.io.StringReader;

public class TestJavaparserParser extends SubjectExecutor {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        packagePrefix = "japa.parser";
        inputFileName = args[0];
        if (args.length > 1){
            isBrief = args[1].equals("1") ? true : false;
        }
        new TestJavaparserParser().wrapExecute();

//        inputFileName = "src/example-javaparser/TestJavaparserParser.saveInput";
//        new JavaparserDriver().wrapExecute();
    }

    @Override
    public void execute(String input) {
        try {
            running(input);
        }catch (Throwable e){}
    }

    void running(String si) throws ParseException {
        StringReader reader = new StringReader(si);
        CompilationUnit compilationUnit = JavaParser.parse(reader, false);
    }
}
