package test;

import com.github.rjeschke.txtmark.Processor;
import gov.nasa.jpf.symbc.Debug;

import java.io.CharArrayReader;
import java.io.IOException;

public class TestTxtmark {
	public static void main(String[] args) throws Exception{
		String s = "## hello\n **just test**";
		System.out.println(s);
		char[] data = s.toCharArray();
		start(data);
	}
	public static void start(char[] data) throws IOException {
		// symbolization
//		String str = new String(data);
		for(int i = 0; i < data.length; i++){
			data[i] = Debug.makeSymbolicChar(""+data[i]);
		}

		String str = new String(data);
		String result = Processor.process(str);
	}
}
