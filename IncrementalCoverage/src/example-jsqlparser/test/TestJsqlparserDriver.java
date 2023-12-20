package test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.Statements;




public class TestJsqlparserDriver {

	public static void main(String[] args) throws Exception{
		start();
	}
	
	public static void start() throws Exception {
//		String s = "SELECT * FROM Orders WHERE OrderDate='2008-12-26'";
		String s = "SELECT * FROM E@ WHERE E@ -> '2008-12-26'";

//		File file=new File("src/example-jsqlparser/test.sql.in");
//		FileInputStream in=new FileInputStream(file);
//		int size=in.available();
//		byte[] buffer=new byte[size];
//		in.read(buffer);
//		in.close();
//		s=new String(buffer,"UTF-8");
		
//		if(JsqlparserConfig.SYMB_FLAG){
//			char[] data;
//			if(JsqlparserConfig.TOKEN_SYMB){
//				Debug.setInput(s, "4");
//				data=s.toCharArray();
//				for(int i=0;i<data.length;++i){
//					System.out.print(""+(int)data[i]+", ");
//				}
//				System.out.println();
//			}else{
//				data=s.toCharArray();
//				for(int i=0;i<s.length();i++){
//					if(((char)data[i])!=' '){
//						data[i]=Debug.makeConcolicChar("sym_cnf_char"+i, ""+(int)data[i]);
//						System.out.print(""+(int)data[i]+", ");
//					}
//				}
//				System.out.println();
//				s = new String(data);
//			}
//		}
		System.out.println(s);
		
		Statements stmt = CCJSqlParserUtil.parseStatements(s);

    }

}
