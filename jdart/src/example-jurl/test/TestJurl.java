package test;

import com.anthonynsimon.url.URL;
import com.anthonynsimon.url.exceptions.InvalidURLReferenceException;
import com.anthonynsimon.url.exceptions.MalformedURLException;

public class TestJurl {
	public static void main(String[] args) throws Exception{
		String s1 = "https://user:secret@example♬.com/path/to/my/dir#about";
		String s2 = "./../file.html?search=germany&language=de_DE";
		start(s1.toCharArray(),s2.toCharArray());
	}
	public static void start(char[] data1, char[] data2) throws MalformedURLException, InvalidURLReferenceException {
		// Parse URLs

		URL base = URL.parse(data1.toString());
		URL ref = URL.parse(data2.toString());

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
