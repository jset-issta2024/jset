package test;

import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonParser;
import coverage.SubjectExecutor;

import java.io.IOException;

public class testNanojson extends SubjectExecutor {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        packagePrefix = "com.grack.nanojson";
        inputFileName = args[0];
        new testNanojson().wrapExecute();
    }

    @Override
    public void execute(String input) throws Throwable {
        JsonObject obj = JsonParser.object().from(input);
    }
}
