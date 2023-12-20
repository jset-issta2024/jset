package test;


import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;

import java.nio.charset.StandardCharsets;

public class TestJsoniter {
	public static void main(String[] args){
		String s = "[1,2,3]";
		start(s.getBytes(StandardCharsets.UTF_8));
	}
	public static void start(byte[] data){

		String str = new String(data);
		
		System.out.println("--------------start--------------");

		int i = 0;
		while(i < str.length()){
			System.out.println(str.charAt(i));
			i++;
		}
		Any obj = JsonIterator.deserialize(data);
		//System.out.println(obj.get(0));
	}
}
