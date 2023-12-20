package test;

import coverage.SubjectExecutor;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import java.io.IOException;

public class testHtmlcleaner extends SubjectExecutor {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        packagePrefix = "org.htmlcleaner";
        inputFileName = args[0];
        new testHtmlcleaner().wrapExecute();
    }
    @Override
    public void execute(String input) throws Throwable {
        HtmlCleaner cleaner = new HtmlCleaner();
        TagNode node = cleaner.clean(input);
    }
}
