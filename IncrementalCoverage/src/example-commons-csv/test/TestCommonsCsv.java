package test;

//import gov.nasa.jpf.jdart.Debug;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;




public class TestCommonsCsv {
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
		Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(csvReader);
		for (CSVRecord record : records) {
		    ;
		}


	}
}
