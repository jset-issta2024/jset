package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import ij.plugin.*;;

public class TestBmpDecoderDriver {
	
	public static void main(String[] args) throws Exception {
		start();
	}

	private static void start() throws Exception {
		
		BMPDecoder decoder = new BMPDecoder();
		InputStream is = new FileInputStream(new File("jpf-symbc/src/example-ImageJA/test/test.bmp"));
		decoder.read(is);
		
	}

}
