package test;


import schroeder.SoundModel;
import schroeder.adapter.WAVEFileAdapter;

import java.io.File;


public class TestWAVEFileRead {

	public static File f = new File("jpf-symbc/src/example-schroeder/test.wav");
	
	public static void main(String[] args) {
		new TestWAVEFileRead().start();
	}
	
	public void start(){
		WAVEFileAdapter r = new WAVEFileAdapter();
		r.setFile(TestWAVEFileRead.f);
		SoundModel model = r.createSoundFromFile();
		System.out.println(model);
	}

}
