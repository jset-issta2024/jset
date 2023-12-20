package test;

import coverage.SubjectExecutor;
import org.w3c.tidy.Tidy;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;

public class testJtidy extends SubjectExecutor {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        packagePrefix = "org.w3c.tidy";
        inputFileName = args[0];
        new testJtidy().wrapExecute();
    }

    @Override
    public void execute(String input) throws Throwable {
        try{
            ByteArrayOutputStream tidyOutStream;
            tidyOutStream = new ByteArrayOutputStream();
            Tidy tidy = new Tidy();
            tidy.setInputEncoding("UTF-8");
            tidy.setQuiet(true);
            tidy.setOutputEncoding("UTF-8");
            tidy.setShowWarnings(false);
            tidy.setIndentContent(true);//
            tidy.setSmartIndent(true);
            tidy.setIndentAttributes(false);
            tidy.setWraplen(1024);
            tidy.setXHTML(true);
            tidy.setErrout(new PrintWriter(System.out));
            tidy.parse(new StringReader(input), tidyOutStream);

        }
        catch ( Exception ex ){
            System.out.println( ex.toString());
            ex.printStackTrace();
        }
    }
}
