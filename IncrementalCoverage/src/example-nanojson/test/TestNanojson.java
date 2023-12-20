package test;

import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonParser;
import com.grack.nanojson.JsonParserException;

//import gov.nasa.jpf.jdart.Debug;
//import gov.nasa.jpf.jdart.SymbolicString;

//import gov.nasa.jpf.jdart.Debug;

public class TestNanojson {
	public static void main(String[] args) throws Exception{
		start();
	}
	public static void start() throws JsonParserException{
//		String s = "{\"abc\":123}";
		String s="[0,{\"1\":{\"2\":{\"3\":{\"4\":[5,{\"6\":7}]}}}}]";

//		if(NanojsonConfig.SYMB_FLAG){
//			char[] data;
//			if(NanojsonConfig.TOKEN_SYMB){
//				Debug.setInput(s,"1");
//				data=s.toCharArray();
//				for(int i=0;i<data.length;++i){
//					System.out.print(""+(int)data[i]+", ");
//				}
//				System.out.println();
//			}else{
//				data=s.toCharArray();
//				for(int i=0;i<s.length();i++){
//					if(((char)data[i])!=' '){
//						data[i]=Debug.makeConcolicChar("sym_cnf_char"+i, ""+(int)data[i]);
//						System.out.print(""+(int)data[i]+", ");
//					}
//				}
//				System.out.println();
//				s = new String(data);
//			}
//		}

//		s = SymbolicString.makeConcolicString(s);
		System.out.println(s+"\n");


		

		JsonObject obj = JsonParser.object().from(s);
//		System.out.println(obj.get("abc"));
	}	
}
