package test;

import java.util.List;

import com.linkedin.urls.Url;
import com.linkedin.urls.detection.UrlDetector;
import com.linkedin.urls.detection.UrlDetectorOptions;

//import gov.nasa.jpf.jdart.Debug;

public class TestUrlDetector {
	public static void main(String[] args) throws Exception{
		start();
	}
	public static void start(){

		String s = "wow;]{ http://test.com such linked";
		System.out.println("------------start-----------");
		char[] data; 
		data=s.toCharArray(); 
//		for(int i=0;i<s.length();i++){
//			if(((char)data[i])!=' '){
//				data[i]=Debug.makeConcolicChar("sym_cnf_char"+i, ""+(int)data[i]);
//			}
//		}
		String str = new String(data);

//		Debug.saveInput(str);

		//NOTE:This parser has multiple UrlDetectorOptions;
		UrlDetector parser = new UrlDetector(str, UrlDetectorOptions.Default);
	    List<Url> found = parser.detect();

//	    for(Url url : found) {
//	        System.out.println("Scheme: " + url.getScheme());
//	        System.out.println("Host: " + url.getHost());
//	        System.out.println("Path: " + url.getPath());
//	        //System.out.println(url.getUsername());
//	    }
	}
}
