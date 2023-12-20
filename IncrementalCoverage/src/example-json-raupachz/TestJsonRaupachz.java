import coverage.SubjectExecutor;
import parser.JSON;
import parser.ParseException;

import java.io.IOException;
import java.io.StringReader;

public class TestJsonRaupachz extends SubjectExecutor {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		packagePrefix = "parser";
		inputFileName = args[0];
		if (args.length > 1){
			isBrief = args[1].equals("1") ? true : false;
		}
		new TestJsonRaupachz().wrapExecute();
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
		JSON json = new JSON(sr);
		try {
			Object obj = json.parse();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
	}
}
