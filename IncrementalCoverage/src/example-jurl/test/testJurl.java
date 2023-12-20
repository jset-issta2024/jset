package test;

import com.anthonynsimon.url.URL;
import coverage.SubjectExecutor;

import java.io.IOException;

public class testJurl extends SubjectExecutor {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        packagePrefix = "com.anthonynsimon.url";
        inputFileName = args[0];
        new testJurl().wrapExecute();
    }

    @Override
    public void execute(String input) throws Throwable {
        // Parse URLs
        URL base = URL.parse(input);

        // Parsed base
        base.getScheme(); // https
        base.getUsername(); // user
        base.getPassword(); // secret
        base.getHost(); // example♬.com
        base.getPath(); // /path/to/my/dir
        base.getFragment(); // about
    }
}
