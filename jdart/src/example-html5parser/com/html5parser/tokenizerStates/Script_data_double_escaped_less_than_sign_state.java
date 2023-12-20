package com.html5parser.tokenizerStates;

import com.html5parser.classes.ASCIICharacter;
import com.html5parser.classes.ParserContext;
import com.html5parser.classes.Token;
import com.html5parser.classes.Token.TokenType;
import com.html5parser.classes.TokenizerContext;
import com.html5parser.classes.TokenizerState;
import com.html5parser.factories.TokenizerStateFactory;
import com.html5parser.interfaces.ITokenizerState;

public class Script_data_double_escaped_less_than_sign_state implements
		ITokenizerState {

	public ParserContext process(ParserContext context) {
		TokenizerStateFactory factory = TokenizerStateFactory.getInstance();
		TokenizerContext tokenizerContext = context.getTokenizerContext();
		int currentChar = tokenizerContext.getCurrentInputCharacter();
		ASCIICharacter asciiCharacter = tokenizerContext
				.getCurrentASCIICharacter();
		
		
			context.addParseEvent("8.2.4.32", currentChar);

		switch (asciiCharacter) {
		case DASH:
			/*
			 * Set the temporary buffer to the empty string. Switch to the
			 * script data double escape end state. Emit a U+002F SOLIDUS
			 * character token.
			 */
			tokenizerContext.setTemporaryBuffer("");
			tokenizerContext
					.setNextState(factory
							.getState(TokenizerState.Script_data_double_escape_end_state));
			tokenizerContext.emitCurrentToken(new Token(TokenType.character,
					0x002F));
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
