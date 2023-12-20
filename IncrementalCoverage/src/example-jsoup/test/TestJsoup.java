package test;

//import gov.nasa.jpf.jdart.Debug;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class TestJsoup {
	public static void main(String[] args) throws Exception{
		start();
	}
	public static void start() throws Exception{
		String s = "<html><head><title>First parse</title></head>"
				  + "<body><p>Parsed HTML into a doc.</p></body></html>";
		
		char[] data; 
		data=s.toCharArray(); 
//		for(int i=0;i<s.length();i++){
//			if(((char)data[i])!=' '){
//				data[i]=Debug.makeConcolicChar("sym_cnf_char"+i, ""+(int)data[i]);
//			}
//		}
		String str = new String(data);
		
		System.out.println("-----------------start-----------------");
		Document doc = Jsoup.parse(str);
		
		//System.out.println(doc.title());
	}
}
