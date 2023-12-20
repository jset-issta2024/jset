package test;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.options.MutableDataSet;
import coverage.SubjectExecutor;

import java.io.IOException;

public class testFlexmark extends SubjectExecutor {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        packagePrefix = "com.vladsch.flexmark";
        inputFileName = args[0];
        new testFlexmark().wrapExecute();
    }

    @Override
    public void execute(String input) throws Throwable {
        MutableDataSet options = new MutableDataSet();
        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        Node document = parser.parse(input);
        String html = renderer.render(document);

    }
}
