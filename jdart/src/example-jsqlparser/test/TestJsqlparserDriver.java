package test;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statements;

public class TestJsqlparserDriver {

	public static void main(String[] args) throws Exception{
		String s = "SELECT * FROM Orders WHERE OrderDate='2008-12-26'";
		char[] data = s.toCharArray();
		start(data);
	}
	
	public static void start(char[] data) throws Exception {

//		File file=new File("src/example-jsqlparser/test.sql.in");
//		FileInputStream in=new FileInputStream(file);
//		int size=in.available();
//		byte[] buffer=new byte[size];
//		in.read(buffer);
//		in.close();
//		s=new String(buffer,"UTF-8");

		try {
			String s = data.toString();
			System.out.println(s);
			Statements stmt = CCJSqlParserUtil.parseStatements(s);
		} catch (JSQLParserException e) {
			e.printStackTrace();
		}

    }

}
