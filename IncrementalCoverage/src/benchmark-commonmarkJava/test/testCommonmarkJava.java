package test;

import coverage.SubjectExecutor;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;

import java.io.IOException;

public class testCommonmarkJava extends SubjectExecutor {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        packagePrefix = "org.commonmark";
        inputFileName = args[0];
        new testCommonmarkJava().wrapExecute();
    }

    @Override
    public void execute(String input) throws Throwable {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(input);
    }
}
