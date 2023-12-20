package test;

import html.parser.ParseException;
import html.parser.TokenMgrError;
import html.parser.test;

import java.io.StringReader;

public class TestHtmlParser {
	public static void main(String[] args){
		String s= "< html >< head >< title > foo </ title ></ head >"
				+ "< body > < p > Parsed HTML into a doc . </ p > < p > Parsed HTML into a doc . </ p > "
				+ " < p > Parsed HTML into a doc . </ p > < p > Parsed HTML into a doc . </ p >  "
				+ " < p > Parsed HTML into a doc . </ p > < p > Parsed HTML into a doc . </ p >  "
				+ "</ body >"
				+ "</ html >";
		char[] data = s.toCharArray();

		start(data);
	}
	public static void start(char[] data){

//	    s = FileUtils.readString("inputs/Html_parser.input");
//		s = "<html><head><title>foo</title></head>"
//			+ "<body>"
//			+ "test"
//			+ "</body></html>";
//		s = SymbolicString.makeConcolicString(s);

		try {
			String s = data.toString();
	    	StringReader sr = new StringReader(s);
			System.out.println(s);
	    	test parser = new test(sr);
			String rus = parser.start();
		} catch (TokenMgrError | ParseException e) {
			e.printStackTrace();
		}
	}
}
