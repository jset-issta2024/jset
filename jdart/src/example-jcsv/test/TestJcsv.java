package test;

import java.io.CharArrayReader;
import java.io.StringReader;
import java.util.List;

import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;

public class TestJcsv {

	public static void main(String[] args) throws Exception {
		String s="a,b,:c\na,\\/b,:;c,.s:{a},[]";
		System.out.println(s);
		char[] data = s.toCharArray();
		start(data);
	}
	public static void start(char[] data) throws Exception
	{
		CSVReader csvParser = CSVReaderBuilder.newDefaultReader(new CharArrayReader(data));
		List data1 = csvParser.readAll();
	}

}
