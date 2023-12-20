package test;


import java.io.IOException;
import java.io.StringReader;

import de.siegmar.fastcsv.reader.*;
//import gov.nasa.jpf.jdart.Debug;

public class TestFastcsv {
	public static void main(String[] args) throws Exception{
		start();
	}
	public static void start() throws IOException {	
		String s = "a,b,c\na,b,c";
		System.out.println(s);
		
		char[] data; 
		data=s.toCharArray(); 
//		for(int i=0;i<s.length();i++){
//			if(((char)data[i])!=' '){
//				data[i]=Debug.makeConcolicChar("sym_cnf_char"+i, ""+(int)data[i]);
//			}
//		}
		String str = new String(data); 
		
        CsvReader csvReader = new CsvReader();
        csvReader.setContainsHeader(true);
        
        
        CsvParser csvParser = csvReader.parse(new StringReader(str));
        csvParser.nextRow();
    }
}
