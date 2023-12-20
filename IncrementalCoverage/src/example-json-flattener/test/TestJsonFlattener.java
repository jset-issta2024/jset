package test;

import java.io.IOException;
import java.util.Map;
import com.github.wnameless.json.flattener.JsonFlattener;
import coverage.SubjectExecutor;

public class TestJsonFlattener extends SubjectExecutor {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		packagePrefix = "com.github.wnameless.json";
		inputFileName = args[0];
		new TestJsonFlattener().wrapExecute();
	}

	@Override
	public void execute(String input) throws Throwable {
		Map<String, Object> flattenJson = JsonFlattener.flattenAsMap(input);
	}
}
