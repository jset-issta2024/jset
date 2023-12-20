package test;

import coverage.SubjectExecutor;
import nanoxml.DumpXML;
import nanoxml.XMLElement;

import java.io.IOException;
import java.io.StringReader;

public class testNanoxml extends SubjectExecutor {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        packagePrefix = "nanoxml";
        inputFileName = args[0];
        new testNanoxml().wrapExecute();
    }

    @Override
    public void execute(String input) throws Throwable {
        try{
            XMLElement xml = new XMLElement();
            StringReader reader = new StringReader(input);
            DumpXML.count = 0;
            xml.parseFromReader(reader);
        } catch (Exception e) {

        }
    }
}
