package test;

import nl.bigo.curta.Curta;
import nl.bigo.curta.ParseException;
import nl.bigo.curta.TokenMgrError;

public class TestCurta {
	public static void main(String[] args) throws ParseException {
		String s="false || true + abs(-999) << cos(PI) * ~hypot(E) + abs(-999) / cos(PI) ^ ~hypot(E)";
		char[] data = s.toCharArray();
		start(data);
	}
	static Curta curta = new Curta();

	public static void start(char[] data) throws ParseException {

//		String s="false || true";
//		s = SymbolicString.makeConcolicString(s);
//		System.out.println(s);
//
//		Curta curta = new Curta();
//		try {
//			curta.eval(s);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		String s = FileUtils.readString("inputs/curta.input");
//		s = SymbolicString.makeConcolicString(s);
//		System.out.println(s);
		try {
			String s = data.toString();
			curta.stage1_2(s);
			curta.stage3();
		} catch (TokenMgrError e) {
			e.printStackTrace();
		}


//		Debug.setValidInputTrue();
//		Debug.prepareForStage3();

	}

}
