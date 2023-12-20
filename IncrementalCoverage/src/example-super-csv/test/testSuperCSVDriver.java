package test;

//import gov.nasa.jpf.jdart.Debug;

import java.io.StringReader;
import java.util.List;

import org.supercsv.io.CsvListReader;
import org.supercsv.io.ICsvListReader;
import org.supercsv.prefs.CsvPreference;

public class testSuperCSVDriver {

	public static void main(String[] args) throws Exception {
		
		start();

	}
	
	public static void start() throws Exception
	{
		String s="a,b,c\na,b,c";
		char[] data; 
		data=s.toCharArray(); 
//		for(int i=0;i<s.length();i++){
//			if(((char)data[i])!=' '){
//				data[i]=Debug.makeConcolicChar("sym_cnf_char"+i, ""+(int)data[i]);
//			}
//		}
		String str = new String(data); 
		
		StringReader csvReader = new StringReader(str);
		
		ICsvListReader listReader = new CsvListReader(csvReader, CsvPreference.STANDARD_PREFERENCE);
		
		listReader.getHeader(true);
		System.out.println("--------------start----------------");
		List<String> customerList;
		while( (customerList = listReader.read()) != null){
//			System.out.println(String.format("lineNo = %s, row = %s, customerList = %s", listReader.getLineNumber(),
//					listReader.getRowNumber(),customerList));
		}
		
	}

}
