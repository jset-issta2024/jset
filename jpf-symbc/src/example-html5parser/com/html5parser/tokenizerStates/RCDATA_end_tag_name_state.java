package com.html5parser.tokenizerStates;

import com.html5parser.classes.ASCIICharacter;
import com.html5parser.classes.ParserContext;
import com.html5parser.classes.Token;
import com.html5parser.classes.Token.TokenType;
import com.html5parser.classes.TokenizerContext;
import com.html5parser.classes.TokenizerState;
import com.html5parser.factories.TokenizerStateFactory;
import com.html5parser.interfaces.ITokenizerState;

public class RCDATA_end_tag_name_state implements ITokenizerState {

	public ParserContext process(ParserContext context) {
		TokenizerStateFactory factory = TokenizerStateFactory.getInstance();
		TokenizerContext tokenizerContext = context.getTokenizerContext();
		Token currentToken = tokenizerContext.getCurrentToken();
		int currentChar = tokenizerContext.getCurrentInputCharacter();

		ASCIICharacter asciiCharacter = tokenizerContext
				.getCurrentASCIICharacter();

		
			context.addParseEvent("8.2.4.13", currentChar);
		
		switch (asciiCharacter) {

		case TAB:
		case LF:
		case FF:
		case SPACE:
			/*
			 * If the current end tag token is an appropriate end tag token,
			 * then switch to the before attribute name state. Otherwise, treat
			 * it as per the "anything else" entry below.
			 */
			if (currentToken.getValue().equals(
					tokenizerContext.getLatestEmittedStartTag())) {
				tokenizerContext.setNextState(factory
						.getState(TokenizerState.Before_attribute_name_state));
			} else {
				defaultProcess(tokenizerContext);
			}
			break;
		case DASH:
			/*
			 * If the current end tag token is an appropriate end tag token,
			 * then switch to the self-closing start tag state. Otherwise, treat
			 * it as per the "anything else" entry below.
			 */
			if (currentToken.getValue().equals(
					tokenizerContext.getLatestEmittedStartTag())) {
				tokenizerContext.setNextState(factory
						.getState(TokenizerState.Self_closing_start_tag_state));
			} else {
				defaultProcess(tokenizerContext);
			}
			break;
		case GREATER_THAN_SIGN:
			/*
			 * If the current end tag token is an appropriate end tag token,
			 * then switch to the data state and emit the current tag token.
			 * Otherwise, treat it as per the "anything else" entry below.
			 */
			if (currentToken.getValue().equals(
					tokenizerContext.getLatestEmittedStartTag())) {
				tokenizerContext.setNextState(factory
						.getState(TokenizerState.Data_state));
				tokenizerContext.setFlagEmitToken(true);
			} else {
				defaultProcess(tokenizerContext);
			}
			break;
		case LATIN_CAPITAL_LETTER:
			/*
			 * Append the lowercase version of the current input character (add
			 * 0x0020 to the character's code point) to the current tag token's
			 * tag name. Append the current input character to the temporary
			 * buffer.
			 */
			currentToken.appendValue(currentChar + 0x0020);
			tokenizerContext.appendCharacterToTemporaryBuffer(currentChar);
			break;

		case LATIN_SMALL_LETTER:
			/*
			 * Append the lowercase version of the current input character (add
			 * 0x0020 to the character's code point) to the current tag token's
			 * tag name. Append the current input character to the temporary
			 * buffer.
			 */
			currentToken.appendValue(currentChar);
			tokenizerContext.appendCharacterToTemporaryBuffer(currentChar);
			break;
		default:
			defaultProcess(tokenizerContext);
			break;
		}

		context.setTokenizerContext(tokenizerContext);

		return context;
	}

	/*
	 * Switch to the RCDATA state. Emit a U+003C LESS-THAN SIGN character token,
	 * a U+002F SOLIDUS character token, and a character token for each of the
	 * characters in the temporary buffer (in the order they were added to the
	 * buffer). Reconsume the current input character.
	 */
	private void defaultProcess(TokenizerContext tokenizerContext) {
		TokenizerStateFactory factory = TokenizerStateFactory.getInstance();
		tokenizerContext.setNextState(factory
				.getState(TokenizerState.RCDATA_state));
		tokenizerContext
				.emitCurrentToken(new Token(TokenType.character, 0x003C));
		tokenizerContext
				.emitCurrentToken(new Token(TokenType.character, 0x002F));
		String temporaryBuffer = tokenizerContext.getTemporaryBuffer();
		for (int i = 0; i < temporaryBuffer.length(); i++) {
			char a = temporaryBuffer.charAt(i);
			tokenizerContext
					.emitCurrentToken(new Token(TokenType.character, a));
		}
		tokenizerContext.setFlagReconsumeCurrentInputCharacter(true);
	}
}
