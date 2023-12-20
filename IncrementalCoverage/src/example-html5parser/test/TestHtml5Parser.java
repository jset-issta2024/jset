package test;

import com.html5dom.Document;
import com.html5parser.parser.Parser;
import coverage.SubjectExecutor;

import java.io.IOException;

public class TestHtml5Parser extends SubjectExecutor {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        packagePrefix = "com";
        inputFileName = args[0];
        new TestHtml5Parser().wrapExecute();
    }
    @Override
    public void execute(String input) throws Throwable {
        Parser parser = new Parser(true);
        Document doc = parser.parse(input);
    }
}
