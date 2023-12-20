import clojure.ClojureParser;
import clojure.ParseException;
import coverage.SubjectExecutor;

import java.io.IOException;
import java.io.StringReader;

public class TestClojureDriver extends SubjectExecutor {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        packagePrefix = "clojure";
        inputFileName = args[0];
        if (args.length > 1){
            isBrief = args[1].equals("1") ? true : false;
        }
        new TestClojureDriver().wrapExecute();
    }

    @Override
    public void execute(String input) throws Throwable {
        try {
            stage1_2(input);
        }catch (Throwable e){}
    }

    private void stage1_2(String s) {
        StringReader reader = new StringReader(s);

        try {
            new ClojureParser(reader).S();
        } catch (ParseException e) {
        }
    }
}
