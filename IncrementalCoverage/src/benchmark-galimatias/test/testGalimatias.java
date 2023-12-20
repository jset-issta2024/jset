package test;

import coverage.SubjectExecutor;
import io.mola.galimatias.URL;

import java.io.IOException;

public class testGalimatias extends SubjectExecutor {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        packagePrefix = "io.mola.galimatias";
        inputFileName = args[0];
        new testGalimatias().wrapExecute();
    }

    @Override
    public void execute(String input) throws Throwable {
        URL url;
        url = URL.parse(input);
    }
}
