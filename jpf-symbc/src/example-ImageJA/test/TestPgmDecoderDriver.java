package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import ij.plugin.GifDecoder;
import ij.plugin.PGM_Reader;

public class TestPgmDecoderDriver {
	
	public static void main(String[] args) throws IOException {
		start();
	}

	private static void start() throws IOException {
		
		PGM_Reader pgmReader = new PGM_Reader();
		//pgmReader.run("src/example-ImageJA/test/testPgm2.pgm");
		//only do symbolic for headerinfo
		new PGM_Reader().openFile("jpf-symbc/src/example-ImageJA/test/testPgm2.pgm");
		
	}

}