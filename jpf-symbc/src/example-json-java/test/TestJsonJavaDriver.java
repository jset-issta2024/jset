package test;

import org.json.JSONException;
import org.json.JSONObject;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
//import com.eclipsesource.json.JsonValue;

//import gov.nasa.jpf.jdart.Debug;

//import com.owlike.genson.Genson;



//import gov.nasa.jpf.jdart.Debug;


public class TestJsonJavaDriver {
	public static void main(String[] args) throws Exception{

		try {
			start();
		}catch (Exception e) {
			if (e instanceof JSONException) {

			}
			else {
				System.out.println("!!!BUG!!!");
				throw e;
			}

		}

	}
	public static void start() throws JSONException{
		//char[] bug1 ={(int)123,(int)91 ,(int)45 ,(int)44};
		String json = "{[1,2,3]:fsf,\"s\":f;";
		//String json = new String(bug1);
		//System.out.println(json);
		
//		char[] data;
//		String s=json;
//		data=s.toCharArray();
//		for(int i=0;i<s.length();i++){
//			if(((char)data[i])!=' '){
//				data[i]=Debug.makeConcolicChar("sym_cnf_char"+i, ""+(int)data[i]);
//				System.out.print((int)data[i]+" ");
//			}
//		}
//		System.out.println();
		//String str = new String(json);
		System.out.println("---------------start---------------");
//		char[] data=json.toCharArray();
//		for(int i=0; i<json.length(); i++){
//			System.out.print((int)data[i] + " ");
//		}
		new JSONObject(json);


	}
}
 