package test;

import ij.plugin.PGM_Reader;
import java.io.IOException;

public class TestPgmDecoderDriver {
	
	public static void main(String[] args) throws IOException {
		start();
	}

	private static void start() throws IOException {
		
		PGM_Reader pgmReader = new PGM_Reader();
		//pgmReader.run("src/example-ImageJA/test/testPgm2.pgm");
		//only do symbolic for headerinfo
		new PGM_Reader().openFile("jdart/src/example-ImageJA/test/testPgm2.pgm");
		
	}

}