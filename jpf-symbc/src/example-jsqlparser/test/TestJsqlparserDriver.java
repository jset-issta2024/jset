package test;

import gov.nasa.jpf.symbc.Debug;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.parser.ParseException;
import net.sf.jsqlparser.parser.TokenMgrError;
import net.sf.jsqlparser.statement.Statements;

public class TestJsqlparserDriver {

	public static void main(String[] args) throws JSQLParserException {
		start();
	}

	public static void start() throws JSQLParserException {
		String s = "SELECT * FROM Orders WHERE OrderDate='2008-12-26'";

//		File file=new File("src/example-jsqlparser/test.sql.in");
//		FileInputStream in=new FileInputStream(file);
//		int size=in.available();
//		byte[] buffer=new byte[size];
//		in.read(buffer);
//		in.close();
//		s=new String(buffer,"UTF-8");

		char[] data;
		data=s.toCharArray();
		for(int i=0;i<s.length();i++){
			if(((char)data[i])!=' '){
//						data[i]=Debug.makeConcolicChar("sym_cnf_char"+i, ""+(int)data[i]);
				data[i]= Debug.makeSymbolicChar(""+(int)data[i]);
				System.out.print(""+(int)data[i]+", ");
			}
		}

		if(data[0] == '0') {
			Debug.printPC("t1 if");
		}
		else {
			Debug.printPC("t1 else");
		}

		s = new String(data);
		System.out.println(s);

		if(s.equals("test")) {
			Debug.printPC("t if");
		}
		else {
			Debug.printPC("t else");
		}

		try {
			Statements stmt = CCJSqlParserUtil.parseStatements(s);
		} catch (JSQLParserException e) {

		} catch (TokenMgrError er) {

		}

	}

}
