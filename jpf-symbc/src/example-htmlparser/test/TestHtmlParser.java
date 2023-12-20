package test;

import gov.nasa.jpf.symbc.Debug;
import html.parser.ParseException;
import html.parser.test;

import java.io.StringReader;

public class TestHtmlParser {
	public static void main(String[] args) throws ParseException {
		start();
	}
	public static void start() throws ParseException {
	    String s= "< html >< head >< title > foo </ title ></ head >"
		        + "< body > < p > Parsed HTML into a doc . </ p > < p > Parsed HTML into a doc . </ p > "
		        + " < p > Parsed HTML into a doc . </ p > < p > Parsed HTML into a doc . </ p >  "
				+ " < p > Parsed HTML into a doc . </ p > < p > Parsed HTML into a doc . </ p >  "
	            + "</ body >"
				+ "</ html >";

//	    s = FileUtils.readString("inputs/Html_parser.input");

		s = "<html><head><title>foo</title></head>"
			+ "<body>"
			+ "test"
			+ "</body></html>";

		s = Debug.makeSymbolicString(s);
		System.out.println(s);

		if(s.equals("test")) {
			Debug.printPC("t if");
		}
		else {
			Debug.printPC("t else");
		}

		try {
			StringReader sr = new StringReader(s);
			test parser = new test(sr);
			String rus = parser.start();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
