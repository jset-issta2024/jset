package sie;

import java.io.DataInputStream;
public class Scanner {  
	private char ch = ' ';  
	private char ident = ' ';  
	private int intValue = 0;  
	private Buffer buffer;  
	public int token;  
	public static char last_char;

	public Scanner (DataInputStream in) {  
		buffer = new Buffer(in);  
		token = Token.semicolon;  
		last_char=Token.toString(Token.semicolon).charAt(0);
	} // Scanner  

	
	boolean isWhitespace(char ch){
		if(ch == ' ' )
//				|| ch == '\n' || ch == '\r' || ch=='\t')
			return true;
		
		if(ch == '\n')
			return true;
		return false;
	}
	
	boolean isDigit(char ch){
		if(ch=='0'||ch=='1')
			return true;
		return false;
	}
	
	static int token_index=0;
	public int getToken ( ) throws Exception {  
//		while (Character.isWhitespace(ch))  
//			ch = buffer.get( );  
 		while (isWhitespace(ch))  
			ch = buffer.get( );
//		if (Character.isLetter(ch)) {  
//			ident = Character.toLowerCase(ch);  
//			ch = buffer.get( );  
//			token = Token.letter;  
//		}  
//		else if (Character.isDigit(ch)) {  
		
		if(ch==';')
			token = Token.semicolon;  
		else if(ch=='.')
			token = Token.period;
		else if(ch>='*'&& ch<='+'){
			Expr.ops.add(new Node(ch));
			token = Token.op;  
		}
		else if (isDigit(ch)) {  
			intValue = getNumber( );  
			token = Token.number;  
		}   
		else if(ch=='\0'){
			token = Token.EOF;
//			System.out.println("EOF !!!!!!!!!!!");
			return token;
		}
		else
			error ("Illegal character " + ch );  
//		Debug.printCurrentPC();
		
		ch = buffer.get( );

		token_index++;
		
		return token;  
	} // getToken  


	public int number ( ) {  
		return intValue;  
	} // number  


	public char letter ( ) {  
		return ident;  
	} // letter  


	public void match (int which) throws Exception {  
		token = getToken( );  
		if (token != which) {  
			error("Invalid token " + Token.toString(token) +  
					"-- expecting " + Token.toString(which));  
//			System.exit(1);  
		} // if  
	} // match  


	public void error (String msg) throws Exception {  
//		System.err.println(msg);  
//		System.exit(1);  
		throw new Exception(msg);
	} // error  


	private int getNumber ( ) throws Exception {  
		Expr.numbers.add(new Node(ch));
//		Debug.printCurrentPC();
//		if(Expr.numbers.get(0).data==9){
//			
//		}
//		Debug.printCurrentPC();
		return Character.digit(ch, 10);  
	} // getNumber  

} // Scanner  

class Buffer {  
	private String line = "";  
	private int column = 0;  
	private int lineNo = 0;  
	private DataInputStream  in;  

	public Buffer (DataInputStream in) {  
		this.in = in;  
	} // Buffer  

	public static int char_index=0; 
	public char get ( ) throws Exception {  
		column++;  
		if (column >= line.length()) {  
			try {  
				line = in.readLine( );  
			} catch (Exception e) {  
				System.err.println("Invalid read operation");  
				System.exit(1);  
			} // try  
			if (line == null){
//				Debug.printCurrentPC();
//				throw new Exception();

				return '\0';
			}
				 
			column = 0;  
			lineNo++;  
//			System.out.println(line);  
			line = line + "\n";  
		} // if column  
		char c=line.charAt(column);
		char a=c;

		Scanner.last_char=a;
		System.out.print(a);
		char_index++;
		return a;
	} // get  

} // class Buffer  

