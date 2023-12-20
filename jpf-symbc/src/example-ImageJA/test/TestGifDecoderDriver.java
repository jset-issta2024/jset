package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import ij.plugin.GifDecoder;

public class TestGifDecoderDriver {
	
	public static void main(String[] args) throws FileNotFoundException {
		start();
	}

	private static void start() throws FileNotFoundException {
		
		GifDecoder decoder = new GifDecoder();
		
//		BufferInputStream is = new FileInputStream(new File("src/example-ImageJA/test/test.gif"));
		
		decoder.read("jpf-symbc/src/example-ImageJA/test/test2.gif");
		
//		GIF_Reader gifReader = new GIF_Reader();
//		gifReader.run("src/example-ImageJA/test/test.gif");
		
	}

}