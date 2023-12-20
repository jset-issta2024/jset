package test;

//import gov.nasa.jpf.jdart.Debug;

import java.io.StringReader;
import java.util.List;

import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;

public class TestJcsv {

	public static void main(String[] args) throws Exception {

		start();

	}

	public static void start() throws Exception
	{
		String csv="a,b,:c\na,\\/b,:;c,.s:{a},[]";
		

		String s=csv;
		char[] data;
		data=s.toCharArray();
//		for(int i=0;i<s.length();i++){
//			if(((char)data[i])!=' '){
//				data[i]=Debug.makeConcolicChar("sym_cnf_char"+i, ""+(int)data[i]);
//			}
//		}
		String str = new String(data);

		System.out.println("--------------start---------------");
		StringReader csvReader = new StringReader(str);

		CSVReader csvParser = CSVReaderBuilder.newDefaultReader(csvReader);
		List data1 = csvParser.readAll();


	}

}
