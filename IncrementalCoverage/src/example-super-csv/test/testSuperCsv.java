package test;

import coverage.SubjectExecutor;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.ICsvListReader;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

public class testSuperCsv extends SubjectExecutor {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        packagePrefix = "org.supercsv";
        inputFileName = args[0];
        new testSuperCsv().wrapExecute();
    }

    @Override
    public void execute(String input) throws Throwable {
        StringReader csvReader = new StringReader(input);
        ICsvListReader listReader = new CsvListReader(csvReader, CsvPreference.STANDARD_PREFERENCE);
        listReader.getHeader(true);
        System.out.println("--------------start----------------");
        List<String> customerList;
        while( (customerList = listReader.read()) != null){
			System.out.println(String.format("lineNo = %s, row = %s, customerList = %s", listReader.getLineNumber(),
					listReader.getRowNumber(),customerList));
        }
    }
}
