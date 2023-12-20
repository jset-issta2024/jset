package test;

import io.mola.galimatias.GalimatiasParseException;
import io.mola.galimatias.URL;
//import gov.nasa.jpf.jdart.Debug;

public class TestGalimatias {
	public static void main(String[] args) throws Exception{
		String s = "http://test.com such linked";
		char[] data = s.toCharArray();
		start(data);
	}
	public static void start(char[] data) throws GalimatiasParseException{


//		System.out.println("------------start-----------");
//		char[] data;
//		data=s.toCharArray();
//		for(int i=0;i<s.length();i++){
//			if(((char)data[i])!=' '){
//				data[i]=Debug.makeConcolicChar("sym_cnf_char"+i, ""+(int)data[i]);
//			}
//		}
		String str = new String(data); 
		URL url;
//		url = URL.parse(str);
		url = URL.parse(str);
		System.out.println(url.toString());
	}
}
