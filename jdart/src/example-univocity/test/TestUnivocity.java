package test;

import java.io.CharArrayReader;
import java.util.Arrays;
import java.util.List;

import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

public class TestUnivocity {
	public static void main(String[] args){
		String s ="user,pwd,info";
		System.out.println(s);
		char[] data = s.toCharArray();
		start(data);
	}
	public static void start(char[] data){
		String str = new String(data); 
		System.out.println("--------------start--------------");
		
		CsvParserSettings settings = new CsvParserSettings();
		CsvParser parser = new CsvParser(settings);
		
		List<String[]> line;
		
//		line = parser.parseLine(str);
		line = parser.parseAll(new CharArrayReader(data) );
		//System.out.println(Arrays.toString(line));
	}
}
