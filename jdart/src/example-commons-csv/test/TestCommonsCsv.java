package test;


import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;


public class TestCommonsCsv {

	private static Map<String, Double> inputWithTime = new LinkedHashMap<>();

	public static void main(String[] args) throws Exception{
		String s = "a,b,c\na,b,c";
		System.out.println(s);
		char[] data = s.toCharArray();
		start(data);
	}
	public static void start(char[] data) throws Exception{
		Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(new CharArrayReader(data));
		for (CSVRecord record : records) {
			;
		}


	}
}
