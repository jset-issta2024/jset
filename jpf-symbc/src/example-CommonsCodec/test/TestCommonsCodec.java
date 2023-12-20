package test;

//import japa.parser.JavaParser;
//import japa.parser.ast.CompilationUnit;
//import japa.parser.ast.stmt.Statement;

//import gov.nasa.jpf.jdart.Debug;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import gov.nasa.jpf.symbc.Debug;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.net.URLCodec;


public class TestCommonsCodec {

	public static void main(String[] args) throws Exception{
		start();
//		List<String> S_list=ReadFromFile();
//		System.out.println(S_list.size());
//		for (String si : S_list) {
//			try{
//				System.out.println(si);
//				start(si);
//			}catch (Throwable e){
//				e.printStackTrace();
//			}
//		}
	}
	public static String parseName="javaparser";
	public static void start() throws Exception {
		String s="http://baidu.com?name=jkl";
//		char[] dd={99, 108, 97, 115, 115, 65, 123, 100, 111, 117, 98, 108, 101, 97, 61, 39, 64, 39, 61, 126,};
//		String s=new String(dd);
		
		char[] data=s.toCharArray();
		for(int i=0;i<s.length();i++){
			if(((char)data[i])!=' '){
//				data[i]=Debug.makeConcolicChar("sym_cnf_char"+i, ""+(int)data[i]);
				data[i]= Debug.makeSymbolicChar( ""+(int)data[i]);
				System.out.print(""+(int)data[i]+", ");
			}
		}
		System.out.println();
		s = new String(data);
		
		start(s);
	}
	
	public static void start(String s) throws Exception {
		URLCodec codec = new URLCodec();
        String encode = codec.encode(s);
        System.out.println(encode);
        String decode = codec.decode(encode);
        System.out.println(decode);
	}

    
	public static List<String> ReadFromFile() throws Exception{
		List<String> S_list= new ArrayList<String>();
		File file=new File("D:\\eclipse\\workplace\\rgse——xx\\jpf-jdart\\"+"javaparser.input");    
		if(file.exists()){
//			S_list=FileUtils.readLines(file);		
			ObjectInputStream osi=new ObjectInputStream(new FileInputStream(file));
			S_list=(ArrayList<String>) osi.readObject();
			osi.close();
		}

		return S_list;
	}
}
