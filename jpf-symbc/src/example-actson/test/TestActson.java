package test;

import java.nio.charset.StandardCharsets;

import de.undercouch.actson.JsonEvent;
import de.undercouch.actson.JsonParser;
import gov.nasa.jpf.symbc.Debug;

public class TestActson {
	public static void main(String[] args) throws Exception{
		start();
	}
	public static void start(){
		String s = "{\"abc\":123}";

		char[] data; 
		data=s.toCharArray(); 
		for(int i=0;i<s.length();i++){ 
			if((data[i])!=' '){
//				data[i]= Debug.makeConcolicChar("sym_cnf_char"+i, ""+(int)data[i]);
				data[i] = Debug.makeSymbolicChar(""+data[i]);
			} 
		} 
		String str = new String(data);
		
		byte[] json = str.getBytes(StandardCharsets.UTF_8);

	    JsonParser parser = new JsonParser(StandardCharsets.UTF_8);

	    int pos = 0; // position in the input JSON text
	    int event; // event returned by the parser

	      // feed the parser until it returns a new event
	      while ((event = parser.nextEvent()) == JsonEvent.NEED_MORE_INPUT) {
	        // provide the parser with more input
	        pos += parser.getFeeder().feed(json, pos, json.length - pos);

	        // indicate end of input to the parser
	        if (pos == json.length) {
	          parser.getFeeder().done();
	        }
	      }
	}	
}
