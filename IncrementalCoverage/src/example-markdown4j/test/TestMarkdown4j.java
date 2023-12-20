package test;

import coverage.SubjectExecutor;
import org.markdown4j.Markdown4jProcessor;
import java.io.IOException;

public class TestMarkdown4j extends SubjectExecutor {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		packagePrefix = "org.markdown4j";
		inputFileName = args[0];
		new TestMarkdown4j().wrapExecute();
	}

	@Override
	public void execute(String input) throws Throwable {
		String html = new Markdown4jProcessor().process(input);
	}
}
