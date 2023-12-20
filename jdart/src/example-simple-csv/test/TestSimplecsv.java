package test;


import java.io.*;
import java.util.List;
import java.util.Map;

import net.quux00.simplecsv.CsvReader;

public class TestSimplecsv {
	public static void main(String[] args) throws Exception{
		String csv = "a,b,c\na,b,c";
		start(csv.toCharArray());
	}
	public static void start(char[] data) throws Exception{


		System.out.println(data.toString());

		CsvReader csvr = new CsvReader(new CharArrayReader(data));
		
		// now read until all records are exhausted
		List<String> toks;
		while ((toks = csvr.readNext()) != null) {
		  // toks[] is an array of values from the line
		  ;
		}
		



	}
}
