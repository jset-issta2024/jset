package test;

import java.io.CharArrayReader;
import java.util.EnumSet;

import gov.nasa.jpf.symbc.Debug;
import org.nibor.autolink.LinkExtractor;
import org.nibor.autolink.LinkType;
import org.nibor.autolink.Span;

//import gov.nasa.jpf.jdart.Debug;

public class TestAutolink {
	public static void main(String[] args) throws Exception{
		start();
	}
	public static void start(){

		String s = "wow; fs:1//sf,'';http://test.com such linked";
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
		LinkExtractor linkExtractor = LinkExtractor.builder()
		        .linkTypes(EnumSet.of(LinkType.URL, LinkType.WWW, LinkType.EMAIL))
		        .build();
		Iterable<Span> spans = linkExtractor.extractSpans(str);
		for(Span span : spans){
			String text = str.substring(span.getBeginIndex(), span.getEndIndex());
		}
		
	}
}
