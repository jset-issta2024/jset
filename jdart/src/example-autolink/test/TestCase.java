package test;
import java.util.EnumSet;

import org.nibor.autolink.*;

public class TestCase {
	public static void main(String args[]){
//		String input = "wow, so example: http://test.com ,, ; ,fdfs";
		String input = "what is it? scheme:example ";
		LinkExtractor linkExtractor = LinkExtractor.builder()
		        .linkTypes(EnumSet.of(LinkType.URL, LinkType.WWW, LinkType.EMAIL))
		        .build();
		Iterable<Span> spans = linkExtractor.extractSpans(input);
//		Iterable<LinkSpan> links = linkExtractor.extractLinks(input);
//		
//		LinkSpan link = links.iterator().next();
//		link.getType();        // LinkType.URL
//		link.getBeginIndex();  // 17
//		link.getEndIndex();    // 32
//		  // "http://test.com"
		//System.out.println(input.substring(link.getBeginIndex(), link.getEndIndex()));
		for(Span span : spans){
			String text = input.substring(span.getBeginIndex(), span.getEndIndex());
			System.out.println(text);
		}
		
	}

}
