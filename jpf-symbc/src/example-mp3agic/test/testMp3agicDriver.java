package test;

import com.mpatric.mp3agic.Mp3File;

public class testMp3agicDriver {

	public static void main(String[] args) throws Exception {
		
		start();

	}
	
	public static void start() throws Exception {
        
        Mp3File mp3file = new Mp3File("src/example-mp3agic/test/resources/dummyframes.mp3");
        System.out.println("Length of this mp3 is: " + mp3file.getLengthInSeconds() + " seconds");
        System.out.println("Bitrate: " + mp3file.getBitrate() + " kbps " + (mp3file.isVbr() ? "(VBR)" : "(CBR)"));
        System.out.println("Sample rate: " + mp3file.getSampleRate() + " Hz");
        System.out.println("Has ID3v1 tag?: " + (mp3file.hasId3v1Tag() ? "YES" : "NO"));
        System.out.println("Has ID3v2 tag?: " + (mp3file.hasId3v2Tag() ? "YES" : "NO"));
        System.out.println("Has custom tag?: " + (mp3file.hasCustomTag() ? "YES" : "NO"));
	}
	

}
