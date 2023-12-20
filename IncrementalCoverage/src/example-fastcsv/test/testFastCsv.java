package test;

import coverage.SubjectExecutor;
import de.siegmar.fastcsv.reader.CsvParser;
import de.siegmar.fastcsv.reader.CsvReader;
import test.expression.ExpressionDriver;

import java.io.IOException;
import java.io.StringReader;

public class testFastCsv extends SubjectExecutor {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        packagePrefix = "de.siegmar.fastcsv";
        if (args.length>0){
            inputFileName = args[0];
        }else {
            System.err.println("input file is not given !!!");
            System.exit(-1);
        }
        new testFastCsv().wrapExecute();
    }

    @Override
    public void execute(String input) throws Throwable {
        CsvReader csvReader = new CsvReader();
        csvReader.setContainsHeader(true);


        CsvParser csvParser = csvReader.parse(new StringReader(input));
        csvParser.nextRow();
    }
}
