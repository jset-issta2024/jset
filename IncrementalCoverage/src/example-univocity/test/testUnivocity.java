package test;

import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import coverage.SubjectExecutor;

import java.io.IOException;

public class testUnivocity extends SubjectExecutor {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        packagePrefix = "com.univocity.parsers";
        inputFileName = args[0];
        new testUnivocity().wrapExecute();
    }

    @Override
    public void execute(String input) throws Throwable {
        CsvParserSettings settings = new CsvParserSettings();
        CsvParser parser = new CsvParser(settings);
        String[] line = parser.parseLine(input);
    }
}
