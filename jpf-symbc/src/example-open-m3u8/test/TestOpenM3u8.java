package test;

//import gov.nasa.jpf.jdart.Debug;

import com.iheartradio.m3u8.*;
import com.iheartradio.m3u8.data.Playlist;
import gov.nasa.jpf.symbc.Debug;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class TestOpenM3u8 {
	public static void main(String[] args){
		start();
	}
	public static void start(){
		String s = "#EXTM3U\n"
					+"#EXT-X-VERSION:5\n"
					+"#EXT-X-TARGETDURATION:3\n"
					+"#EXT-X-MEDIA-SEQUENCE:1\n"
					+"#EXTINF:3.0,Example Song\n"
					+"example.mp3\n"
					+"#EXTINF:3.0,Additional Song\n"
					+"additional.mp3\n"
					+"#EXT-X-ENDLIST";
		char[] data; 
		data=s.toCharArray(); 
		for(int i=0;i<s.length();i++){ 
			if(((char)data[i])!=' '){
//				data[i]=Debug.makeConcolicChar("sym_cnf_char"+i, ""+(int)data[i]);
				data[i]= Debug.makeSymbolicChar(""+(int)data[i]);
				//System.out.print((int)data[i] + " ");
			} 
		} 
		String str = new String(data);
		System.out.println("start");
		
//		InputStream inputStream = new ByteArrayInputStream(s.getBytes());
		InputStream inputStream = new ByteArrayInputStream(str.getBytes());
		PlaylistParser parser = new PlaylistParser(inputStream, Format.EXT_M3U, Encoding.UTF_8);
		Playlist playlist = null;
		try {
			playlist = parser.parse();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PlaylistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(playlist.toString());
	}
}
