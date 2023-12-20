import coverage.SubjectExecutor;
import jsonparser.JSONObject;
import jsonparser.JSONParser;
import jsonparser.ParseException;

import java.io.IOException;
import java.io.StringReader;

public class TestJsonparserDriver extends SubjectExecutor {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		packagePrefix = "jsonparser";
		inputFileName = args[0];
		if (args.length > 1){
			isBrief = args[1].equals("1") ? true : false;
		}
		new TestJsonparserDriver().wrapExecute();
	}

	@Override
	public void execute(String input) throws Throwable {
		try{
			start(input);
		}catch (Throwable e){}
	}

	void start(String jsonstr) {

//		jsonstr = SymbolicString.makeConcolicString(jsonstr);
//		System.out.println(jsonstr);
		StringReader sr = new StringReader(jsonstr);

		try {
			JSONObject res = JSONParser.parse(sr);
		} catch (ParseException e) {
//			e.printStackTrace();
		}
	}
}
