package test;

//import gov.nasa.jpf.jdart.Debug;

import com.anthonynsimon.url.URL;

public class TestJurl {
	public static void main(String[] args) throws Exception{
		start();          
	}
	public static void start() throws Exception{
		// Parse URLs
		String s1 = "https://user:secret@example♬.com/path/to/my/dir#about";
		String s2 = "./../file.html?search=germany&language=de_DE";
		
		String ss1 = new String(s1);
		char[] data1;
		data1 = ss1.toCharArray();
		int cnt = 0;
//		for(int i=0;i<ss1.length();i++){
//			if((char)data1[i]!=' '){
//				data1[i] = Debug.makeConcolicChar("sym_cnf_char"+i,""+(int)data1[i]);
//				cnt++;
//			}
//		}
		String str1 = new String(data1);
		
		String ss2 = new String(s2);
		char[] data2;
		data2 = ss2.toCharArray();
//		for(int i=0;i<ss2.length();i++){
//			if((char)data2[i]!=' '){
//				int label = cnt+i;
//				data2[i] = Debug.makeConcolicChar("sym_cnf_char"+label,""+(int)data2[i]);
//			}
//		}
		String str2 = new String(data2);
		
		URL base = URL.parse(str1);
		URL ref = URL.parse(str2);

		// Parsed base
		base.getScheme(); // https
		base.getUsername(); // user
		base.getPassword(); // secret
		base.getHost(); // example♬.com
		base.getPath(); // /path/to/my/dir
		base.getFragment(); // about

		// Parsed reference
		ref.getPath(); // ./../file.html
		ref.getQueryPairs(); // Map<String, String> = {search=germany, language=de_DE}

		// Resolve them!
		URL resolved = base.resolveReference(ref); // https://user:secret@example♬.com/path/to/file.html?search=germany&language=de_DE
		resolved.getPath(); // /path/to/file.html

		// Escaped UTF-8 result
		System.out.println(resolved.toString()); // https://user:secret@example%E2%99%AC.com/path/to/file.html?search=germany&language=de_DE
	}
}
