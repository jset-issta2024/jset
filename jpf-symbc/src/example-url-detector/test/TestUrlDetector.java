package test;

import java.util.List;

import com.linkedin.urls.Url;
import com.linkedin.urls.detection.UrlDetector;
import com.linkedin.urls.detection.UrlDetectorOptions;
import gov.nasa.jpf.symbc.Debug;

public class TestUrlDetector {
	public static void main(String[] args) throws Exception{
		String s = "wow;]{ http://test.com such linked";
		start(s.toCharArray());
	}
	public static void start(char[] data){


		System.out.println("------------start-----------");
		for(int i = 0; i < data.length; i++){
			data[i] = Debug.makeSymbolicChar("symC"+i);
		}
		//NOTE:This parser has multiple UrlDetectorOptions;
		UrlDetector parser = new UrlDetector(data, UrlDetectorOptions.Default);
	    List<Url> found = parser.detect();

//	    for(Url url : found) {
//	        System.out.println("Scheme: " + url.getScheme());
//	        System.out.println("Host: " + url.getHost());
//	        System.out.println("Path: " + url.getPath());
//	        //System.out.println(url.getUsername());
//	    }
	}
}
