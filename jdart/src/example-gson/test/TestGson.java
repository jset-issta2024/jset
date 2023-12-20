package test;

import com.google.gson.Gson;

//import gov.nasa.jpf.jdart.Debug;

import java.io.CharArrayReader;
import java.io.Reader;

public class TestGson {
	static String text1 = "[true, false]"; 
	public static void main(String[] args) throws Exception{
		//String s = "{\"order\": 4711,";
		String s = "[\"abc\"]";
		char[] data = s.toCharArray();
		start(data);
	}
	public static void start(char[] data){
//		System.out.println("-------------------start----------------------");

//		char[] data;
//		data=s.toCharArray();
//		for(int i=0;i<s.length();i++){
//			if(((char)data[i])!=' '){
//				data[i]=Debug.makeConcolicChar("sym_cnf_char"+i, ""+(int)data[i]);
//			}
//		}
		String str = new String(data); 
		System.out.println(str);
		
		Gson gson = new Gson();
//		gson.fromJson(str, String.class);
		gson.fromJson(new CharArrayReader(data), Reader.class);
		// gson.fromJson(text1,String[].class);
	}	
}
