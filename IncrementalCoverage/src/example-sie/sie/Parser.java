package sie;


import java.io.*;  


/* This program illustrates recursive descent parsing using a 
   pure procedural approach. 

   The grammar: 
   //   expression = term { ( "+" | "-" ) term } 
//   term      = factor { ( "*" | "/" ) factor } 
   

   statement = { expression  ";" } "."   
   expression = factor {( "+" ) factor}
   factor    = ( "0" | "1" ) 

 */  


public class Parser {  

	private Scanner scanner;  


	public Parser(Scanner scanner) {  
		this.scanner = scanner;  
	} // Parser  


	public void run ( ) throws Exception {  
//		scanner.getToken( );  
		statement( );  
	} // run  


	private void statement ( ) throws Exception {  
		//   statement = { expression  ";" } "."    
		//replaced by: statement = { expression ";" } <EOF>
		do{  
			scanner.getToken();
			int value = expression( );
			System.out.println("=> " + value);  
//			scanner.getToken( );  // flush ";"  
		}while(scanner.token == Token.semicolon) ; // while  
		if(scanner.token != Token.EOF)
			throw new Exception("Expect EOF !!! ");
	} // statement  

	
	private int expression ( ) throws Exception {  
		//    expression = factor { ( "+" | "-" ) factor }  
		int left = factor( );  
		while (scanner.token == Token.op ){
			int saveToken = scanner.token;  
			scanner.getToken( );  
			switch (saveToken) {  
			case Token.op:  
				left += factor( );
//				left=op_cal(left,saveChar);
				break;  
			} // switch  
		} // while  
		return left;  
	} // expression  
	
	int op_cal(int left, char op) throws Exception{
		if(op=='*')
			return left*factor();
		else 
			return left+factor();
	}


	private int term ( ) throws Exception {  
		//    term = factor { ( "*" | "/" ) factor }  
		int left = factor( );  
		while (scanner.token == Token.timesop ||   
				scanner.token == Token.divideop) {  
			int saveToken = scanner.token;  
			scanner.getToken( );  
			switch (saveToken) {  
			case Token.timesop:  
				left *= factor( );  
				break;  
			case Token.divideop:  
				left /= factor( );  
				break;  
			} // switch  
		} // while  
		return left;  
	} // term  


//	private int factor ( ) {  
//		//    factor    = number | "(" expression ")"  
//		int value = 0;  
//		switch (scanner.token) {  
//		case Token.number:  
//			value = scanner.number( );  
//			scanner.getToken( );  // flush number  
//			break;  
//		case Token.lparen:  
//			scanner.getToken( );  
//			value = expression( );  
//			if (scanner.token != Token.rparen)  
//				scanner.error("Missing ')'");  
//			scanner.getToken( );  // flush ")"  
//			break;  
//		default:  
//			scanner.error("Expecting number or (");  
//			break;  
//		} // switch  
//		return value;  
//	} // factor  
	
	private int factor ( ) throws Exception {  
		//    factor    = number | "(" expression ")"  
		int value = 0;  
		if(scanner.token==Token.number){
			value = scanner.number( );  
			scanner.getToken( );  // flush number  
		}else{
			scanner.error("Expecting number or (");  
		}
		
//		switch (scanner.token) {  
//		case Token.number:  
//			value = scanner.number( );  
//			scanner.getToken( );  // flush number  
//			break;   
//		default:  
//			scanner.error("Expecting number or (");  
//			break;  
//		} // switch  
		return value;  
	} // factor  

} // class Parser  

