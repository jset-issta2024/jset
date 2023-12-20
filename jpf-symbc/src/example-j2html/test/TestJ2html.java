package test;
import gov.nasa.jpf.symbc.Debug;

import static j2html.TagCreator.*;
//import gov.nasa.jpf.jdart.Debug;

public class TestJ2html {
    public static void main(String[] args) {
    	start();

    }
    public static void start(){
    	String s = "0'l;|/f";
    	char[] data; 
		data=s.toCharArray(); 
		for(int i=0;i<s.length();i++){ 
			if(((char)data[i])!=' '){
//				data[i]=Debug.makeConcolicChar("sym_cnf_char"+i, ""+(int)data[i]);
				data[i]= Debug.makeSymbolicChar(""+(int)data[i]);
			} 
		} 
		String str = new String(data); 
		
        body().with(
                h1(str).withClass("example"),
                img().withSrc(str)
        ).render();
    }
}
