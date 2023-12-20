import coverage.SubjectExecutor;
import html.parser.ParseException;
import html.parser.test;

import java.io.IOException;
import java.io.StringReader;

public class TestHtmlParserDriver extends SubjectExecutor {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		packagePrefix = "html.parser";
		inputFileName = args[0];
		if (args.length > 1){
			isBrief = args[1].equals("1") ? true : false;
		}
		new TestHtmlParserDriver().wrapExecute();
	}

	@Override
	public void execute(String input) throws Throwable {
		try {
			start(input);
		}catch (Throwable e){}
	}

	void start(String s){

//	    s = SymbolicString.makeConcolicString(s);
		StringReader sr = new StringReader(s);
//		System.out.println(s);
		test parser = new test(sr);
		try {
			String rus = parser.start();
		} catch (ParseException e) {
//			e.printStackTrace();
		}
	}
}
