package test;

//import gov.nasa.jpf.jdart.Debug;

import java.util.ArrayList;

import com.html5dom.Document;
import com.html5dom.Element;
import com.html5dom.Node;
import com.html5parser.algorithms.ListOfActiveFormattingElements;
import com.html5parser.algorithms.ResetTheInsertionModeAppropriately;
import com.html5parser.classes.ParserContext;
import com.html5parser.classes.Token;
import com.html5parser.classes.Token.TokenType;
import com.html5parser.insertionModes.InSelect;
import com.html5parser.parser.Parser;
import com.html5parser.parser.Serializer;
import com.html5parser.tracer.Event;
import com.html5parser.tracer.Event.EventType;
import com.html5parser.tracer.ParseError;
import com.html5parser.tracer.Tracer;
import com.html5parser.tracer.TracerSummary;
import gov.nasa.jpf.symbc.Debug;

/**
 * Hello world!
 *
 */
public class TestHtml5parserDriver {
	public static void main(String[] args) {
		start();
	}

	private static void start() {
		//Parser parser = new Parser(true, true);
		
		String s = "<!doctype html> <!doctype html>"
				+ "<html><head><title>First parse</title></head>"
				+ "<!-- Region: {view-rendered} 	 Module: {view-rendered}  -->"
				  + "<body><p>Parsed HTML into a doc.</p></body></html>";

		if(Html5parserConfig.SYMB_FLAG){
			char[] data;
			if(Html5parserConfig.TOKEN_SYMB){
//				System.out.println("JavaparserConfig.FLAG : "+JavaparserConfig.TOKEN_SYMB+"   "+JavaparserConfig.SYMB_FLAG);
//				Debug.setInput(s, "5");
				data=s.toCharArray();
				for(int i=0;i<data.length;++i){
					System.out.print(""+(int)data[i]+", ");
				}
				System.out.println();
			}else{
				data=s.toCharArray();
				for(int i=0;i<s.length();i++){
					if(((char)data[i])!=' '){
//						data[i]=Debug.makeConcolicChar("sym_cnf_char"+i, ""+(int)data[i]);
						data[i]= Debug.makeSymbolicChar(""+(int)data[i]);
						System.out.print(""+(int)data[i]+", ");
					}
				}
				System.out.println();
				s = new String(data);
			}
		}
		System.out.println(s);
		
		
		
		Parser parser = new Parser(true);
		Document doc = parser.parse(s);		
//		System.out.println(doc.getOuterHtml(true));
//		if(Html5parserConfig.SYMB_FLAG)
//			Debug.printCurrentPC();
	}

}
