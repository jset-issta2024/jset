package test;

import argo.jdom.JdomParser;
import argo.saj.InvalidSyntaxException;
import gov.nasa.jpf.symbc.Debug;

import java.io.CharArrayReader;

public class TestArgo {
	public static void main(String[] args) throws Exception{

		try {
			start();
		}catch (Exception e) {
			if (e instanceof InvalidSyntaxException) {

			}
			else {
				System.out.println("!!!BUG!!!");
				throw e;
			}
		}

	}
	public static void start() throws Exception{

		String jsonText = "[true, false]";
		
		String s = new String(jsonText); 
		char[] data; 
		data=s.toCharArray(); 
		for(int i=0;i<s.length();i++){ 
			if(((char)data[i])!=' '){ 
//				data[i]= Debug.makeConcolicChar("sym_cnf_char"+i, ""+(int)data[i]);
				data[i]= Debug.makeSymbolicChar(""+(int)data[i]);
			} 
		} 
//		String str = new String(data);
		
		new JdomParser().parse(new CharArrayReader(data));
		



	}
}
