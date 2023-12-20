import com.braxisltd.calculator.ArithmeticParser;
import com.braxisltd.calculator.ParseException;
import com.braxisltd.calculator.Value;
import coverage.SubjectExecutor;

import java.io.IOException;
import java.io.StringReader;

public class TestCalculatorDriver extends SubjectExecutor {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		packagePrefix = "com.braxisltd.calculator";
		inputFileName = args[0];
		if (args.length > 1){
			isBrief = args[1].equals("1") ? true : false;
		}
		new TestCalculatorDriver().wrapExecute();
	}

	@Override
	public void execute(String input) throws Throwable {
		try {
			stage1_2(input);
			stage3();
		}catch (Throwable e){}
	}

	private void stage3() {
		Value v = parser.evaluate();
	}
	ArithmeticParser parser;
	private void stage1_2(String s) {

//		s = SymbolicString.makeConcolicString(s);
		StringReader sr = new StringReader(s);
//		System.out.println(s);
		parser = new ArithmeticParser(sr);
		try {
			parser.parse();
		} catch (ParseException e) {
//			e.printStackTrace();
		}
	}
}
