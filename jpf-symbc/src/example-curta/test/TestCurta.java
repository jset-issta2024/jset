package test;

import gov.nasa.jpf.symbc.Debug;
import nl.bigo.curta.Curta;
import nl.bigo.curta.ParseException;
import nl.bigo.curta.TokenMgrError;

public class TestCurta {
	public static void main(String[] args) throws ParseException {
		start();
	}

	static Curta curta = new Curta();
	public static void start() throws ParseException {

//		Curta curta = new Curta();
//		try {
//			curta.eval(s);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		String s="false || true + abs(-999) << cos(PI) * ~hypot(E) + abs(-999) / cos(PI) ^ ~hypot(E)";
//		String s = FileUtils.readString("inputs/curta.input");

		s = Debug.makeSymbolicString(s);
		System.out.println(s);

		if(s.equals("test")) {
			Debug.printPC("t if");
		}
		else {
			Debug.printPC("t else");
		}

		try {
			curta.stage1_2(s);
			curta.stage3();
		} catch (TokenMgrError e) {

		}

//		Debug.setValidInputTrue();
//		Debug.prepareForStage3();

	}

}
