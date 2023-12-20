package test;


import org.markdown4j.Markdown4jProcessor;

import java.io.CharArrayReader;


public class TestMarkdown4j {
	public static void main(String[] args) throws Exception{
		String markdownString = "This is ***TXTMARK***";
		start(markdownString.toCharArray());
	}
	public static void start(char[] data) throws Exception{


		String html = new Markdown4jProcessor().process(new CharArrayReader(data));
		
//		System.out.println(html);



	}
}
