package test;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonValue;
import coverage.SubjectExecutor;

import java.io.IOException;

public class testMinimalJson extends SubjectExecutor {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        packagePrefix = "com.eclipsesource.json";
        inputFileName = args[0];
        new testMinimalJson().wrapExecute();
    }

    @Override
    public void execute(String input) throws Throwable {
        JsonValue value = Json.parse(input);
        System.out.println(value);
    }
}
