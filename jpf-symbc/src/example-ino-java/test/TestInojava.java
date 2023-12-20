package test;

//import gov.nasa.jpf.jdart.Debug;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.amazon.ion.IonReader;
import com.amazon.ion.IonWriter;
import com.amazon.ion.impl.IonReaderTextRawTokensX;
import com.amazon.ion.system.IonReaderBuilder;
import com.amazon.ion.system.IonTextWriterBuilder;
import gov.nasa.jpf.symbc.Debug;


public class TestInojava {

	public static void main(String[] args) throws Exception{
		try {
			start();
		}catch (Exception e) {
			if (e instanceof IonReaderTextRawTokensX.IonReaderTextTokenException) {

			}
			else {
				System.out.println("!!!BUG!!!");
				throw e;
			}
		}

	}
	
	public static void start() throws Exception {
		String textIon = "{buzz: {{+AB/}}}";
		
//		File file=new File("src/example-ino-java/test.ino");
//		FileInputStream in=new FileInputStream(file);
//		int size=in.available();
//		byte[] buffer=new byte[size];
//		in.read(buffer);
//		in.close();
//		textIon=new String(buffer,"UTF-8");
		
		if(InojavaConfig.SYMB_FLAG){
			char[] data; 
//			if(InojavaConfig.TOKEN_SYMB){
//				Debug.setInput(textIon,"2");
//				data=textIon.toCharArray();
//				for(int i=0;i<data.length;++i){
//					System.out.print(""+(int)data[i]+", ");
//				}
//				System.out.println();
//			}else{
				String s=textIon;
				data=s.toCharArray(); 
				for(int i=0;i<s.length();i++){ 
					if(((char)data[i])!=' '){ 
//						data[i]= Debug.makeConcolicChar("sym_cnf_char"+i, ""+(int)data[i]);
						data[i]= Debug.makeSymbolicChar(""+(int)data[i]);
						System.out.print(""+(int)data[i]+", ");
					} 
				} 
				System.out.println();
				textIon = new String(data);
//			}
		}
		
		System.out.println(textIon);
		
		StringBuilder stringBuilder = new StringBuilder();
		IonWriter jsonWriter = IonTextWriterBuilder.json().withPrettyPrinting().build(stringBuilder);
		IonReader reader = IonReaderBuilder.standard().build(textIon);
		jsonWriter.writeValues(reader);
	    
		System.out.println();
	    System.out.println(stringBuilder.toString());
    }

}
