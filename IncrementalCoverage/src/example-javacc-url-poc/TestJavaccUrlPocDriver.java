import coverage.SubjectExecutor;
import io.mikael.poc.ParseException;
import io.mikael.poc.UrlParser;

import java.io.IOException;
import java.io.StringReader;

public class TestJavaccUrlPocDriver extends SubjectExecutor {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		packagePrefix = "io.mikael.poc";
		inputFileName = args[0];
		if (args.length > 1){
			isBrief = args[1].equals("1") ? true : false;
		}
		new TestJavaccUrlPocDriver().wrapExecute();
	}

	@Override
	public void execute(String input) throws Throwable {
		try {
			start(input);
		}catch (Throwable throwable){}
	}

	void start(String s){

//	    s = SymbolicString.makeConcolicString(s);
		StringReader sr = new StringReader(s);
//		System.out.println(s);
		try {
			UrlParser.Result r = UrlParser.parse(s);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
	}
}
