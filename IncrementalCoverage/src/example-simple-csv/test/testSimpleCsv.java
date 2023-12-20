package test;

import coverage.SubjectExecutor;
import net.quux00.simplecsv.CsvReader;

import java.io.IOException;
import java.io.StringReader;

public class testSimpleCsv extends SubjectExecutor {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        packagePrefix = "net.quux00.simplecsv";
        inputFileName = args[0];
        new testSimpleCsv().wrapExecute();
    }

    @Override
    public void execute(String input) throws Throwable {
        StringReader csvReader=new StringReader(input);
        CsvReader csvr = new CsvReader(csvReader);
    }
}
