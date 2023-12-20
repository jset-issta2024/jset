package test;

//import gov.nasa.jpf.jdart.Debug;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.Map;

import net.quux00.simplecsv.CsvReader;

public class TestSimplecsv {
	public static void main(String[] args) throws Exception{
		start();
	}
	public static void start() throws Exception{

		String csv = "a,b,c\na,b,c";
		System.out.println(csv);
		
		String s=csv;
		char[] data; 
		data=s.toCharArray(); 
//		for(int i=0;i<s.length();i++){
//			if(((char)data[i])!=' '){
//				data[i]=Debug.makeConcolicChar("sym_cnf_char"+i, ""+(int)data[i]);
//			}
//		}
		String str = new String(data); 
		
		StringReader csvReader=new StringReader(str);
		
		CsvReader csvr = new CsvReader(csvReader);
		
		// now read until all records are exhausted
		List<String> toks;
		while ((toks = csvr.readNext()) != null) {
		  // toks[] is an array of values from the line
		  ;
		}
		



	}
}
