package com.html5parser.insertionModes;

import com.html5dom.Element;
import com.html5parser.algorithms.AdjustedInsertionLocation;
import com.html5parser.algorithms.AppropiatePlaceForInsertingANode;
import com.html5parser.algorithms.CreateAnElementForAToken;
import com.html5parser.algorithms.GenerateImpliedEndTags;
import com.html5parser.algorithms.GenericRCDATAElementParsing;
import com.html5parser.algorithms.GenericRawTextElementParsing;
import com.html5parser.algorithms.InsertAnHTMLElement;
import com.html5parser.algorithms.InsertCharacter;
import com.html5parser.algorithms.InsertComment;
import com.html5parser.algorithms.ListOfActiveFormattingElements;
import com.html5parser.algorithms.ResetTheInsertionModeAppropriately;
import com.html5parser.classes.InsertionMode;
import com.html5parser.classes.ParserContext;
import com.html5parser.classes.Token;
import com.html5parser.classes.Token.TokenType;
import com.html5parser.classes.TokenizerState;
import com.html5parser.classes.token.TagToken;
import com.html5parser.constants.Namespace;
import com.html5parser.factories.InsertionModeFactory;
import com.html5parser.factories.TokenizerStateFactory;
import com.html5parser.interfaces.IInsertionMode;
import com.html5parser.tracer.ParseError.ParseErrorType;

public class InHead implements IInsertionMode {

	public ParserContext process(ParserContext parserContext) {

		InsertionModeFactory factory = InsertionModeFactory.getInstance();
		Token token = parserContext.getTokenizerContext().getCurrentToken();
		TokenType tokenType = token.getType();
		
		
			parserContext.addParseEvent("8.2.5.4.4", token);

		/*
		 * A character token that is one of U+0009 CHARACTER TABULATION, "LF"
		 * (U+000A), "FF" (U+000C), "CR" (U+000D), or U+0020 SPACE Insert the
		 * character.
		 */
		if (token.isSpaceCharacter()) {
			InsertCharacter.run(parserContext, token);
		}
		/*
		 * A comment token Insert a comment.
		 */
		else if (tokenType == TokenType.comment) {
			InsertComment.run(parserContext, token);
		}
		/*
		 * A DOCTYPE token Parse error. Ignore the token.
		 */
		else if (tokenType == TokenType.DOCTYPE) {
			parserContext.addParseErrors(ParseErrorType.UnexpectedToken);
			return parserContext;
		}
		/*
		 * A start tag whose tag name is "html"Process the token using the rules
		 * for the "in body" insertion mode.
		 */
		else if (tokenType == TokenType.start_tag
				&& token.getValue().equals("html")) {
			IInsertionMode inBody = factory
					.getInsertionMode(InsertionMode.in_body);
			parserContext = inBody.process(parserContext);
		}
		/*
		 * A start tag whose tag name is one of: "base", "basefont", "bgsound",
		 * "link" Insert an HTML element for the token. Immediately pop the
		 * current node off the stack of open elements. Acknowledge the token's
		 * self-closing flag, if it is set.
		 */
		else if (tokenType == TokenType.start_tag
				&& (token.getValue().equals("base")
						|| token.getValue().equals("basefont")
						|| token.getValue().equals("bgsound") || token
						.getValue().equals("link"))) {
			InsertAnHTMLElement.run(parserContext, token);
			parserContext.getOpenElements().pop();
			((TagToken) token).setFlagAcknowledgeSelfClosingTag(true);
			return parserContext;
		}
		/*
		 * A start tag whose tag name is "meta" Insert an HTML element for the
		 * token. Immediately pop the current node off the stack of open
		 * elements. Acknowledge the token's self-closing flag, if it is set.
		 * TODO If the element has a charset attribute, and getting an encoding
		 * from its value results in a supported ASCII-compatible character
		 * encoding or a UTF-16 encoding, and the confidence is currently
		 * tentative, then change the encoding to the resulting encoding.
		 * Otherwise, if the element has an http-equiv attribute whose value is
		 * an ASCII case-insensitive match for the string "Content-Type", and
		 * the element has a content attribute, and applying the algorithm for
		 * extracting a character encoding from a meta element to that
		 * attribute's value returns a supported ASCII-compatible character
		 * encoding or a UTF-16 encoding, and the confidence is currently
		 * tentative, then change the encoding to the extracted encoding.
		 */
		else if (tokenType == TokenType.start_tag
				&& token.getValue().equals("meta")) {
			InsertAnHTMLElement.run(parserContext, token);
			parserContext.getOpenElements().pop();
			((TagToken) token).setFlagAcknowledgeSelfClosingTag(true);
			// TODO
			return parserContext;
		}
		/*
		 * A start tag whose tag name is "title" Follow the generic RCDATA
		 * element parsing algorithm.
		 */
		else if (tokenType == TokenType.start_tag
				&& token.getValue().equals("title")) {
			GenericRCDATAElementParsing.run(parserContext, (TagToken) token);
		}
		/*
		 * A start tag whose tag name is "noscript", if the scripting flag is
		 * enabled A start tag whose tag name is one of: "noframes", "style"
		 * Follow the generic raw text element parsing algorithm.
		 */
		else if ((tokenType == TokenType.start_tag
				&& token.getValue().equals("noscript") && parserContext
					.isFlagScripting())
				|| (tokenType == TokenType.start_tag && (token.getValue()
						.equals("noframes") || token.getValue().equals("style")))) {
			GenericRawTextElementParsing.run(parserContext, (TagToken) token);
		}
		/*
		 * A start tag whose tag name is "noscript", if the scripting flag is
		 * disabled Insert an HTML element for the token. Switch the insertion
		 * mode to "in head noscript".
		 */
		else if (tokenType == TokenType.start_tag
				&& token.getValue().equals("noscript")
				&& !parserContext.isFlagScripting()) {
			InsertAnHTMLElement.run(parserContext, token);
			parserContext.setInsertionMode(factory
					.getInsertionMode(InsertionMode.in_head_noscript));
		}
		/*
		 * A start tag whose tag name is "script" Run these steps: TODO Let the
		 * adjusted insertion location be the appropriate place for inserting a
		 * node. Create an element for the token in the HTML namespace, with the
		 * intended parent being the element in which the adjusted insertion
		 * location finds itself. Mark the element as being "parser-inserted"
		 * and unset the element's "force-async" flag. This ensures that, if the
		 * script is external, any document.write() calls in the script will
		 * execute in-line, instead of blowing the document away, as would
		 * happen in most other cases. It also prevents the script from
		 * executing until the end tag is seen. If the parser was originally
		 * created for the HTML fragment parsing algorithm, then mark the script
		 * element as "already started". (fragment case) Insert the newly
		 * created element at the adjusted insertion location. Push the element
		 * onto the stack of open elements so that it is the new current node.
		 * Switch the tokenizer to the script data state. Let the original
		 * insertion mode be the current insertion mode. Switch the insertion
		 * mode to "text".
		 */
		else if (tokenType == TokenType.start_tag
				&& token.getValue().equals("script")) {
			AdjustedInsertionLocation location = AppropiatePlaceForInsertingANode
					.run(parserContext);
			Element element = CreateAnElementForAToken.run(
					location.getParent(), Namespace.HTML, token, parserContext);

			// TODO: Mark the element as being "parser-inserted" and unset the
			// element's "non-blocking" flag. Out of scope.

			// TODO: If the parser was originally created for the HTML fragment
			// parsing algorithm, then mark the script element as
			// "already started". (fragment case) Need script element
			// implementation.
			if (parserContext.isFlagHTMLFragmentParser()) {

			}

			location.insertElement(element);
			parserContext.getOpenElements().push(element);

			parserContext.getTokenizerContext().setNextState(
					TokenizerStateFactory.getInstance().getState(
							TokenizerState.Script_data_state));

			parserContext.setOriginalInsertionMode(parserContext
					.getInsertionMode());

			parserContext.setInsertionMode(InsertionModeFactory.getInstance()
					.getInsertionMode(InsertionMode.text));

		}
		/*
		 * An end tag whose tag name is "head" Pop the current node (which will
		 * be the head element) off the stack of open elements. Switch the
		 * insertion mode to "after head".
		 */
		else if (tokenType == TokenType.end_tag
				&& token.getValue().equals("head")) {
			parserContext.getOpenElements().pop();
			parserContext.setInsertionMode(factory
					.getInsertionMode(InsertionMode.after_head));
		}
		/*
		 * An end tag whose tag name is one of: "body", "html", "br" Act as
		 * described in the "anything else" entry below.
		 */
		else if (tokenType == TokenType.end_tag
				&& (token.getValue().equals("body")
						|| token.getValue().equals("html") || token.getValue()
						.equals("br"))) {
			anythingElse(parserContext);
		}
		/*
		 * A start tag whose tag name is "template" Insert an HTML element for
		 * the token. Insert a marker at the end of the list of active
		 * formatting elements. Set the frameset-ok flag to "not ok". Switch the
		 * insertion mode to "in template". Push "in template" onto the stack of
		 * template insertion modes so that it is the new current template
		 * insertion mode.
		 */
		else if (tokenType == TokenType.start_tag
				&& token.getValue().equals("template")) {
			InsertAnHTMLElement.run(parserContext, token);
			ListOfActiveFormattingElements.insertMarker(parserContext);
			parserContext.setFlagFramesetOk(false);
			IInsertionMode in_template = factory
					.getInsertionMode(InsertionMode.in_template);
			parserContext.setInsertionMode(in_template);
			parserContext.getTemplateInsertionModes().push(in_template);

		}
		/*
		 * An end tag whose tag name is "template"
		 * 
		 * If there is no template element on the stack of open elements, then
		 * this is a parse error; ignore the token.
		 * 
		 * Otherwise, run these steps:
		 * 
		 * Generate implied end tags.
		 * 
		 * If the current node is not a template element, then this is a parse
		 * error.
		 * 
		 * Pop elements from the stack of open elements until a template element
		 * has been popped from the stack.
		 * 
		 * Clear the list of active formatting elements up to the last marker.
		 * Pop the current template insertion mode off the stack of template
		 * insertion modes.
		 * 
		 * Reset the insertion mode appropriately.
		 */
		else if (tokenType == TokenType.end_tag
				&& token.getValue().equals("template")) {
			if (!parserContext.openElementsContain("template"))
				parserContext.addParseErrors(ParseErrorType.UnexpectedToken);
			else {
				GenerateImpliedEndTags.run(parserContext);
				if (!parserContext.getCurrentNode().getNodeName()
						.equals("template"))
					parserContext
							.addParseErrors(ParseErrorType.UnexpectedToken);
				while (true) {
					Element element = parserContext.getOpenElements().pop();
					if (element.isHTMLElement()
							&& element.getNodeName().equals(token.getValue())) {
						break;
					}
				}
				ListOfActiveFormattingElements.clear(parserContext);
				parserContext.getTemplateInsertionModes().pop();
				ResetTheInsertionModeAppropriately.Run(parserContext);
			}
		}
		/*
		 * A start tag whose tag name is "head" Any other end tag Parse error.
		 * Ignore the token.
		 */
		else if ((tokenType == TokenType.start_tag && token.getValue().equals(
				"head"))
				|| tokenType == TokenType.end_tag) {
			parserContext.addParseErrors(ParseErrorType.UnexpectedToken);
		}
		/*
		 * Anything else Pop the current node (which will be the head element)
		 * off the stack of open elements. Switch the insertion mode to
		 * "after head". Reprocess the token.
		 */
		else {
			anythingElse(parserContext);
		}
		return parserContext;
	}

	private void anythingElse(ParserContext parserContext) {
		parserContext.getOpenElements().pop();
		InsertionModeFactory factory = InsertionModeFactory.getInstance();
		parserContext.setInsertionMode(factory
				.getInsertionMode(InsertionMode.after_head));
		parserContext.setFlagReconsumeToken(true);
	}
}
