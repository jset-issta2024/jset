package test;

//import gov.nasa.jpf.jdart.Debug;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import gov.nasa.jpf.symbc.Debug;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class TestJsoniter {
	public static void main(String[] args){
		start();
	}
	public static void start(){
		String s = "[1,2,3]";
		byte[] d = s.getBytes(StandardCharsets.UTF_8);
    	char[] data;
		data=s.toCharArray();
		for(int i=0;i<s.length();i++){ 
			if(((char)data[i])!=' '){ 
//				data[i]= Debug.makeConcolicChar("sym_cnf_char"+i, ""+(int)data[i]);
				data[i]= Debug.makeSymbolicChar(""+(int)data[i]);
			} 
		} 
		String str = new String(data);
		
		System.out.println("--------------start--------------");

		Charset charset = Charset.forName("UTF-8");
		CharBuffer charBuffer = CharBuffer.allocate(data.length);
		charBuffer.put(data);
		charBuffer.flip();
		ByteBuffer byteBuffer = charset.encode(charBuffer);

		Any obj = JsonIterator.deserialize(byteBuffer.array());
		//System.out.println(obj.get(0));
	}
}
