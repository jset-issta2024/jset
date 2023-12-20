package test;

import ch.nunnisoft.xml.parser.NunniJXMLParserFSM;
import coverage.SubjectExecutor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class testXml extends SubjectExecutor {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        packagePrefix = "ch.nunnisoft.xml.parser";
        inputFileName = args[0];
        new testXml().wrapExecute();
    }

    @Override
    public void execute(String input) throws Throwable {
        try {
            NunniJXMLParserFSM parser = new NunniJXMLParserFSM(null);

            BufferedReader fr = new BufferedReader(new StringReader(input));
            DocHandler handler = new DocHandler();
            parser.parse(fr, handler);
        }
        catch (Exception e) {
            if (e instanceof ch.nunnisoft.xml.parser.LogicError) {

            }
        }
    }
}
