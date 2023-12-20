package test;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonValue;
import com.eclipsesource.json.ParseException;
import gov.nasa.jpf.symbc.Debug;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.io.CharArrayReader;
import java.io.IOException;

//import gov.nasa.jpf.jdart.Debug;

//import com.owlike.genson.Genson;

//import gov.nasa.jpf.jdart.Debug;


public class TestDriver {
	public static void main(String[] args) throws Exception{

		try{
			start();
		} catch (Exception e) {
			if (e instanceof ParseException) {

			}
			else {
				System.out.println("!!!BUG!!!");
				throw e;
			}
		}

	}
	public static void start() throws IOException {

		String json = "{0:0e,\"f\":[1,2,3],true}";
		//System.out.println(json);
		
		/*
		 * Note that this symbolization does not succeed in getting the rgse test running,

			The reason may be the read() function called by parse() -- the character read with an int variable current
			
			I signed current with makeconcolicinterger, and it came out successfully
		 */
		String s=json;
		char[] data; 
		data=s.toCharArray(); 
		for(int i=0;i<s.length();i++){ 
			if(((char)data[i])!=' '){ 
//				data[i]=Debug.makeConcolicChar("sym_cnf_char"+i, ""+(int)data[i]);
				data[i]= Debug.makeSymbolicChar(""+(int)data[i]);
			} 
		}
		
		JsonValue value =Json.parse(new CharArrayReader(data));

//		Genson genson=new Genson();
		


	}
}
