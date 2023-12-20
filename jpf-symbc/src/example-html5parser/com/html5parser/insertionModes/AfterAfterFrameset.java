package com.html5parser.insertionModes;

import com.html5dom.Document;

import com.html5parser.algorithms.InsertComment;
import com.html5parser.classes.InsertionMode;
import com.html5parser.classes.ParserContext;
import com.html5parser.classes.Token;
import com.html5parser.classes.Token.TokenType;
import com.html5parser.factories.InsertionModeFactory;
import com.html5parser.interfaces.IInsertionMode;
import com.html5parser.tracer.ParseError.ParseErrorType;

public class AfterAfterFrameset implements IInsertionMode {

	public ParserContext process(ParserContext parserContext) {

		InsertionModeFactory factory = InsertionModeFactory.getInstance();
		Token token = parserContext.getTokenizerContext().getCurrentToken();
		TokenType tokenType = token.getType();
		
		
			parserContext.addParseEvent("8.2.5.4.23", token);

		/**
		 * A comment token
		 * 
		 * Insert a comment as the last child of the Document object.
		 */
		if (tokenType == TokenType.comment) {
			Document document = parserContext.getDocument();
			InsertComment.run(parserContext, token, document);
			return parserContext;
		}

		/**
		 * A DOCTYPE token
		 * 
		 * or
		 * 
		 * A character token that is one of U+0009 CHARACTER TABULATION, U+000A
		 * LINE FEED (LF), U+000C FORM FEED (FF), U+000D CARRIAGE RETURN (CR),
		 * or U+0020 SPACE
		 * 
		 * A start tag whose tag name is "html"
		 * 
		 * Process the token using the rules for the "in body" insertion mode.
		 * 
		 */
		if ((tokenType == TokenType.DOCTYPE)
				|| (tokenType == TokenType.start_tag && token.getValue()
						.equals("html"))
				|| (tokenType == TokenType.character && token
						.isSpaceCharacter())) {
			IInsertionMode insertionMode = factory
					.getInsertionMode(InsertionMode.in_body);
			return parserContext = insertionMode.process(parserContext);
		}

		/**
		 * An end-of-file token
		 * 
		 * Stop parsing.
		 */
		if (tokenType == TokenType.end_of_file) {
			parserContext.setFlagStopParsing(true);
			return parserContext;
		}

		/**
		 * A start tag whose tag name is "noframes"
		 * 
		 * Process the token using the rules for the "in head" insertion mode.
		 */
		if (tokenType == TokenType.start_tag
				&& token.getValue().equals("noframes")) {
			IInsertionMode insertionMode = factory
					.getInsertionMode(InsertionMode.in_head);
			return parserContext = insertionMode.process(parserContext);
		}

		/**
		 * Anything else
		 * 
		 * Parse error. Ignore the token.
		 */
		parserContext.addParseErrors(ParseErrorType.UnexpectedToken);
		return parserContext;
	}
}
