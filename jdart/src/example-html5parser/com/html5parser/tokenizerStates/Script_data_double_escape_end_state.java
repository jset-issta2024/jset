package com.html5parser.tokenizerStates;

import com.html5parser.classes.ASCIICharacter;
import com.html5parser.classes.ParserContext;
import com.html5parser.classes.Token;
import com.html5parser.classes.Token.TokenType;
import com.html5parser.classes.TokenizerContext;
import com.html5parser.classes.TokenizerState;
import com.html5parser.factories.TokenizerStateFactory;
import com.html5parser.interfaces.ITokenizerState;

public class Script_data_double_escape_end_state implements ITokenizerState {

	public ParserContext process(ParserContext context) {
		TokenizerStateFactory factory = TokenizerStateFactory.getInstance();
		TokenizerContext tokenizerContext = context.getTokenizerContext();
		int currentChar = tokenizerContext.getCurrentInputCharacter();

		ASCIICharacter asciiCharacter = tokenizerContext
				.getCurrentASCIICharacter();
		
		
			context.addParseEvent("8.2.4.33", currentChar);

		switch (asciiCharacter) {

		case TAB:
		case LF:
		case FF:
		case SPACE:
		case DASH:
		case GREATER_THAN_SIGN:
			/*
			 * If the temporary buffer is the string "script", then switch to
			 * the script data escaped state. Otherwise, switch to the script
			 * data double escaped state. Emit the current input character as a
			 * character token.
			 */
			if (tokenizerContext.getTemporaryBuffer().equals("script")) {
				tokenizerContext.setNextState(factory
						.getState(TokenizerState.Script_data_escaped_state));
			} else {
				tokenizerContext
						.setNextState(factory
								.getState(TokenizerState.Script_data_double_escaped_state));
			}
			tokenizerContext.emitCurrentToken(new Token(TokenType.character,
					currentChar));
			break;
		case LATIN_CAPITAL_LETTER:
			/*
			 * Uppercase ASCII letter Append the lowercase version of the
			 * current input character (add 0x0020 to the character's code
			 * point) to the temporary buffer. Emit the current input character
			 * as a character token.
			 */
			tokenizerContext
					.appendCharacterToTemporaryBuffer(currentChar + 0x0020);
			tokenizerContext.emitCurrentToken(new Token(TokenType.character,
					currentChar));
			break;
		case LATIN_SMALL_LETTER:
			/*
			 * Append the current input character to the temporary buffer. Emit
			 * the current input character as a character token.
			 */
			tokenizerContext.appendCharacterToTemporaryBuffer(currentChar);
			tokenizerContext.emitCurrentToken(new Token(TokenType.character,
					currentChar));
			break;
		default:
			/*
			 * Switch to the script data double escaped state. Reconsume the
			 * current input character.
			 */
			tokenizerContext.setNextState(factory
					.getState(TokenizerState.Script_data_double_escaped_state));
			tokenizerContext.setFlagReconsumeCurrentInputCharacter(true);
			break;
		}

		context.setTokenizerContext(tokenizerContext);

		return context;
	}

}
