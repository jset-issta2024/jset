package com.html5parser.insertionModes;

import com.html5dom.Element;
import com.html5parser.algorithms.ListOfActiveFormattingElements;
import com.html5parser.algorithms.ResetTheInsertionModeAppropriately;
import com.html5parser.classes.InsertionMode;
import com.html5parser.classes.ParserContext;
import com.html5parser.classes.Token;
import com.html5parser.classes.Token.TokenType;
import com.html5parser.factories.InsertionModeFactory;
import com.html5parser.interfaces.IInsertionMode;
import com.html5parser.tracer.ParseError.ParseErrorType;

public class InTemplate implements IInsertionMode {

	public ParserContext process(ParserContext parserContext) {

		InsertionModeFactory factory = InsertionModeFactory.getInstance();
		Token token = parserContext.getTokenizerContext().getCurrentToken();
		TokenType tokenType = token.getType();

		
			parserContext.addParseEvent("8.2.5.4.18", token);
		
		/**
		 * A character token A comment token A DOCTYPE token
		 * 
		 * Process the token using the rules for the "in body" insertion mode.
		 */
		if (tokenType == TokenType.character || tokenType == TokenType.comment
				|| tokenType == TokenType.DOCTYPE) {
			IInsertionMode insertionMode = factory
					.getInsertionMode(InsertionMode.in_body);
			return parserContext = insertionMode.process(parserContext);
		}

		/**
		 * A start tag whose tag name is one of: "base", "basefont", "bgsound",
		 * "link", "meta", "noframes", "script", "style", "template", "title"
		 * 
		 * An end tag whose tag name is "template"
		 * 
		 * Process the token using the rules for the "in head" insertion mode.
		 */
		if ((tokenType == TokenType.start_tag && (token.getValue().equals(
				"base")
				|| token.getValue().equals("basefont")
				|| token.getValue().equals("bgsound")
				|| token.getValue().equals("link")
				|| token.getValue().equals("meta")
				|| token.getValue().equals("noframes")
				|| token.getValue().equals("script")
				|| token.getValue().equals("style")
				|| token.getValue().equals("template") || token.getValue()
				.equals("title")))
				|| (tokenType == TokenType.end_tag && token.getValue().equals(
						"template"))) {
			IInsertionMode insertionMode = factory
					.getInsertionMode(InsertionMode.in_head);
			return parserContext = insertionMode.process(parserContext);
		}

		/**
		 * A start tag whose tag name is one of: "caption", "colgroup", "tbody",
		 * "tfoot", "thead"
		 * 
		 * Pop the current template insertion mode off the stack of template
		 * insertion modes.
		 * 
		 * Push "in table" onto the stack of template insertion modes so that it
		 * is the new current template insertion mode.
		 * 
		 * Switch the insertion mode to "in table", and reprocess the token.
		 */

		if (tokenType == TokenType.start_tag
				&& (token.getValue().equals("caption")
						|| token.getValue().equals("colgroup")
						|| token.getValue().equals("tbody")
						|| token.getValue().equals("tfoot") || token.getValue()
						.equals("thead"))) {
			parserContext.getTemplateInsertionModes().pop();
			IInsertionMode insertionMode = factory
					.getInsertionMode(InsertionMode.in_table);
			parserContext.getTemplateInsertionModes().push(insertionMode);
			parserContext.setInsertionMode(insertionMode);
			parserContext.setFlagReconsumeToken(true);
			return parserContext;
		}

		/**
		 * A start tag whose tag name is "col"
		 * 
		 * Pop the current template insertion mode off the stack of template
		 * insertion modes.
		 * 
		 * Push "in column group" onto the stack of template insertion modes so
		 * that it is the new current template insertion mode.
		 * 
		 * Switch the insertion mode to "in column group", and reprocess the
		 * token.
		 */
		if (tokenType == TokenType.start_tag && token.getValue().equals("col")) {
			parserContext.getTemplateInsertionModes().pop();
			IInsertionMode insertionMode = factory
					.getInsertionMode(InsertionMode.in_column_group);
			parserContext.getTemplateInsertionModes().push(insertionMode);
			parserContext.setInsertionMode(insertionMode);
			parserContext.setFlagReconsumeToken(true);
			return parserContext;
		}

		/**
		 * A start tag whose tag name is "tr"
		 * 
		 * 
		 * Pop the current template insertion mode off the stack of template
		 * insertion modes.
		 * 
		 * Push "in table body" onto the stack of template insertion modes so
		 * that it is the new current template insertion mode.
		 * 
		 * Switch the insertion mode to "in table body", and reprocess the
		 * token.
		 */
		if (tokenType == TokenType.start_tag && token.getValue().equals("tr")) {
			parserContext.getTemplateInsertionModes().pop();
			IInsertionMode insertionMode = factory
					.getInsertionMode(InsertionMode.in_table_body);
			parserContext.getTemplateInsertionModes().push(insertionMode);
			parserContext.setInsertionMode(insertionMode);
			parserContext.setFlagReconsumeToken(true);
			return parserContext;
		}

		/**
		 * A start tag whose tag name is one of: "td", "th"
		 * 
		 * 
		 * Pop the current template insertion mode off the stack of template
		 * insertion modes.
		 * 
		 * Push "in row" onto the stack of template insertion modes so that it
		 * is the new current template insertion mode.
		 * 
		 * Switch the insertion mode to "in row", and reprocess the token.
		 */
		if (tokenType == TokenType.start_tag
				&& (token.getValue().equals("td") || token.getValue().equals(
						"th"))) {
			parserContext.getTemplateInsertionModes().pop();
			IInsertionMode insertionMode = factory
					.getInsertionMode(InsertionMode.in_row);
			parserContext.getTemplateInsertionModes().push(insertionMode);
			parserContext.setInsertionMode(insertionMode);
			parserContext.setFlagReconsumeToken(true);
			return parserContext;
		}

		/**
		 * Any other start tag
		 * 
		 * Pop the current template insertion mode off the stack of template
		 * insertion modes.
		 * 
		 * Push "in body" onto the stack of template insertion modes so that it
		 * is the new current template insertion mode.
		 * 
		 * Switch the insertion mode to "in body", and reprocess the token.
		 */
		if (tokenType == TokenType.start_tag) {
			parserContext.getTemplateInsertionModes().pop();
			IInsertionMode insertionMode = factory
					.getInsertionMode(InsertionMode.in_body);
			parserContext.getTemplateInsertionModes().push(insertionMode);
			parserContext.setInsertionMode(insertionMode);
			parserContext.setFlagReconsumeToken(true);
			return parserContext;
		}

		/**
		 * Any other end tag
		 * 
		 * Parse error. Ignore the token.
		 */
		if (tokenType == TokenType.end_tag) {
			parserContext.addParseErrors(ParseErrorType.UnexpectedToken);
			return parserContext;
		}

		/**
		 * An end-of-file token
		 * 
		 * If there is no template element on the stack of open elements, then
		 * stop parsing. (fragment case)
		 * 
		 * Otherwise, this is a parse error.
		 * 
		 * Pop elements from the stack of open elements until a template element
		 * has been popped from the stack.
		 * 
		 * Clear the list of active formatting elements up to the last marker.
		 * 
		 * Pop the current template insertion mode off the stack of template
		 * insertion modes.
		 * 
		 * Reset the insertion mode appropriately.
		 * 
		 * Reprocess the token.
		 */
		if (tokenType == TokenType.end_of_file) {
			if (!parserContext.openElementsContain("template")) {
				parserContext.setFlagStopParsing(true);
				return parserContext;
			}

			parserContext.addParseErrors(ParseErrorType.UnexpectedToken);

			Element templateElement = null;
			do {
				templateElement = parserContext.getOpenElements().pop();
				if (templateElement.isHTMLElement()
						&& templateElement.getNodeName().equals("template"))
					break;
			} while (!parserContext.getOpenElements().isEmpty());

			ListOfActiveFormattingElements.clear(parserContext);
			parserContext.getTemplateInsertionModes().pop();
			ResetTheInsertionModeAppropriately.Run(parserContext);
			parserContext.setFlagReconsumeToken(true);
			return parserContext;
		}

		return null; // this is an application error. Means something in the
						// code is wrong.
	}
}
