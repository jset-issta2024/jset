package test;

import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonParser;
import com.grack.nanojson.JsonParserException;
import gov.nasa.jpf.symbc.Debug;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.io.CharArrayReader;

//import gov.nasa.jpf.jdart.Debug;

public class TestNanojson {
	public static void main(String[] args) throws Exception{

		try {
			String s="[0,{\"1\":{\"2\":{\"3\":{\"4\":[5,{\"6\":7}]}}}}]";
			start(s.toCharArray());
		}catch (Exception e) {
			if (e instanceof JsonParserException) {

			}
			else {
				System.out.println("!!!Bug!!!");
				throw e;
			}
 		}

	}
	public static void start(char[] data) throws JsonParserException{
//		String s = "{\"abc\":123}";


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
				for(int i=0;i<data.length;i++){
					if(((char)data[i])!=' '){
						data[i]= Debug.makeSymbolicChar(""+(int)data[i]);
						System.out.print(""+(int)data[i]+", ");
					}
				}
//				System.out.println();
//				s = new String(data);
//			}
//		}

		System.out.println(data.toString()+"\n");

		JsonObject obj = JsonParser.object().from(new CharArrayReader(data));
//		System.out.println(obj.get("abc"));
	}	
}
