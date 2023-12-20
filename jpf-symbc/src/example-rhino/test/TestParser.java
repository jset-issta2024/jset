package test;

import java.io.CharArrayReader;
import java.io.StringReader;

import gov.nasa.jpf.symbc.Debug;
import mozilla.javascript.IRFactory;
import mozilla.javascript.Node;
import mozilla.javascript.Parser;
import mozilla.javascript.Source;
import mozilla.javascript.TokenStream;


public class TestParser {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = "[1,2]";
		char[] data = s.toCharArray();
		new TestParser().start(data);
		System.out.println(s);
	}
	
	/**
	 * method for pinpointing the bug
	 */
	public void start(char[] data){
		for(int i = 0; i < data.length; i++){
			data[i] = Debug.makeSymbolicChar(""+data[i]);
		}

		TokenStream ts = new TokenStream(new CharArrayReader(data), null, 1);
		
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

	/**
	 * The method for pinpointing the bug in toString of Node
	 * 
	 */
	public void start1(){
		StringReader sr = new StringReader("[1,2]");
		TokenStream ts = new TokenStream(sr, null, 1);
		
		try {
			//Context.enter();
            IRFactory irf = new IRFactory(ts);
            Parser p = new Parser(irf);
            //Object o = p.parse(ts);
            Object o = p.primaryExpr(ts, new Source());
//            Iterator i = null;
            String s = ((Node)o).toString();
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
        }
	}

}
