package test;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonValue;


import java.io.CharArrayReader;
import java.io.IOException;


public class TestDriver {
	public static void main(String[] args) throws Exception{

		String json = "{0:0e,\"f\":[1,2,3],true}";
		start(json.toCharArray());
	}
	public static void start(char[] data) throws IOException {


		//System.out.println(json);
		
		/*
		 * Note that this symbolization does not succeed in getting the rgse test running,

			The reason may be the read() function called by parse() -- the character read with an int variable current
			
			I signed current with makeconcolicinterger, and it came out successfully
		 */

		//#####//
		
		
		System.out.println("---------------start---------------");
		
		JsonValue value =Json.parse(new CharArrayReader(data));

//		Genson genson=new Genson();
		


	}
}
