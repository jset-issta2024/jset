package test;


import java.io.CharArrayReader;
import java.io.StringReader;
import java.util.List;

import org.supercsv.io.CsvListReader;
import org.supercsv.io.ICsvListReader;
import org.supercsv.prefs.CsvPreference;

public class testSuperCSVDriver {

	public static void main(String[] args) throws Exception {
		String s="a,b,c\na,b,c";
		
		start(s.toCharArray());

	}
	
	public static void start(char[] data) throws Exception
	{


		
		ICsvListReader listReader = new CsvListReader(new CharArrayReader(data), CsvPreference.STANDARD_PREFERENCE);
		
		listReader.getHeader(true);
		System.out.println("--------------start----------------");
		List<String> customerList;
		while( (customerList = listReader.read()) != null){
//			System.out.println(String.format("lineNo = %s, row = %s, customerList = %s", listReader.getLineNumber(),
//					listReader.getRowNumber(),customerList));
		}
		
	}

}
