package interpreter;

//import gov.nasa.jpf.jdart.Debug;

public class Parser {
    public Tokenizer tokenizer = null;
    public Node init = new Node();

    public Parser(Tokenizer tokenizer){
        this.tokenizer = tokenizer;
    }
    
    /*
     * expression -> id; | id(number) 
     * */
    
    public Node parse() throws ParseException, TokenizerException {
    	tokenizer.getNextToken();
        if (consumeToken(Token.IDENTIFIER)){
            init.data = tokenizer.token.image;
            tokenizer.next();
            tokenizer.getNextToken();
            if (tokenizer.token.kind == Token.LPAREN && consumeToken(Token.LPAREN)){
                tokenizer.next();
                tokenizer.getNextToken();
                if (consumeToken(Token.NUMBER)){
                    tokenizer.next();
                    tokenizer.getNextToken();
                    if (consumeToken(Token.RPAREN)){
                        System.out.println("parse success!!");
                    }
                }
            }else if (consumeToken(Token.SEMICOLON)){
                System.out.println("parse success!!");
            }
        }else{
        	System.out.println("Parse Fail!!");
        	throw new ParseException(this.tokenizer.token.kind, Token.IDENTIFIER);
        }
        
        return init;
    }

    boolean consumeToken (int tokenKind) throws ParseException {
    	System.out.println(tokenKind+" , "+this.tokenizer.token.kind);
        if (tokenKind == this.tokenizer.token.kind)
            return true;
        System.out.println("Parse Fail!!");
        throw new ParseException(this.tokenizer.token.kind, tokenKind);
    }

    public boolean semanticCheck(){
    	// 
//    	Debug.printCurrentPC();
//        char[] data = init.data.toCharArray();
        int i = 0;
        while (init.data.charAt(i)>='A' && init.data.charAt(i)<='Z'){
            i++;
            if (i>=init.data.length())
                break;
        }
//        Debug.printCurrentPC();
        return i>=init.data.length();
    }
}
