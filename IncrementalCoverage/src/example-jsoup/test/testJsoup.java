package test;

import coverage.SubjectExecutor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class testJsoup extends SubjectExecutor {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        packagePrefix = "org.jsoup";
        inputFileName = args[0];
        new testJsoup().wrapExecute();
    }

    @Override
    public void execute(String input) throws Throwable {
        Document doc = Jsoup.parse(input);
    }
}
