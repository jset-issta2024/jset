package test;

//import gov.nasa.jpf.jdart.Debug;

import java.io.*;

import org.w3c.tidy.Tidy;

public class TestJtidy {
	public static void main(String[] args) throws Exception{
		start();          
	}
	public static void start() throws Exception{
	    ByteArrayOutputStream tidyOutStream; //输出
	    String s = "<html><head><title>First parse</titile></head>"
	    		+"<body><p>Parsed HTML into a doc.</p></body></html>";
	    char[] data; 
		data=s.toCharArray(); 
//		for(int i=0;i<s.length();i++){
//			if(((char)data[i])!=' '){
//				data[i]=Debug.makeConcolicChar("sym_cnf_char"+i, ""+(int)data[i]);
//			}
//		}
		String str = new String(data);
	
	    try{           
	        /*
	    	ByteArrayOutputStream  bos  =  new  ByteArrayOutputStream();  
	        bos.write(str.getBytes());
	        
	        byte[]  bs  =  bos.toByteArray();  
	        bos.close();
	        String hope_gb2312=new String(bs,"GB2312");//注意，默认是GB2312，所以这里先转化成GB2312然后再转化成其他的。
	        byte[] hope_b=hope_gb2312.getBytes();
	        String basil =new String(hope_b,"utf-8");//将GB2312转化成UTF-8            
	        ByteArrayInputStream stream = new ByteArrayInputStream(basil.getBytes());*/
	        tidyOutStream = new ByteArrayOutputStream();        
	        Tidy tidy = new Tidy();
	        tidy.setInputEncoding("UTF-8");
	        tidy.setQuiet(true);                   
	        tidy.setOutputEncoding("UTF-8");          
	        tidy.setShowWarnings(false); //不显示警告信息
	        tidy.setIndentContent(true);//
	        tidy.setSmartIndent(true);
	        tidy.setIndentAttributes(false);
	        tidy.setWraplen(1024); //多长换行
	        //输出为xhtml
	        tidy.setXHTML(true);
	        tidy.setErrout(new PrintWriter(System.out));

	        tidy.parse(new StringReader(str), tidyOutStream);
	       
	        //System.out.println(tidyOutStream.toString());
	    }
	    catch ( Exception ex ){
	            System.out.println( ex.toString());
	            ex.printStackTrace();
	    }
	}
}
