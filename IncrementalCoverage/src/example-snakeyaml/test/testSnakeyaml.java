package test;

import coverage.SubjectExecutor;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;

public class testSnakeyaml extends SubjectExecutor {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		packagePrefix = "org.yaml.snakeyaml";
		inputFileName = args[0];
		new testSnakeyaml().wrapExecute();
	}

	@Override
	public void execute(String input) throws Throwable {

		Yaml yaml = new Yaml();
		Iterable<Object> ret = yaml.loadAll(input);

	}
}

