package test;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.StringReader;

import org.w3c.tidy.Tidy;

public class TestJtidyBug {
	public static char[] bug1 = {(int)60,(int)79,(int)128,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)45,(int)61,(int)38,(int)35,(int)70};
	public static void main(String[] args){
		ByteArrayOutputStream tidyOutStream; //输出
		String s = new String(bug1);
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

	        tidy.parse(new StringReader(s), tidyOutStream);
	       
	        //System.out.println(tidyOutStream.toString());
	    }
	    catch ( Exception ex ){
//	            System.out.println( ex.toString());
//	            ex.printStackTrace();
	    }
	}
}
