package test;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.ByteArrayInputStream;
import java.io.CharArrayReader;

public class TestJsoup {
	public static void main(String[] args) throws Exception{
		String s = "<html><head><title>First parse</title></head>"
				+ "<body><p>Parsed HTML into a doc.</p></body></html>";

		start(s.toCharArray());
	}
	public static void start(char[] data) throws Exception{



		System.out.println("-----------------start-----------------");
		Document doc = Jsoup.parse(data);
		
		//System.out.println(doc.title());
	}
}
