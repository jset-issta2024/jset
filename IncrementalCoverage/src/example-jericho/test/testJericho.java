package test;

import coverage.SubjectExecutor;
import net.htmlparser.jericho.Source;

import java.io.IOException;

public class testJericho extends SubjectExecutor {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        packagePrefix = "net.htmlparser.jericho";
        inputFileName = args[0];
        new testJericho().wrapExecute();
    }

    @Override
    public void execute(String input) throws Throwable {
        Source source=new Source(input);
        source.fullSequentialParse();
    }
}
