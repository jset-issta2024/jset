package test;

import gov.nasa.jpf.symbc.Debug;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;

//import gov.nasa.jpf.jdart.Debug;


public class TestJsonSimpleDriver {
	
	public static String s="[true, false]";
	
	public static void main(String[] args) throws Exception {
        start();
    }

	public static void start(){
//		String s="[0,{\"1\":{\"2\":{\"3\":{\"4\":[5,{\"6\":7}]}}}}]";
		
		System.out.println("-----------------------start");
		
		char[] data;
		data=s.toCharArray();
		
		for(int i=0;i<s.length();i++){
			if(((char)data[i])!=' '){
//				data[i]=Debug.makeConcolicChar("sym_cnf_char"+i, ""+(int)data[i]);
				data[i]= Debug.makeSymbolicChar(""+(int)data[i]);
			}
		}
		
		String str = new String(data);
		System.out.println("---------------start---------------");
//		if (str.charAt(0) > 0) {
//			System.out.println("-----------------aaa");
//		}
//		
//		Debug.printCurrentPC();
		
		Object obj=JSONValue.parse(str);
		JSONArray array=(JSONArray)obj;
		
		
	}

}
