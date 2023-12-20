package test;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;
import com.ibm.icu.util.BytesTrie.Iterator;

public class TestJcsvC {
	public static void main(String[] args) throws IOException{
		String s="a,b,c\na,b,c";
		StringReader csvReader = new StringReader(s);
		
		CSVReader csvParser = CSVReaderBuilder.newDefaultReader(csvReader);
		List res = csvParser.readAll();
		 for (int i = 0; i < res.size(); i++) {
			 String[] t = (String[]) res.get(i);
			 for(String ss : t) 
				 System.out.println(ss);
		 }
		System.out.println(res);
	}
}
