package test;

//import gov.nasa.jpf.jdart.Debug;

import gov.nasa.jpf.symbc.Debug;
import org.markdown4j.Markdown4jProcessor;

import java.io.CharArrayReader;


public class TestMarkdown4j {
	public static void main(String[] args) throws Exception{
		start();
	}
	public static void start() throws Exception{

		String markdownString = "This is ***TXTMARK***";
		
		String s = markdownString; 
		char[] data; 
		data=s.toCharArray(); 
		for(int i=0;i<s.length();i++){ 
			if(((char)data[i])!=' '){ 
//				data[i]= Debug.makeConcolicChar("sym_cnf_char"+i, ""+(int)data[i]);
				data[i]= Debug.makeSymbolicChar(""+(int)data[i]);
			} 
		} 
		String str = new String(data);
		
		String html = new Markdown4jProcessor().process(new CharArrayReader(data));
		
//		System.out.println(html);



	}
}
