package com.html5parser.tokenizerStates;

import com.html5parser.classes.ParserContext;
import com.html5parser.classes.Token;
import com.html5parser.classes.Token.TokenType;
import com.html5parser.classes.TokenizerContext;
import com.html5parser.classes.TokenizerState;
import com.html5parser.factories.TokenizerStateFactory;
import com.html5parser.interfaces.ITokenizerState;
import com.html5parser.tracer.ParseError.ParseErrorType;

public class RAWTEXT_state implements ITokenizerState {

	public ParserContext process(ParserContext context) {
		TokenizerStateFactory factory;
		Token token = null;
		TokenizerContext tokenizerContext = context.getTokenizerContext();
		int currentChar = tokenizerContext.getCurrentInputCharacter();

		
			context.addParseEvent("8.2.4.5", currentChar);
		
		switch (tokenizerContext.getCurrentASCIICharacter()) {
		// U+003C LESS-THAN SIGN (<)
		// Switch to the RAWTEXT less-than sign state
		case LESS_THAN_SIGN:
			factory = TokenizerStateFactory.getInstance();
			tokenizerContext.setNextState(factory
					.getState(TokenizerState.RAWTEXT_less_than_sign_state));
			break;

		// U+0000 NULL
		// Parse error. Emit a U+FFFD REPLACEMENT CHARACTER character token.
		case NULL:
			context.addParseErrors(ParseErrorType.UnexpectedInputCharacter);
			tokenizerContext.emitCurrentToken(new Token(TokenType.character,
					0xFFFD));
			break;

		// EOF
		// Emit an end-of-file token.
		case EOF:
			token = new Token(TokenType.end_of_file, null);
			tokenizerContext.emitCurrentToken(token);
			break;

		// Anything else
		// Emit the current input character as a character token.
		default:
			token = new Token(TokenType.character, currentChar);
			tokenizerContext.emitCurrentToken(token);
			break;
		}

		context.setTokenizerContext(tokenizerContext);
		return context;
	}
}