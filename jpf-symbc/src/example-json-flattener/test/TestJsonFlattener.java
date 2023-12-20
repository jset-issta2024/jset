package test;

import java.io.CharArrayReader;
import java.io.IOException;
import java.util.Map;

import com.github.wnameless.json.flattener.JsonFlattener;
import gov.nasa.jpf.symbc.Debug;

//import com.eclipsesource.json.JsonValue;

//import gov.nasa.jpf.jdart.Debug;

//import com.owlike.genson.Genson;

//import gov.nasa.jpf.jdart.Debug;


public class TestJsonFlattener {
	public static void main(String[] args) throws Exception{
		start();
	}
	public static void start() throws IOException {

		String s = "{ \"a\" : { \"b\" : 1, \"c\": null, \"d\": [false, true] }, \"e\": \"f\", \"g\":2.3 }";
		
		char[] data; 
		data=s.toCharArray(); 
		for(int i=0;i<s.length();i++){ 
			if(((char)data[i])!=' '){ 
//				data[i]= Debug.makeConcolicChar("sym_cnf_char"+i, ""+(int)data[i]);
				data[i]= Debug.makeSymbolicChar(""+(int)data[i]);
			} 
		} 
		String str = new String(data); 
//		String json = "{ \"a\" : { \"b\" : 1, \"c\": null, \"d\": [false, true] }, \"e\": \"f\", \"g\":2.3 }";
		
		//Map<String, Object> flattenJson = JsonFlattener.flattenAsMap(json);
		System.out.println("start with"+str);
		Map<String, Object> flattenJson = JsonFlattener.flattenAsMap(new CharArrayReader(data));

		//System.out.println(flattenJson);
		
//		String jsonStr = JsonFlattener.flatten(str);
//		System.out.println(jsonStr);


	}
}
