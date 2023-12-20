package com.html5parser.classes;

import test.Html5parserConfig;
//import test.JavaparserConfig;
//import gov.nasa.jpf.jdart.Debug;

public class Token {

	public enum TokenType {
		DOCTYPE, start_tag, end_tag, comment, character, end_of_file
	}

	private String value;
	private TokenType type;

	/**
	 * Create a new instance of a Token object.
	 */
	public Token() {

	}

	/**
	 * Create a new instance of a Token object.
	 * 
	 * @param _type
	 *            The token type (e.g. DOCTYPE, start_tag, end_tag, comment,
	 *            character, end_of_file).
	 * @param _value
	 *            The value of the token as a string.
	 */
	public Token(Token.TokenType _type, String _value) {
		this.setType(_type);
		this.setValue(_value);
		if(Html5parserConfig.SYMB_FLAG==false)
			System.out.println("Type: "+_type+"  value: "+_value);
	}

	public Token(Token.TokenType _type, int _value) {
		this.setType(_type);
		this.setValue(_value);
		if(Html5parserConfig.SYMB_FLAG==false)
			System.out.println("Type: "+_type+"  value: "+this.value);
	}

	public TokenType getType() {
		return type;
	}
	
	//for token symbolic
	public static int token_index = 0;
	public static int oldtoken = -1;
	public void setType(TokenType type) {
		this.type = type;
		
		if(Html5parserConfig.SYMB_FLAG==false || Html5parserConfig.TOKEN_SYMB==false){
			return ;
		}
		
		if(type==Token.TokenType.end_of_file){
			return;
		}
//		if (oldtoken == type.ordinal() ) {
////			c = Debug.makeConcolicInteger("sym_token_" + (token_index - 1),
////					"" + (int) matchedToken.kind , "1");
//			Debug.makeConcolicEnum("sym_token_" + (token_index - 1), this.type);
//		} else {
//			//			System.out.println("start to create: " + pos + ":" + token + ":" +token_index);
////			c = Debug.makeConcolicInteger("sym_token_" + token_index, ""
////					+ (int) matchedToken.kind , "1");
//			Debug.makeConcolicEnum("sym_token_" + (token_index - 1), this.type);
//			token_index++;
//			oldtoken = type.ordinal();
//		}
//		matchedToken.kind=c;
		
//		Debug.makeConcolicEnum("sym_token_"+token_index, this.type);
		token_index++;
	}

	public String getValue() {
		return value;
	}
	
	public int getIntValue() {
		return value.codePointAt(0);
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setValue(int value) {
		this.value = String.valueOf(Character.toChars(value));
	}

	public void appendValue(int value) {
		this.value = this.value
				.concat(String.valueOf(Character.toChars(value)));
	}

	public void appendValue(String value) {
		this.value = this.value.concat(value);
	}

	public Boolean isSpaceCharacter() {
		if (this.type == TokenType.character
				&& (value.codePointAt(0) == 0x0009
						|| value.codePointAt(0) == 0x000A
						|| value.codePointAt(0) == 0x000C
						|| value.codePointAt(0) == 0x000D 
						|| value.codePointAt(0) == 0x0020))
			return true;

		return false;
	}
}
