package test;

import io.mola.galimatias.GalimatiasParseException;
import io.mola.galimatias.URL;

public class TestGalimatiasC {
	public static void main(String args[]){
		String urlString = "scs schema://data";
		URL url;
		try {
			url = URL.parse(urlString);
			System.out.println(url.toString());
		} catch (GalimatiasParseException ex) {
			System.out.print("It's a non-recoverable parsing error!");
		  // Do something with non-recoverable parsing error
		}
	}

}
