package test;

import gov.nasa.jpf.symbc.Debug;
import io.mola.galimatias.GalimatiasParseException;
import io.mola.galimatias.URL;
//import gov.nasa.jpf.jdart.Debug;

public class TestGalimatias {
	public static void main(String[] args) throws Exception{
		start();
	}
	public static void start() throws GalimatiasParseException{

		String s = "http://test.com such linked";
		System.out.println("------------start-----------");
		char[] data; 
		data=s.toCharArray(); 
		for(int i=0;i<s.length();i++){ 
			if(((char)data[i])!=' '){ 
//				data[i]= Debug.makeConcolicChar("sym_cnf_char"+i, ""+(int)data[i]);
				data[i]= Debug.makeSymbolicChar(""+(int)data[i]);
			} 
		} 
		String str = new String(data); 
		URL url;
		url = URL.parse(str);
		System.out.println(url.toString());
	}
}
