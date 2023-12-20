package test;

//import gov.nasa.jpf.jdart.SymbolicString;
import gov.nasa.jpf.symbc.Debug;
import html.grammarparser.MyNewGrammar;
import html.grammarparser.ParseException;

import java.io.StringReader;

public class TestHtmlparser2 {
    public static void main(String[] args) throws ParseException {
        new TestHtmlparser2().start();
    }

    void start() throws ParseException {
        stage1_2();
    }

    void stage1_2() throws ParseException {
        String s="<!doctype html> <!doctype html>"
				+ "<html><head><title>First parse</title></head>"
				+ "<!-- Region: {view-rendered} 	 Module: {view-rendered}  -->"
				  + "<body><p>Parsed HTML into a doc.</p></body></html>";
//        s = SymbolicString.makeConcolicString(s);
        s = Debug.makeSymbolicString(s);
        System.out.println(s);

        StringReader reader = new StringReader(s);
        new MyNewGrammar(reader).exp();
    }
}
