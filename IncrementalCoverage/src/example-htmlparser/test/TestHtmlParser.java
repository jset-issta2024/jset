package test;

import html.parser.ParseException;
import html.parser.test;

import java.io.StringReader;

public class TestHtmlParser {
	public static void main(String[] args){
		start();
	}
	public static void start(){
	    String s= "<html><head><title>foo</title></head>"
		        + "<body>"
		        + "test"
	            + "</body></html>";
	    
//	    s = SymbolicString.makeConcolicString(s);
	    StringReader sr = new StringReader(s);
		System.out.println(s);
	    test parser = new test(sr);
	    try {
			String rus = parser.start();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
