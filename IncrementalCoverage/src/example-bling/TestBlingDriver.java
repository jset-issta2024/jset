import com.cloudability.bling.ast.BlingParser;
import com.cloudability.bling.ast.Expression;
import coverage.SubjectExecutor;

import java.io.IOException;
import java.io.StringReader;

public class TestBlingDriver extends SubjectExecutor {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		packagePrefix = "com.cloudability.bling.ast";
		inputFileName = args[0];
		if (args.length > 1){
			isBrief = args[1].equals("1") ? true : false;
		}
		new TestBlingDriver().wrapExecute();
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
		BlingParser parser = new BlingParser(sr);
		Expression e = parser.parse();
	}
}
