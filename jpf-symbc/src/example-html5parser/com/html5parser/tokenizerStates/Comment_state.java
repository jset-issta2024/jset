package com.html5parser.tokenizerStates;

import com.html5parser.classes.ParserContext;
import com.html5parser.classes.TokenizerContext;
import com.html5parser.classes.TokenizerState;
import com.html5parser.factories.TokenizerStateFactory;
import com.html5parser.interfaces.ITokenizerState;
import com.html5parser.tracer.ParseError.ParseErrorType;

public class Comment_state implements ITokenizerState {

	public ParserContext process(ParserContext context) {
		TokenizerStateFactory factory = TokenizerStateFactory.getInstance();
		TokenizerContext tokenizerContext = context.getTokenizerContext();
		int currentChar = tokenizerContext.getCurrentInputCharacter();
		
		
			context.addParseEvent("8.2.4.48", currentChar);

		switch (tokenizerContext.getCurrentASCIICharacter()) {
		// "-" (U+002D)
		// Switch to the comment end dash state
		case HYPHEN_MINUS:
			tokenizerContext.setNextState(factory
					.getState(TokenizerState.Comment_end_dash_state));
			break;

		// U+0000 NULL
		// Parse error. Append a U+FFFD REPLACEMENT CHARACTER character to the
		// comment token's data.
		case NULL:
			context.addParseErrors(ParseErrorType.UnexpectedInputCharacter);
			tokenizerContext.getCurrentToken().appendValue(0xFFFD);
			break;

		// EOF
		// Parse error. Switch to the data state. Emit the comment token.
		// Reconsume the EOF character.
		case EOF:
			context.addParseErrors(ParseErrorType.UnexpectedInputCharacter);
			tokenizerContext.setNextState(factory
					.getState(TokenizerState.Data_state));
			tokenizerContext.setFlagEmitToken(true);
			tokenizerContext.setFlagReconsumeCurrentInputCharacter(true);
			break;

		// Anything else
		// Append the current input character to the comment token's data.
		default:
			tokenizerContext.getCurrentToken().appendValue(currentChar);
			break;
		}

		context.setTokenizerContext(tokenizerContext);
		return context;
	}
}