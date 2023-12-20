package test;

import argo.jdom.JdomParser;
import coverage.SubjectExecutor;

import java.io.IOException;

public class testArgo extends SubjectExecutor {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        packagePrefix = "argo";
        inputFileName = args[0];
        new testArgo().wrapExecute();
    }

    @Override
    public void execute(String input) throws Throwable {
        new JdomParser().parse(input);
    }
}
