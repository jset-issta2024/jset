package com.html5parser.tokenizerStates;

import com.html5parser.classes.ASCIICharacter;
import com.html5parser.classes.ParserContext;
import com.html5parser.classes.Token;
import com.html5parser.classes.Token.TokenType;
import com.html5parser.classes.TokenizerContext;
import com.html5parser.classes.TokenizerState;
import com.html5parser.factories.TokenizerStateFactory;
import com.html5parser.interfaces.ITokenizerState;
import com.html5parser.tracer.ParseError.ParseErrorType;

public class Script_data_escaped_state implements ITokenizerState {

	public ParserContext process(ParserContext context) {
		TokenizerStateFactory factory = TokenizerStateFactory.getInstance();
		TokenizerContext tokenizerContext = context.getTokenizerContext();
		int currentChar = tokenizerContext.getCurrentInputCharacter();
		ASCIICharacter asciiCharacter = tokenizerContext
				.getCurrentASCIICharacter();
		
		
			context.addParseEvent("8.2.4.22", currentChar);

		switch (asciiCharacter) {
		case HYPHEN_MINUS:
			/*
			 * Switch to the script data escaped dash state. Emit a U+002D
			 * HYPHEN-MINUS character token.
			 */
			tokenizerContext.setNextState(factory
					.getState(TokenizerState.Script_data_escaped_dash_state));
			tokenizerContext.emitCurrentToken(new Token(TokenType.character,
					0x002D));
			break;
		case LESS_THAN_SIGN:
			/*
			 * Switch to the script data escaped less-than sign state.
			 */
			tokenizerContext
					.setNextState(factory
							.getState(TokenizerState.Script_data_escaped_less_than_sign_state));
			break;
		case NULL:
			/*
			 * Parse error. Emit a U+FFFD REPLACEMENT CHARACTER character token.
			 */
			context.addParseErrors(ParseErrorType.UnexpectedInputCharacter);
			tokenizerContext.emitCurrentToken(new Token(TokenType.character,
					0xFFFD));
			break;
		case EOF:
			/*
			 * Switch to the data state. Parse error. Reconsume the EOF
			 * character.
			 */
			tokenizerContext.setNextState(factory
					.getState(TokenizerState.Data_state));
			context.addParseErrors(ParseErrorType.UnexpectedInputCharacter);
			tokenizerContext.setFlagReconsumeCurrentInputCharacter(true);
			break;

		default:
			/*
			 * Emit the current input character as a character token.
			 */
			Token token = new Token(TokenType.character,
					tokenizerContext.getCurrentInputCharacter());
			tokenizerContext.emitCurrentToken(token);
			break;
		}

		context.setTokenizerContext(tokenizerContext);
		return context;
	}

}
