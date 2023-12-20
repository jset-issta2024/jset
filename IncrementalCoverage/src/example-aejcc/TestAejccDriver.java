import ca.ubc.cs411.aejcc.ast.AE;
import ca.ubc.cs411.aejcc.parser.AEParser;
import ca.ubc.cs411.aejcc.parser.ParseException;
import coverage.SubjectExecutor;

import java.io.IOException;
import java.io.StringReader;

public class TestAejccDriver extends SubjectExecutor {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		packagePrefix = "ca.ubc.cs411.aejcc";
		inputFileName = args[0];
		if (args.length > 1){
			isBrief = args[1].equals("1") ? true : false;
		}
		new TestAejccDriver().wrapExecute();
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
		AEParser aep = new AEParser(sr);
		try {
			AE result = aep.REPLCmd();
		} catch (ParseException e) {
//			e.printStackTrace();
		}
	}
}
