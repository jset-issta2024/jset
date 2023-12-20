package test;

import com.html5dom.Document;
import com.html5parser.parser.Parser;
//import gov.nasa.jpf.jdart.Debug;

/**
 * Hello world!
 *
 */
public class TestHtml5parserDriver {
	public static void main(String[] args) {
		String s = "<!doctype html> <!doctype html>"
				+ "<html><head><title>First parse</title></head>"
				+ "<!-- Region: {view-rendered} 	 Module: {view-rendered}  -->"
				+ "<body><p>Parsed HTML into a doc.</p></body></html>";
		char[] data = s.toCharArray();
		start(data);
	}

	private static void start(char[] data) {
		//Parser parser = new Parser(true, true);

		String s = data.toString();
		
		Parser parser = new Parser(true);
		Document doc = parser.parse(s);		
//		System.out.println(doc.getOuterHtml(true));
//		if(Html5parserConfig.SYMB_FLAG)
//			Debug.printCurrentPC();
	}

}
