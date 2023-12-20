package test;

import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

//import gov.nasa.jpf.jdart.Debug;


public class TestJsonSimpleDriver {

	public static void main(String[] args) throws Exception {
		String s="[true, false]";
		char[] data = s.toCharArray();
        start(data);
    }

	public static void start(char[] data){
//		String s="[0,{\"1\":{\"2\":{\"3\":{\"4\":[5,{\"6\":7}]}}}}]";
		
//		System.out.println("-----------------------start");
//
//		char[] data;
//		data=s.toCharArray();
//
//		for(int i=0;i<s.length();i++){
//			if(((char)data[i])!=' '){
//				data[i]=Debug.makeConcolicChar("sym_cnf_char"+i, ""+(int)data[i]);
//			}
//		}
//
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
