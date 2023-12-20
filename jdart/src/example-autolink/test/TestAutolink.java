package test;

import org.nibor.autolink.LinkExtractor;
import org.nibor.autolink.LinkType;
import org.nibor.autolink.Span;

import java.util.EnumSet;

public class TestAutolink {
	public static void main(String[] args) throws Exception{
		String s = "wow; fs:1//sf,'';http://test.com such linked";
		char[] data = s.toCharArray();
		start(data);
	}
	public static void start(char[] data){

//		System.out.println("------------start-----------");
//		char[] data;
//		data=s.toCharArray();
//		for(int i=0;i<s.length();i++){
//			if(((char)data[i])!=' '){
//				data[i]=Debug.makeConcolicChar("sym_cnf_char"+i, ""+(int)data[i]);
//			}
//		}

		String str = new String(data);
		LinkExtractor linkExtractor = LinkExtractor.builder()
		        .linkTypes(EnumSet.of(LinkType.URL, LinkType.WWW, LinkType.EMAIL))
		        .build();
		Iterable<Span> spans = linkExtractor.extractSpans(str);

//		for(Span span : spans){
//			String text = str1.substring(span.getBeginIndex(), span.getEndIndex());
//		}
		
	}
}
