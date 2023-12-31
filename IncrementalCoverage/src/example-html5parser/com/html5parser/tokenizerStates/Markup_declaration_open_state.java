package com.html5parser.tokenizerStates;

import com.html5dom.Element;

import com.html5parser.classes.ParserContext;
import com.html5parser.classes.Token;
import com.html5parser.classes.Token.TokenType;
import com.html5parser.classes.TokenizerContext;
import com.html5parser.classes.TokenizerState;
import com.html5parser.constants.Namespace;
import com.html5parser.factories.TokenizerStateFactory;
import com.html5parser.interfaces.ITokenizerState;
import com.html5parser.tracer.ParseError.ParseErrorType;

public class Markup_declaration_open_state implements ITokenizerState {

	private String temp = "";

	public ParserContext process(ParserContext context) {
		TokenizerStateFactory factory = TokenizerStateFactory.getInstance();
		TokenizerContext tokenizerContext = context.getTokenizerContext();
		int currentChar = tokenizerContext.getCurrentInputCharacter();
		Token bogusCommentToken;

		
			context.addParseEvent("8.2.4.45", currentChar);
		
		/*
		 * If the next two characters are both "-" (U+002D) characters, consume
		 * those two characters, create a comment token whose data is the empty
		 * string, and switch to the comment start state.
		 * 
		 * Otherwise, if the next seven characters are an ASCII case-insensitive
		 * match for the word "DOCTYPE", then consume those characters and
		 * switch to the DOCTYPE state.
		 * 
		 * Otherwise, if there is a current node and it is not an element in the
		 * HTML namespace and the next seven characters are a case-sensitive
		 * match for the string "[CDATA[" (the five uppercase letters "CDATA"
		 * with a U+005B LEFT SQUARE BRACKET character before and after), then
		 * consume those characters and switch to the CDATA section state.
		 * 
		 * Otherwise, this is a parse error. Switch to the bogus comment state.
		 * The next character that is consumed, if any, is the first character
		 * that will be in the comment.
		 */

		// Emulation of Bogus_comment_state
		switch (tokenizerContext.getCurrentASCIICharacter()) {
		// U+0000 NULL characters replaced by U+FFFD
		case NULL:
			appendCharToTemp(0xFFFD);
			break;
		// If the end of the file was reached, reconsume the EOF character.
		case EOF:
			tokenizerContext.setFlagReconsumeCurrentInputCharacter(true);
			// tokenizerContext.setFlagEmitToken(true);
			// tokenizerContext.setNextState(factory
			// .getState(TokenizerState.Data_state));
			// bogusCommentToken = new Token(TokenType.comment, temp);
			// tokenizerContext.emitCurrentToken(bogusCommentToken);
			// break;
		case GREATER_THAN_SIGN:
			// tokenizerContext.setFlagEmitToken(true);
			tokenizerContext.setNextState(factory
					.getState(TokenizerState.Data_state));
			// the token is empty if it was generated by the string "<!>"
			// if (!temp.equals(""))
			// appendCharToTemp(0x003E);
			bogusCommentToken = new Token(TokenType.comment, temp);
			tokenizerContext.emitCurrentToken(bogusCommentToken);
			if (!temp.isEmpty())
				context.addParseErrors(
						ParseErrorType.UnexpectedInputCharacter,
						"Unexpected character: " + temp.charAt(0) + " ("
								+ temp.codePointAt(0) + ") at "
								+ context.getTokenizerContext().getNextState());
			else
				context.addParseErrors(ParseErrorType.UnexpectedInputCharacter);
			break;

		// Consume every character
		default:
			appendCharToTemp(currentChar);

			if (temp.equals("--")) {
				Token commentToken = new Token(TokenType.comment, "");
				tokenizerContext.setCurrentToken(commentToken);
				tokenizerContext.setNextState(factory
						.getState(TokenizerState.Comment_start_state));
			} else if (temp.toUpperCase().equals("DOCTYPE")) {
				tokenizerContext.setNextState(factory
						.getState(TokenizerState.DOCTYPE_state));
			} else if (temp.equals("[CDATA[")) {
				Element currentNode = context.getCurrentNode();
				if (currentNode != null
						&& currentNode.getNamespaceURI() != Namespace.HTML) {
					tokenizerContext.setNextState(factory
							.getState(TokenizerState.CDATA_section_state));

				}
			} else {

				// If not remains in the same state it means a parse error, then
				// go
				// to Bogus_comment_state
				if (!tokenizerContext.getNextState().equals(this)
						&& !temp.isEmpty()) {
					tokenizerContext.setNextState(factory
							.getState(TokenizerState.Bogus_comment_state));
					context.addParseErrors(
							ParseErrorType.UnexpectedInputCharacter,
							"Unexpected character: "
									+ temp.charAt(0)
									+ " ("
									+ temp.codePointAt(0)
									+ ") at "
									+ context.getTokenizerContext()
											.getNextState());
					// Create a comment token for the Bogus_comment_state
					bogusCommentToken = new Token(TokenType.comment, temp);
					tokenizerContext.setCurrentToken(bogusCommentToken);
				}
			}

			break;
		}

		context.setTokenizerContext(tokenizerContext);
		return context;
	}

	private void appendCharToTemp(int value) {
		if (this.temp == null)
			this.temp = "";
		this.temp = this.temp.concat(String.valueOf(Character.toChars(value)));
	}
}