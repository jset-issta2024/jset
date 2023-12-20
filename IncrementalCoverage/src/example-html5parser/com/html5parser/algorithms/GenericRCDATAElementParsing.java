package com.html5parser.algorithms;

import com.html5parser.classes.InsertionMode;
import com.html5parser.classes.ParserContext;
import com.html5parser.classes.TokenizerState;
import com.html5parser.classes.token.TagToken;
import com.html5parser.factories.InsertionModeFactory;
import com.html5parser.factories.TokenizerStateFactory;

public class GenericRCDATAElementParsing {

	public static ParserContext run(ParserContext parserContext, TagToken token) {
		
		
			parserContext.addParseEvent("8.2.5.2_2", token);
		
		/*
		 * 1 Insert an HTML element for the token. 2 If the algorithm that was
		 * invoked is the generic raw text element parsing algorithm, switch the
		 * tokenizer to the RAWTEXT state; otherwise the algorithm invoked was
		 * the generic RCDATA element parsing algorithm, switch the tokenizer to
		 * the RCDATA state. 3 Let the original insertion mode be the current
		 * insertion mode. 4Then, switch the insertion mode to "text".
		 */
		TokenizerStateFactory tokenizerFactory = TokenizerStateFactory
				.getInstance();
		InsertionModeFactory insertionModeFactory = InsertionModeFactory
				.getInstance();
		InsertAnHTMLElement.run(parserContext, token);
		parserContext.getTokenizerContext().setNextState(
				tokenizerFactory.getState(TokenizerState.RCDATA_state));
		parserContext.setOriginalInsertionMode(parserContext.getInsertionMode());
		parserContext.setInsertionMode(insertionModeFactory
				.getInsertionMode(InsertionMode.text));
		return parserContext;
	}
}
