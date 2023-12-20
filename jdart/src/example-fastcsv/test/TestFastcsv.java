package test;


import java.io.CharArrayReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

import de.siegmar.fastcsv.reader.*;

public class TestFastcsv {
	public static void main(String[] args) throws Exception{
		String s = "a,b,c\na,b,c";
		char[] data = s.toCharArray();
		start(data);
	}
	public static void start(char[] data) throws IOException {


		String str = new String(data); 
		System.out.println("===================\n" + data);
        CsvReader csvReader = new CsvReader();
        csvReader.setContainsHeader(true);
        CsvParser csvParser = csvReader.parse(new CharArrayReader(data));
        csvParser.nextRow();
    }
}
