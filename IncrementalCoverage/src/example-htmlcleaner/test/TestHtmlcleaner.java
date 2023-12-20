package test;

//import gov.nasa.jpf.jdart.Debug;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

public class TestHtmlcleaner {
	public static void main(String[] args){
		start();
	}
	public static void start(){
		
		String s = "<html><head><title>First parse</title></head>\n<gag]"
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
		//only do parsing
		HtmlCleaner cleaner = new HtmlCleaner();
		TagNode node = cleaner.clean(str);
	
		//Object[] ns = node.getElementsByName("title", true);
	}
}
