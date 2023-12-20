package com.html5parser.tokenizerStates;

import com.html5parser.classes.ASCIICharacter;
import com.html5parser.classes.ParserContext;
import com.html5parser.classes.Token;
import com.html5parser.classes.Token.TokenType;
import com.html5parser.classes.TokenizerContext;
import com.html5parser.classes.TokenizerState;
import com.html5parser.factories.TokenizerStateFactory;
import com.html5parser.interfaces.ITokenizerState;

public class Script_data_less_than_sign_state implements ITokenizerState {

	public ParserContext process(ParserContext context) {
		TokenizerStateFactory factory = TokenizerStateFactory.getInstance();
		TokenizerContext tokenizerContext = context.getTokenizerContext();
		int currentChar = tokenizerContext.getCurrentInputCharacter();
		ASCIICharacter asciiCharacter = tokenizerContext
				.getCurrentASCIICharacter();

		
			context.addParseEvent("8.2.4.17", currentChar);
		
		switch (asciiCharacter) {
		case DASH:
			/*
			 * Set the temporary buffer to the empty string. Switch to the
			 * script data end tag open state.
			 */
			tokenizerContext.setTemporaryBuffer("");
			tokenizerContext.setNextState(factory
					.getState(TokenizerState.Script_data_end_tag_open_state));
			break;
		case EXCLAMATION_MARK:
			/*
			 * Switch to the script data escape start state. Emit a U+003C
			 * LESS-THAN SIGN character token and a U+0021 EXCLAMATION MARK
			 * character token.
			 */
			tokenizerContext.setNextState(factory
					.getState(TokenizerState.Script_data_escape_start_state));
			tokenizerContext.emitCurrentToken(new Token(TokenType.character,
					0x003C));
			tokenizerContext.emitCurrentToken(new Token(TokenType.character,
					0x0021));
			break;
		default:
			/*
			 * Switch to the script data state. Emit a U+003C LESS-THAN SIGN
			 * character token. Reconsume the current input character.
			 */
			tokenizerContext.setNextState(factory
					.getState(TokenizerState.Script_data_state));
			tokenizerContext.emitCurrentToken(new Token(TokenType.character,
					0x003C));
			tokenizerContext.setFlagReconsumeCurrentInputCharacter(true);
			break;

		}

		context.setTokenizerContext(tokenizerContext);
		return context;
	}

}
