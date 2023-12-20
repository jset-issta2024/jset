package test;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import coverage.SubjectExecutor;

import java.io.IOException;

public class testJsoniter extends SubjectExecutor {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        packagePrefix = "com.jsoniter";
        inputFileName = args[0];
        new testJsoniter().wrapExecute();
    }

    @Override
    public void execute(String input) throws Throwable {
        Any obj = JsonIterator.deserialize(input);
    }
}
