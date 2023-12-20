package interpreter;


//import gov.nasa.jpf.jdart.Debug;

public class Tokenizer {

    public String input = "";
    public int pos = -1;
    public Token token = null;

    static boolean shouldBack = true;


    public Tokenizer(String src){
        this.input = src;
        this.pos = 0;
    }

    boolean next(){
        boolean spaceOccur = false;
        if (pos < this.input.length() - 1){
            pos ++ ;
            if (this.current() == ' ' && pos < this.input.length() - 1){
                pos ++;
                spaceOccur = true;
            }
            if (spaceOccur)
                return false;
            return true;
        }
        shouldBack = false;
        return false;
    }

    public char current(){
        return this.input.charAt(pos);
    }

    public boolean back(){
        if (this.pos > 0){
            this.pos--;
            return true;
        }
        return false;
    }

    boolean deleteSpace(){
        boolean spaceOccur = false;
        while (this.current() == ' ' && pos < this.input.length() - 1){
            pos ++;
            spaceOccur = true;
        }
        return spaceOccur;
    }

    private void isTokenNumber (TokenParams params){
        params.isToken = isTokenNumber(params.imageBuilder,params.curPos,params.isToken);
    }

    private void isTokenIdentifier (TokenParams params){
        isTokenIdentifier(params.imageBuilder,params.curPos, params.isToken);
    }

    private void isToken(TokenParams params, char literal, int tokenType){
        isToken(params.imageBuilder, literal, tokenType);
    }

    public static int token_index = 0;
    static boolean isFirstCall = false;

    public Token getNextToken() throws TokenizerException {
        TokenParams params = new TokenParams().invoke();

        isTokenNumber(params);

        isTokenIdentifier(params);

        isToken(params,'(', Token.LPAREN);

        isToken(params, ')', Token.RPAREN);

        isToken(params, ';', Token.SEMICOLON);

        try {
            finish(params);
        } catch (TokenizerException e) {
            throw e;
        }

        return token;
    }

    private void finish (TokenParams params) throws TokenizerException {
        finish(params.imageBuilder,params.curPos);
    }

    public Token getNextTokenOld() throws TokenizerException{
        TokenParams tokenParams = new TokenParams().invoke();
        char[] imageBuilder = tokenParams.getImageBuilder();
        int curPos = tokenParams.getCurPos();
        boolean isToken = tokenParams.isToken();

        isToken = isTokenNumber(imageBuilder, curPos, isToken);

        isTokenIdentifier(imageBuilder, curPos, isToken);

        isToken(imageBuilder, '(', Token.LPAREN);

        // )
        isToken(imageBuilder, ')', Token.RPAREN);

        // ;
        isToken(imageBuilder, ';', Token.SEMICOLON);

        if (finish(imageBuilder, curPos))
            return token;

        return token;
    }

    private boolean finish(char[] imageBuilder, int curPos) throws TokenizerException {
        int j = 0;
        int k = 0;
        for(j = curPos; j <= pos; j++){
            if(this.input.charAt(j)!=' '){
                imageBuilder[k] = this.input.charAt(j);
                k++;
            }
        }

        if (token == null){
//            token = new Token(Token.ERROR,"");
            throw new TokenizerException();
}

        imageBuilder[k] = '\0';
        token.image = new String(imageBuilder,0,k);

//        if(token != null){
//        	Debug.GenTokenStringByConcolic(""+token.kind,token.image);
//        }
//
//        if (Debug.isGenTokenStringByConcolic())
//		{
//			Debug.SystemExit();
//		}
//        if (Debug.isTokenSymb()){
//            Debug.cleanPC();
//        }
//
//        if (!Debug.isTokenSymb())
//            return true;
//
//        int c;
//        c = Debug.makeConcolicInteger("sym_token_" + token_index,
//				"" + (int) token.kind , "1");
//        token.kind = c;
//        token_index++;
        return false;
    }

    private void isToken(char[] imageBuilder, char c, int lparen) {
        // (
        if (token == null) {
            if (current() == c) {
//                imageBuilder.append(current());
                token = new Token(lparen, imageBuilder.toString());
            }
        }
    }

    private void isTokenIdentifier(char[] imageBuilder, int curPos, boolean isToken) {
        // identifier
        if (token == null){
            shouldBack = true;
            while (current() >= 'a' && current() <= 'z'
            || current() >= 'A' && current() <= 'Z')
            {
            	isToken = true;
//                imageBuilder.append(current());
                if (!this.next()){
                    break;
                }

            }
            if (isToken){
            	if (shouldBack
                && (curPos != this.pos || curPos == this.input.length()-1)){
    	            this.back();
    	        }
            	token = new Token(Token.IDENTIFIER,imageBuilder.toString());
            }
        }
    }

    private boolean isTokenNumber(char[] imageBuilder, int curPos, boolean isToken) {
        // number
        shouldBack = true;
        while (current() >= '0' && current() <= '9'){
        	isToken = true;
//            imageBuilder.append(current());
            if (!this.next()){
                break;
            }
        }
        if (isToken){
        	if (shouldBack
            && (curPos != this.pos || curPos == this.input.length()-1)){
	            this.back();
	        }

        	token = new Token(Token.NUMBER,imageBuilder.toString());
        }
        return isToken;
    }

    private class TokenParams {
        private char[] imageBuilder;
        private int curPos;
        private boolean isToken;

        public char[] getImageBuilder() {
            return imageBuilder;
        }

        public int getCurPos() {
            return curPos;
        }

        public boolean isToken() {
            return isToken;
        }

        public TokenParams invoke() {
            deleteSpace();

            imageBuilder = new char[100];
            curPos = Tokenizer.this.pos;
            isToken = false;
            token = null;
            return this;
        }
    }
}
