package test;

import java.io.*;

import com.amazon.ion.IonReader;
import com.amazon.ion.IonWriter;
import com.amazon.ion.system.IonReaderBuilder;
import com.amazon.ion.system.IonTextWriterBuilder;



public class TestInojava {

	public static void main(String[] args) throws Exception{
		String s = "{buzz: {{+AB/}}}";
		System.out.println(s);
		char[] data = s.toCharArray();
		start(data);
	}
	
	public static void start(char[] data) throws Exception {

		
//		File file=new File("src/example-ino-java/test.ino");
//		FileInputStream in=new FileInputStream(file);
//		int size=in.available();
//		byte[] buffer=new byte[size];
//		in.read(buffer);
//		in.close();
//		textIon=new String(buffer,"UTF-8");
		
		if(InojavaConfig.SYMB_FLAG){
//			if(InojavaConfig.TOKEN_SYMB){
//				Debug.setInput(textIon,"2");
//				data=textIon.toCharArray();
//				for(int i=0;i<data.length;++i){
//					System.out.print(""+(int)data[i]+", ");
//				}
//				System.out.println();
//			}else{
//				for(int i=0;i<s.length();i++){
//					if(((char)data[i])!=' '){
//						data[i]=Debug.makeConcolicChar("sym_cnf_char"+i, ""+(int)data[i]);
//						System.out.print(""+(int)data[i]+", ");
//					}
//				}
				System.out.println();
//				textIon = new String(data);
//			}
		}
		
		System.out.println(data);
		
		StringBuilder stringBuilder = new StringBuilder();
		IonWriter jsonWriter = IonTextWriterBuilder.json().withPrettyPrinting().build(stringBuilder);
		IonReader reader = IonReaderBuilder.standard().build(new CharArrayReader(data));
		jsonWriter.writeValues(reader);
	    
		System.out.println();
	    System.out.println(stringBuilder.toString());
    }

}
