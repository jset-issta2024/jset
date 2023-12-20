package test;

//import gov.nasa.jpf.jdart.Debug;

import java.util.Arrays;

import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

public class TestUnivocity {
	public static void main(String[] args){
		start();
	}
	public static void start(){
		String s ="user,pwd,info";
		
    	char[] data; 
		data=s.toCharArray(); 
//		for(int i=0;i<s.length();i++){
//			if(((char)data[i])!=' '){
//				data[i]=Debug.makeConcolicChar("sym_cnf_char"+i, ""+(int)data[i]);
//			}
//		}
		String str = new String(data); 
		System.out.println("--------------start--------------");

//		Debug.saveInput(str);

		CsvParserSettings settings = new CsvParserSettings();
		CsvParser parser = new CsvParser(settings);
		
		String[] line;
		
		line = parser.parseLine(str);
		//System.out.println(Arrays.toString(line));
	}
}
