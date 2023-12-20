package test;

import com.google.gson.Gson;
import coverage.SubjectExecutor;

import java.io.IOException;

public class testGson extends SubjectExecutor {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        packagePrefix = "com.google.gson";
        inputFileName = args[0];
        new testGson().wrapExecute();
    }
    @Override
    public void execute(String input) throws Throwable {
        Gson gson = new Gson();
        gson.fromJson(input, String.class);
    }
}
