package test.rhino;

import mozilla.javascript.*;

import java.io.StringReader;


public class TestParser {

	public static void main(String[] args) throws JavaScriptException {
		// TODO Auto-generated method stub
		new TestParser().start();
	}

	/**
	 * method for pinpointing the bug
	 */


	public void start() throws JavaScriptException {
		stage3();
	}

	public void stage3() throws JavaScriptException {
		Context cx = Context.enter();
		cx.setOptimizationLevel(-1);
		cx.setLanguageVersion(Context.VERSION_1_4);
		Scriptable scope = cx.initStandardObjects(null);
//		String src = "3/(1+2)";
		Object result = cx.evaluateString(scope,src,null,1,null);
		Context.toNumber(result);
		cx.exit();
	}

//	static String src = "[1,2]";
	static String src = ".57";

	public void stage1_2(){
		System.out.println(src);


		StringReader sr = new StringReader(src);
		//StringReader sr = new StringReader("{1:2}");
		TokenStream ts = new TokenStream(sr, null, 1);

		try {
			//Context.enter();
			IRFactory irf = new IRFactory(ts);
			Parser p = new Parser(irf);
//            Object o = p.parse(ts);
			Object o = p.primaryExpr(ts, new Source());
//            Iterator i = null;
//            String s = ((Node)o).toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}


}
