package test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import de.undercouch.actson.JsonEvent;
import de.undercouch.actson.JsonParser;

public class TestActson {
	public static void main(String[] args) throws Exception{
		String s = "{\"abc\":123}";
		char[] data = s.toCharArray();
		start(data);
	}
	public static void start(char[] data) throws IOException {

//		saveInput(data);

		String str = new String(data);
//		byte[] json = str.getBytes(StandardCharsets.UTF_8);

		Charset cs = Charset.forName("UTF-8");
		CharBuffer charBuffer = CharBuffer.allocate(data.length);
		charBuffer.put(str);
		charBuffer.flip();
		ByteBuffer byteBuffer = cs.encode(charBuffer);
		byte[] json = byteBuffer.array();

	    JsonParser parser = new JsonParser(StandardCharsets.UTF_8);

	    int pos = 0; // position in the input JSON text
	    int event; // event returned by the parser

	      // feed the parser until it returns a new event
	      while ((event = parser.nextEvent()) == JsonEvent.NEED_MORE_INPUT) {
	        // provide the parser with more input
	        pos += parser.getFeeder().feed(json, pos, json.length - pos);

	        // indicate end of input to the parser
	        if (pos == json.length) {
	          parser.getFeeder().done();
	        }
	      }
	}

	private static void saveInput(char[] data) throws IOException {
//		double time = (System.currentTimeMillis() - StatisticResutls.START_TIME);
//		inputWithTime.put(input,time);
//

		String fileName = "/home/lmx/Documents/GitHub/jpf8/jdart/expeResult/testCoverage.log";
		File file =new File(fileName);
		FileOutputStream out = new FileOutputStream(file);
		ObjectOutputStream objOut=new ObjectOutputStream(out);
		objOut.writeObject("ttttttttttttttttttttttttttttttttt");
		objOut.writeObject(data);
		objOut.flush();
		objOut.close();
	}
}
