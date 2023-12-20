package test;

import coverage.SubjectExecutor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.StringReader;

public class testCommonsCsv extends SubjectExecutor {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        packagePrefix = "org.apache.commons.csv";
        inputFileName = args[0];
        new testCommonsCsv().wrapExecute();
    }

    @Override
    public void execute(String input) throws Throwable {
        StringReader csvReader=new StringReader(input);
        Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(csvReader);
    }
}
