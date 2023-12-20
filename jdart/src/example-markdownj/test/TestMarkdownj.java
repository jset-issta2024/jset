package test;

//import gov.nasa.jpf.jdart.Debug;

import org.markdownj.*;


public class TestMarkdownj {
	public static void main(String[] args) throws Exception{
		String markdownString = "This is ***TXTMARK***";
		char[] data = markdownString.toCharArray();
		start(data);
	}
	public static void start(char[] data) throws Exception{

		String str = new String(data);
		MarkdownProcessor markdownProcessor = new MarkdownProcessor();
		String html = markdownProcessor.markdown(str);

		System.out.println(html);

	}
}
