package test;

//import gov.nasa.jpf.jdart.Debug;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;

public class TestJsoniter {
	public static void main(String[] args){
		start();
	}
	public static void start(){
		String s = "[1,2,3]";
    	char[] data; 
		data=s.toCharArray(); 
//		for(int i=0;i<s.length();i++){
//			if(((char)data[i])!=' '){
//				data[i]=Debug.makeConcolicChar("sym_cnf_char"+i, ""+(int)data[i]);
//			}
//		}
		String str = new String(data);
		
		System.out.println("--------------start--------------");

		Any obj = JsonIterator.deserialize(str);
		//System.out.println(obj.get(0));
	}
}
