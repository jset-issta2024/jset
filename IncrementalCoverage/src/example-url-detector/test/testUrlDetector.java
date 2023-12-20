package test;

import com.linkedin.urls.Url;
import com.linkedin.urls.detection.UrlDetector;
import com.linkedin.urls.detection.UrlDetectorOptions;
import coverage.SubjectExecutor;

import java.io.IOException;
import java.util.List;

public class testUrlDetector extends SubjectExecutor {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        packagePrefix = "com.linkedin.urls";
        inputFileName = args[0];
        new testUrlDetector().wrapExecute();
    }

    @Override
    public void execute(String input) throws Throwable {
        UrlDetector parser = new UrlDetector(input, UrlDetectorOptions.Default);
        List<Url> found = parser.detect();
    }
}
