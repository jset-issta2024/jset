package test;

//import gov.nasa.jpf.jdart.Debug;

import java.io.*;
import java.util.Map;

import com.google.inject.internal.cglib.proxy.$InvocationHandler;
import gov.nasa.jpf.symbc.Debug;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class TestCommonsCsv {
	public static void main(String[] args) throws Exception{

		try {
			start();
		} catch (Exception e) {
			if (e instanceof IOException) {

			}
			else {
				System.out.println("!!!BUG!!!");
				throw e;
			}
		}

	}
	public static void start() throws Exception{

		String csv = "a,b,c\na,b,c";
		System.out.println(csv);
		
		String s=csv;
		char[] data; 
		data=s.toCharArray(); 
		for(int i=0;i<s.length();i++){ 
			if(((char)data[i])!=' '){ 
//				data[i]= Debug.makeConcolicChar("sym_cnf_char"+i, ""+(int)data[i]);
				data[i]= Debug.makeSymbolicChar(""+(int)data[i]);
			} 
		} 
		String str = new String(data); 
		
		StringReader csvReader=new StringReader(str);
		Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(new CharArrayReader(data));
		for (CSVRecord record : records) {
		    ;
		}


	}
}
