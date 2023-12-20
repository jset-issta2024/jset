package test;

//import gov.nasa.jpf.jdart.FileUtils;
//import gov.nasa.jpf.jdart.SymbolicString;
import html.parser.ParseException;
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
//	    s = SymbolicString.makeConcolicString(s);

		String s = data.toString();

		StringReader sr = new StringReader(s);
//		System.out.println(s);

	    test parser = new test(sr);
	    try {
			String rus = parser.start();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
