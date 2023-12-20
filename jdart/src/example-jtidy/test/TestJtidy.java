package test;

import java.io.*;

import org.w3c.tidy.Tidy;

public class TestJtidy {

	int temp;
	double t1;

	public static void main(String[] args) throws Exception{

		char[] bug1 = {(int)60,(int)79,(int)128,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)10,(int)45,(int)61,(int)38,(int)35,(int)70};
		String bugString = new String(bug1);

		String s = "<O\u0080\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"\n" +
				"-=&#F\n" +
				"\n" +
				"Process finished with exit code 0\n";
		System.out.println(bugString);

		start(s.toCharArray());

//		String s = "<html><head><title>First parse</titile></head>"
//				+"<body><p>Parsed HTML into a doc.</p></body></html>";
//		start(s.toCharArray());
	}
	public static void start(char[] data) throws Exception{
	    ByteArrayOutputStream tidyOutStream; //输出


	
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

	        tidy.parse(new CharArrayReader(data), tidyOutStream);
	       
	        //System.out.println(tidyOutStream.toString());
	    }
	    catch ( Exception ex ){
	            System.out.println( ex.toString());
	            ex.printStackTrace();
	    }
	}
}
