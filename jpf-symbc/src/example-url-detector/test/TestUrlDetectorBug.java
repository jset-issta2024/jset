package test;

import java.util.List;

import com.linkedin.urls.Url;
import com.linkedin.urls.detection.UrlDetector;
import com.linkedin.urls.detection.UrlDetectorOptions;

public class TestUrlDetectorBug {
	//public static char[] bug1 = {(int)5 ,(int)5 ,(int)5 ,(int)5 ,(int)5 ,(int)5 ,(int)5 ,(int)5 ,(int)5 ,(int)5 ,(int)58 ,(int)47 ,(int)47 ,(int)5 ,(int)5 ,(int)5 ,(int)5 ,(int)46 ,(int)121 ,(int)121 ,(int)122 ,(int)5 ,(int)5 ,(int)5 ,(int)5 ,(int)5 ,(int)5 ,(int)5 ,(int)5 ,(int)39 ,(int)46};
	public static char[] bug1 = {(int)5 ,(int)5 ,(int)5 ,(int)5 ,(int)5 ,(int)5 ,(int)5 ,(int)5 ,(int)5 ,(int)5 ,(int)58 ,(int)47 ,(int)47 ,(int)5 ,(int)5 ,(int)5 ,(int)5 ,(int)46 ,(int)121 ,(int)121 ,(int)122 ,(int)5 ,(int)5 ,(int)5 ,(int)5 ,(int)5 ,(int)5 ,(int)0 ,(int)39 ,(int)46 ,(int)10}; 
	public static void main(String[] args){
		String str = new String(bug1);
		UrlDetector parser = new UrlDetector(str, UrlDetectorOptions.Default);
	    List<Url> found = parser.detect();
	    
	    for(Url url : found) {
        System.out.println("Scheme: " + url.getScheme());
        System.out.println("Host: " + url.getHost());
        System.out.println("Path: " + url.getPath());
        System.out.println(url.getUsername());
    }
	}
}
