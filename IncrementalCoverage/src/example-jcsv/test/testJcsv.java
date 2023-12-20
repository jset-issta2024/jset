package test;

import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;
import coverage.SubjectExecutor;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

public class testJcsv extends SubjectExecutor {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        packagePrefix = "com.googlecode.jcsv";
        inputFileName = args[0];

        new testJcsv().wrapExecute();
    }

    @Override
    public void execute(String input) throws Throwable {
        StringReader csvReader = new StringReader(input);
        CSVReader csvParser = CSVReaderBuilder.newDefaultReader(csvReader);
        List data = csvParser.readAll();
    }
}
