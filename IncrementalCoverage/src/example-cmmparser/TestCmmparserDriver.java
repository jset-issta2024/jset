import coverage.SubjectExecutor;
import rong.CMMParser;

import java.io.IOException;
import java.io.StringReader;

public class TestCmmparserDriver extends SubjectExecutor {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        packagePrefix = "rong";
        inputFileName = args[0];
        if (args.length > 1){
            isBrief = args[1].equals("1") ? true : false;
        }

        new TestCmmparserDriver().wrapExecute();
    }

    @Override
    public void execute(String input) throws Throwable {
        try {
            stage1_2(input);
        }catch (Throwable e){}
    }

    public void stage1_2(String s){
//        System.out.println(s);
        StringReader reader = new StringReader(s);
        CMMParser parser = new CMMParser(reader);
        try {
            parser.procedure();
        } catch (Exception e) {}
    }
}
