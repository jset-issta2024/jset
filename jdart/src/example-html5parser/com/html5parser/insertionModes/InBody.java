package com.html5parser.insertionModes;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.html5dom.Element;
import com.html5dom.Node;
import com.html5parser.algorithms.AddAttributesToAnElement;
import com.html5parser.algorithms.AdjustForeignAttributes;
import com.html5parser.algorithms.AdjustMathMLAttributes;
import com.html5parser.algorithms.AdjustSVGAttributes;
import com.html5parser.algorithms.AdoptionAgencyAlgorithm;
import com.html5parser.algorithms.ElementInScope;
import com.html5parser.algorithms.GenerateImpliedEndTags;
import com.html5parser.algorithms.GenericRawTextElementParsing;
import com.html5parser.algorithms.InsertAnHTMLElement;
import com.html5parser.algorithms.InsertCharacter;
import com.html5parser.algorithms.InsertComment;
import com.html5parser.algorithms.InsertForeignElement;
import com.html5parser.algorithms.ListOfActiveFormattingElements;
import com.html5parser.classes.InsertionMode;
import com.html5parser.classes.ParserContext;
import com.html5parser.classes.Token;
import com.html5parser.classes.Token.TokenType;
import com.html5parser.classes.TokenizerState;
import com.html5parser.classes.token.TagToken;
import com.html5parser.classes.token.TagToken.Attribute;
import com.html5parser.constants.Namespace;
import com.html5parser.factories.InsertionModeFactory;
import com.html5parser.factories.TokenizerStateFactory;
import com.html5parser.interfaces.IInsertionMode;
import com.html5parser.tracer.ParseError.ParseErrorType;

public class InBody implements IInsertionMode {

	private boolean ignoreNextLFCharToken = false;

	public ParserContext process(ParserContext parserContext) {

		InsertionModeFactory factory = InsertionModeFactory.getInstance();
		Token token = parserContext.getTokenizerContext().getCurrentToken();
		TokenType tokenType = token.getType();
		Stack<Element> openElementStack = parserContext.getOpenElements();

		parserContext.addParseEvent("8.2.5.4.7", token);

		/*
		 * A character token that is U+0000 NULL Parse error. Ignore the token.
		 */
		if (tokenType == TokenType.character && token.getIntValue() == 0x000) {
			parserContext.addParseErrors(ParseErrorType.UnexpectedToken);
		}
		/*
		 * A character token that is one of U+0009 CHARACTER TABULATION, "LF"
		 * (U+000A), "FF" (U+000C), "CR" (U+000D), or U+0020 SPACE Reconstruct
		 * the active formatting elements, if any. Insert the token's character.
		 */
		else if (token.isSpaceCharacter()) {
			if ((token.getValue().codePointAt(0) != 0x000A || !ignoreNextLFCharToken)) {
				if (!parserContext.getActiveFormattingElements().isEmpty()) {
					ListOfActiveFormattingElements.reconstruct(parserContext);
				}
				InsertCharacter.run(parserContext, token);
			}
		}
		/*
		 * Any other character token Reconstruct the active formatting
		 * elements,if any. Insert the token's character. Set the frameset-ok
		 * flag to "not ok".
		 */
		else if (tokenType == TokenType.character) {
			ListOfActiveFormattingElements.reconstruct(parserContext);
			InsertCharacter.run(parserContext, token);
			parserContext.setFlagFramesetOk(false);
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
		 * A start tag whose tag name is "html" Parse error. If there is a
		 * template element on the stack of open elements, then ignore the
		 * token. Otherwise, for each attribute on the token, check to see if
		 * the attribute is already present on the top element of the stack of
		 * open elements. If it is not, add the attribute and its corresponding
		 * value to that element.
		 */
		else if (tokenType == TokenType.start_tag
				&& token.getValue().equals("html")) {

			parserContext.addParseErrors(ParseErrorType.UnexpectedToken);
			if (!parserContext.openElementsContain("template")) {
				Element html = parserContext.getOpenElements().get(0);
				TagToken tagToken = (TagToken) token;
				AddAttributesToAnElement.run(html, tagToken);
			}
		}
		/*
		 * A start tag whose tag name is one of: "base", "basefont", "bgsound",
		 * "link", "meta", "noframes", "script", "style", "template", "title" An
		 * end tag whose tag name is "template" Process the token using the
		 * rules for the "in head" insertion mode.
		 */
		else if ((tokenType == TokenType.start_tag && isOneOf(token.getValue(),
				new String[] { "base", "basefont", "bgsound", "link", "meta",
						"noframes", "script", "style", "template", "title" }))
				|| (tokenType == TokenType.end_tag && token.getValue().equals(
						"template"))) {
			IInsertionMode inHead = factory
					.getInsertionMode(InsertionMode.in_head);
			parserContext = inHead.process(parserContext);
		}
		/*
		 * A start tag whose tag name is "body" Parse error. If the second
		 * element on the stack of open elements is not a body element, if the
		 * stack of open elements has only one node on it, or if there is a
		 * template element on the stack of open elements, then ignore the
		 * token. (fragment case) Otherwise, set the frameset-ok flag to
		 * "not ok"; then, for each attribute on the token, check to see if the
		 * attribute is already present on the body element (the second element)
		 * on the stack of open elements, and if it is not, add the attribute
		 * and its corresponding value to that element.
		 */
		else if (tokenType == TokenType.start_tag
				&& token.getValue().equals("body")) {
			parserContext.addParseErrors(ParseErrorType.UnexpectedToken);
			if ((parserContext.getOpenElements().size() > 1 && !parserContext
					.getOpenElements().get(1).getNodeName().equals("body"))
					|| parserContext.getOpenElements().size() == 1
					|| parserContext.openElementsContain("template")) {
				// ignore the token
			} else {
				parserContext.setFlagFramesetOk(false);
				Element body = parserContext.getOpenElements().get(1);
				TagToken tagToken = (TagToken) token;
				for (Attribute att : tagToken.getAttributes()) {
					if (!body.hasAttribute(att.getName())) {
						body.setAttribute(att.getName(), att.getValue());
					}
				}
			}
		}
		/*
		 * A start tag whose tag name is "frameset" Parse error. If the stack of
		 * open elements has only one node on it, or if the second element on
		 * the stack of open elements is not a body element, then ignore the
		 * token. (fragment case) If the frameset-ok flag is set to "not ok",
		 * ignore the token. Otherwise, run the following steps: Remove the
		 * second element on the stack of open elements from its parent node, if
		 * it has one. Pop all the nodes from the bottom of the stack of open
		 * elements, from the current node up to, but not including, the root
		 * html element. Insert an HTML element for the token. Switch the
		 * insertion mode to "in frameset".
		 */
		else if (tokenType == TokenType.start_tag
				&& token.getValue().equals("frameset")) {
			parserContext.addParseErrors(ParseErrorType.UnexpectedToken);
			if (parserContext.getOpenElements().size() > 1) {
				ArrayList<Element> openElements = new ArrayList<Element>();
				openElements.addAll(parserContext.getOpenElements());
				if (!openElements.get(1).getNodeName().equals("body"))
					return parserContext;
				if (!parserContext.isFlagFramesetOk())
					return parserContext;
				openElements.get(0).removeChild(openElements.get(1));
				while (parserContext.getOpenElements().size() > 1)
					parserContext.getOpenElements().pop();
				InsertAnHTMLElement.run(parserContext, token);
				parserContext.setInsertionMode(factory
						.getInsertionMode(InsertionMode.in_frameset));
			} else if (parserContext.getOpenElements().size() == 1) {
				return parserContext;
			}
		}
		/*
		 * An end-of-file token If there is a node in the stack of open elements
		 * that is not either a dd element, a dt element, an li element, a p
		 * element, a tbody element, a td element, a tfoot element, a th
		 * element, a thead element, a tr element, the body element, or the html
		 * element, then this is a parse error.
		 * 
		 * If the stack of template insertion modes is not empty, then process
		 * the token using the rules for the "in template" insertion mode.
		 * Otherwise, stop parsing.
		 */
		else if (tokenType == TokenType.end_of_file) {
			if (!parserContext.getOpenElements().isEmpty()) {
				ArrayList<Element> stack = new ArrayList<Element>();
				stack.addAll(parserContext.getOpenElements());
				boolean flag = true;
				for (Element element : stack) {
					String name = element.getNodeName();
					if (!isOneOf(name, new String[] { "dd", "dt", "li", "p",
							"tbody", "td", "tfoot", "th", "thead", "tr",
							"body", "html" })) {
						flag = false;
						break;
					}
				}
				if (!flag) {
					parserContext
							.addParseErrors(ParseErrorType.UnexpectedToken);
				}
			}
			if (!parserContext.getTemplateInsertionModes().isEmpty()) {
				IInsertionMode inTemplate = factory
						.getInsertionMode(InsertionMode.in_template);
				parserContext = inTemplate.process(parserContext);
			} else {
				parserContext.setFlagStopParsing(true);
			}
		}

		/*
		 * An end tag whose tag name is "body" If the stack of open elements
		 * does not have a body element in scope, this is a parse error; ignore
		 * the token. Otherwise, if there is a node in the stack of open
		 * elements that is not either a dd element, a dt element, an li
		 * element, an optgroup element, an option element, a p element, an rb
		 * element, an rp element, an rt element, an rtc element, a tbody
		 * element, a td element, a tfoot element, a th element, a thead
		 * element, a tr element, the body element, or the html element, then
		 * this is a parse error. Switch the insertion mode to "after body".
		 */
		else if (tokenType == TokenType.end_tag
				&& token.getValue().equals("body")) {
			if (!ElementInScope.isInScope(parserContext, "body")) {
				parserContext.addParseErrors(ParseErrorType.UnexpectedToken);
				return parserContext;
			} else {
				ArrayList<Element> list = new ArrayList<Element>();
				list.addAll(parserContext.getOpenElements());
				for (Element element : list) {
					if (!isOneOf(element.getNodeName(), new String[] { "dd",
							"dt", "li", "optgroup", "option", "p", "rb", "rp",
							"rt", "rtc", "tbody", "td", "tfoot", "th", "thead",
							"tr", "body", "html" })) {
						parserContext
								.addParseErrors(ParseErrorType.UnexpectedToken);
						break;
					}
				}
				parserContext.setInsertionMode(factory
						.getInsertionMode(InsertionMode.after_body));
			}
		}
		/*
		 * An end tag whose tag name is "html" If the stack of open elements
		 * does not have a body element in scope, this is a parse error; ignore
		 * the token. Otherwise, if there is a node in the stack of open
		 * elements that is not either a dd element, a dt element, an li
		 * element, an optgroup element, an option element, a p element, an rb
		 * element, an rp element, an rt element, an rtc element, a tbody
		 * element, a td element, a tfoot element, a th element, a thead
		 * element, a tr element, the body element, or the html element, then
		 * this is a parse error. Switch the insertion mode to "after body".
		 * Reprocess the token.
		 */
		else if (tokenType == TokenType.end_tag
				&& token.getValue().equals("html")) {
			if (!ElementInScope.isInScope(parserContext, "body")) {
				parserContext.addParseErrors(ParseErrorType.UnexpectedToken);
				return parserContext;
			} else {
				ArrayList<Element> list = new ArrayList<Element>();
				list.addAll(parserContext.getOpenElements());
				for (Element element : list) {
					if (!isOneOf(element.getNodeName(), new String[] { "dd",
							"dt", "li", "optgroup", "option", "p", "rb", "rp",
							"rt", "rtc", "tbody", "td", "tfoot", "th", "thead",
							"tr", "body", "html" })) {
						parserContext
								.addParseErrors(ParseErrorType.UnexpectedToken);
						break;
					}
				}
				parserContext.setInsertionMode(factory
						.getInsertionMode(InsertionMode.after_body));
				parserContext.setFlagReconsumeToken(true);
			}
		}
		/*
		 * A start tag whose tag name is one of: "address", "article", "aside",
		 * "blockquote", "center", "details", "dialog", "dir", "div", "dl",
		 * "fieldset", "figcaption", "figure", "footer", "header", "hgroup",
		 * "main", "nav", "ol", "p", "section", "summary", "ul" If the stack of
		 * open elements has a p element in button scope, then close a p
		 * element. Insert an HTML element for the token.
		 */
		else if (tokenType == TokenType.start_tag
				&& isOneOf(token.getValue(), new String[] { "address",
						"article", "aside", "blockquote", "center", "details",
						"dialog", "dir", "div", "dl", "fieldset", "figcaption",
						"figure", "footer", "header", "hgroup", "main", "nav",
						"ol", "p", "section", "summary", "ul" })) {
			if (ElementInScope.isInButtonScope(parserContext, "p"))
				closeApElement(parserContext);
			InsertAnHTMLElement.run(parserContext, token);
		}
		/*
		 * A start tag whose tag name is one of: "h1", "h2", "h3", "h4", "h5",
		 * "h6" If the stack of open elements has a p element in button scope,
		 * then close a p element. If the current node is an HTML element whose
		 * tag name is one of "h1", "h2", "h3", "h4", "h5", or "h6", then this
		 * is a parse error; pop the current node off the stack of open
		 * elements. Insert an HTML element for the token.
		 */
		else if (tokenType == TokenType.start_tag
				&& isOneOf(token.getValue(), new String[] { "h1", "h2", "h3",
						"h4", "h5", "h6" })) {
			if (ElementInScope.isInButtonScope(parserContext, "p"))
				closeApElement(parserContext);
			if (isOneOf(parserContext.getCurrentNode().getTagName(),
					new String[] { "h1", "h2", "h3", "h4", "h5", "h6" })) {
				parserContext.addParseErrors(ParseErrorType.UnexpectedToken);
				parserContext.getOpenElements().pop();
			}
			InsertAnHTMLElement.run(parserContext, token);
		}
		/*
		 * A start tag whose tag name is one of: "pre", "listing" If the stack
		 * of open elements has a p element in button scope, then close a p
		 * element. Insert an HTML element for the token. If the next token is a
		 * "LF" (U+000A) character token, then ignore that token and move on to
		 * the next one. (Newlines at the start of pre blocks are ignored as an
		 * authoring convenience.) Set the frameset-ok flag to "not ok".
		 */
		else if (tokenType == TokenType.start_tag
				&& isOneOf(token.getValue(), new String[] { "pre", "listing" })) {
			if (ElementInScope.isInButtonScope(parserContext, "p")) {
				closeApElement(parserContext);
			}
			InsertAnHTMLElement.run(parserContext, token);
			ignoreNextLFCharToken = true;
			parserContext.setFlagFramesetOk(false);
		}
		/*
		 * A start tag whose tag name is "form" If the form element pointer is
		 * not null, and there is no template element on the stack of open
		 * elements, then this is a parse error; ignore the token. Otherwise: If
		 * the stack of open elements has a p element in button scope, then
		 * close a p element. Insert an HTML element for the token, and, if
		 * there is no template element on the stack of open elements, set the
		 * form element pointer to point to the element created.
		 */
		else if (tokenType == TokenType.start_tag
				&& token.getValue().equals("form")) {
			if (parserContext.getFormElementPointer() != null
					&& !parserContext.openElementsContain("template")) {
				parserContext.addParseErrors(ParseErrorType.UnexpectedToken);
				return parserContext;
			} else {
				if (ElementInScope.isInButtonScope(parserContext, "p")) {
					closeApElement(parserContext);
				}
				Element element = InsertAnHTMLElement.run(parserContext, token);
				if (!parserContext.getOpenElements().isEmpty()
						&& !parserContext.openElementsContain("template")) {
					parserContext.setFormElementPointer(element);
				}
			}
		}
		/*
		 * TODO A start tag whose tag name is "li" Run these steps: Set the
		 * frameset-ok flag to "not ok". Initialize node to be the current node
		 * (the bottommost node of the stack). Loop: If node is an li element,
		 * then run these substeps: Generate implied end tags, except for li
		 * elements. If the current node is not an li element, then this is a
		 * parse error. Pop elements from the stack of open elements until an li
		 * element has been popped from the stack. Jump to the step labeled done
		 * below. If node is in the special category, but is not an address,
		 * div, or p element, then jump to the step labeled done below.
		 * Otherwise, set node to the previous entry in the stack of open
		 * elements and return to the step labeled loop. Done: If the stack of
		 * open elements has a p element in button scope, then close a p
		 * element. Finally, insert an HTML element for the token.
		 */
		else if (tokenType == TokenType.start_tag
				&& token.getValue().equals("li")) {
			parserContext.setFlagFramesetOk(false);
			Node node = parserContext.getCurrentNode();

			done: while (true) {
				if (node.getNodeName().equals("li")) {
					GenerateImpliedEndTags.run(parserContext, "li");
					if (!parserContext.getCurrentNode().getNodeName()
							.equals("li")) {
						parserContext
								.addParseErrors(ParseErrorType.UnexpectedToken);
					}
					Node popped;
					do {
						popped = parserContext.getOpenElements().pop();
					} while (!popped.getNodeName().equals("li"));
					break done;
				}
				if (isOneOf(
						node.getNodeName(),
						new String(
								"applet, area, article, aside, base, basefont, bgsound, "
										+ "blockquote, body, br, button, caption, center, col, colgroup, dd, "
										+ "details, dir, dl, dt, embed, fieldset, figcaption, figure, "
										+ "footer, form, frame, frameset, h1, h2, h3, h4, h5, h6, head, header, "
										+ "hgroup, hr, html, iframe, img, input, isindex, li, link, listing, "
										+ "main, marquee, meta, nav, noembed, noframes, noscript, object, ol, "
										+ "param, plaintext, pre, script, section, select, source, style, "
										+ "summary, table, tbody, td, template, textarea, tfoot, th, thead, "
										+ "title, tr, track, ul, wbr, xmp, mi, mo, mn, ms, mtext, annotation-xml, "
										+ "foreignObject, desc, title")
								.split(", "))) {
					break done;
				}
				node = parserContext.getOpenElements().get(
						parserContext.getOpenElements().indexOf(node) - 1);
			}
			done(parserContext);
			InsertAnHTMLElement.run(parserContext, token);

		}
		/*
		 * A start tag whose tag name is one of: "dd", "dt" Run these steps: Set
		 * the frameset-ok flag to "not ok". Initialize node to be the current
		 * node (the bottommost node of the stack).
		 * 
		 * Loop: If node is a dd element, then run these substeps:
		 * 
		 * Generate implied end tags, except for dd elements.
		 * 
		 * If the current node is not a dd element, then this is a parse error.
		 * 
		 * Pop elements from the stack of open elements until a dd element has
		 * been popped from the stack.
		 * 
		 * Jump to the step labeled done below.
		 * 
		 * If node is a dt element, then run these substeps:
		 * 
		 * Generate implied end tags, except for dt elements.
		 * 
		 * If the current node is not a dt element, then this is a parse error.
		 * 
		 * Pop elements from the stack of open elements until a dt element has
		 * been popped from the stack.
		 * 
		 * Jump to the step labeled done below.
		 * 
		 * If node is in the special category, but is not an address, div, or p
		 * element, then jump to the step labeled done below.
		 * 
		 * Otherwise, set node to the previous entry in the stack of open
		 * elements and return to the step labeled loop.
		 * 
		 * Done: If the stack of open elements has a p element in button scope,
		 * then close a p element.
		 * 
		 * Finally, insert an HTML element for the token.
		 */

		else if (tokenType == TokenType.start_tag
				&& isOneOf(token.getValue(), new String[] { "dd", "dt" })) {
			parserContext.setFlagFramesetOk(false);
			Node node = parserContext.getCurrentNode();

			done: while (true) {
				if (node.getNodeName().equals("dd")) {
					GenerateImpliedEndTags.run(parserContext, "dd");
					if (!parserContext.getCurrentNode().getNodeName()
							.equals("dd")) {
						parserContext
								.addParseErrors(ParseErrorType.UnexpectedToken);
					}
					Node popped;
					do {
						popped = parserContext.getOpenElements().pop();
					} while (!popped.getNodeName().equals("dd"));
					break done;
				} else if (node.getNodeName().equals("dt")) {
					GenerateImpliedEndTags.run(parserContext, "dt");
					if (!parserContext.getCurrentNode().getNodeName()
							.equals("dt")) {
						parserContext
								.addParseErrors(ParseErrorType.UnexpectedToken);
					}
					Node popped;
					do {
						popped = parserContext.getOpenElements().pop();
					} while (!popped.getNodeName().equals("dt"));
					break done;
				}
				if (isOneOf(
						node.getNodeName(),
						new String(
								"applet, area, article, aside, base, basefont, bgsound, "
										+ "blockquote, body, br, button, caption, center, col, colgroup, dd, "
										+ "details, dir, dl, dt, embed, fieldset, figcaption, figure, "
										+ "footer, form, frame, frameset, h1, h2, h3, h4, h5, h6, head, header, "
										+ "hgroup, hr, html, iframe, img, input, isindex, li, link, listing, "
										+ "main, marquee, meta, nav, noembed, noframes, noscript, object, ol, "
										+ "param, plaintext, pre, script, section, select, source, style, "
										+ "summary, table, tbody, td, template, textarea, tfoot, th, thead, "
										+ "title, tr, track, ul, wbr, xmp, mi, mo, mn, ms, mtext, annotation-xml, "
										+ "foreignObject, desc, title")
								.split(", "))) {
					break done;
				}
				node = parserContext.getOpenElements().get(
						parserContext.getOpenElements().indexOf(node) - 1);
			}
			done(parserContext);
			InsertAnHTMLElement.run(parserContext, token);
		}
		/*
		 * start tag whose tag name is "plaintext" If the stack of open elements
		 * has a p element in button scope, then close a p element. Insert an
		 * HTML element for the token. Switch the tokenizer to the PLAINTEXT
		 * state.
		 */
		else if (tokenType == TokenType.start_tag
				&& token.getValue().equals("plaintext")) {
			if (ElementInScope.isInButtonScope(parserContext, "p")) {
				closeApElement(parserContext);
			}
			InsertAnHTMLElement.run(parserContext, token);
			TokenizerStateFactory tokenStateFactory = TokenizerStateFactory
					.getInstance();
			parserContext.getTokenizerContext().setNextState(
					tokenStateFactory.getState(TokenizerState.PLAINTEXT_state));
		}
		/*
		 * A start tag whose tag name is "button" If the stack of open elements
		 * has a button element in scope, then run these substeps: Parse error.
		 * Generate implied end tags. Pop elements from the stack of open
		 * elements until a button element has been popped from the stack.
		 * Reconstruct the active formatting elements, if any. Insert an HTML
		 * element for the token. Set the frameset-ok flag to "not ok".
		 */
		else if (tokenType == TokenType.start_tag
				&& token.getValue().equals("button")) {
			if (ElementInScope.isInScope(parserContext, "button")) {
				parserContext.addParseErrors(ParseErrorType.UnexpectedToken);
				GenerateImpliedEndTags.run(parserContext);
				while (!parserContext.getOpenElements().isEmpty()) {
					Element element = parserContext.getOpenElements().pop();
					if (element.getNodeName().equals("button")) {
						break;
					}
				}
			}
			if (!parserContext.getActiveFormattingElements().isEmpty()) {
				ListOfActiveFormattingElements.reconstruct(parserContext);
			}
			InsertAnHTMLElement.run(parserContext, token);
			parserContext.setFlagFramesetOk(false);
		}
		/*
		 * An end tag whose tag name is one of: "address", "article", "aside",
		 * "blockquote", "button", "center", "details", "dialog", "dir", "div",
		 * "dl", "fieldset", "figcaption", "figure", "footer", "header",
		 * "hgroup", "listing", "main", "nav", "ol", "pre", "section",
		 * "summary", "ul" If the stack of open elements does not have an
		 * element in scope that is an HTML element and with the same tag name
		 * as that of the token, then this is a parse error; ignore the token.
		 * Otherwise, run these steps: Generate implied end tags. If the current
		 * node is not an HTML element with the same tag name as that of the
		 * token, then this is a parse error. Pop elements from the stack of
		 * open elements until an HTML element with the same tag name as the
		 * token has been popped from the stack.
		 */
		else if (tokenType == TokenType.end_tag
				&& isOneOf(token.getValue(), new String[] { "address",
						"article", "aside", "blockquote", "button", "center",
						"details", "dialog", "dir", "div", "dl", "fieldset",
						"figcaption", "figure", "footer", "header", "hgroup",
						"listing", "main", "nav", "ol", "pre", "section",
						"summary", "ul" })) {
			if (!ElementInScope.isInScope(parserContext, token.getValue())) {
				parserContext
						.addParseErrors(ParseErrorType.UnexpectedInputCharacter);
			} else {
				GenerateImpliedEndTags.run(parserContext);
				if (!parserContext.getCurrentNode().getNodeName()
						.equals(token.getValue()))
					parserContext
							.addParseErrors(ParseErrorType.UnexpectedToken);
				while (!parserContext.getOpenElements().isEmpty()) {
					Element element = parserContext.getOpenElements().pop();
					if (element.getNodeName().equals(token.getValue())
							&& element.isHTMLElement()) {
						break;
					}
				}
			}

		}
		/*
		 * An end tag whose tag name is "form"
		 * 
		 * If there is no template element on the stack of open elements, then
		 * run these substeps:
		 * 
		 * 1 Let node be the element that the form element pointer is set to, or
		 * null if it is not set to an element.
		 * 
		 * 2 Set the form element pointer to null. Otherwise, let node be null.
		 * 
		 * 3 If node is null or if the stack of open elements does not have node
		 * in scope, then this is a parse error; abort these steps and ignore
		 * the token.
		 * 
		 * 4 Generate implied end tags.
		 * 
		 * 5 If the current node is not node, then this is a parse error.
		 * 
		 * 6 Remove node from the stack of open elements.
		 */
		else if (tokenType == TokenType.end_tag
				&& token.getValue().equals("form")) {
			if (!parserContext.openElementsContain("template")) {
				Node node = parserContext.getFormElementPointer();
				parserContext.setFormElementPointer(null);

				if (node == null
						|| !ElementInScope.isInScope(parserContext,
								node.getNodeName())) {
					parserContext
							.addParseErrors(ParseErrorType.UnexpectedToken);
					return parserContext;
				}
				GenerateImpliedEndTags.run(parserContext);
				if (parserContext.getCurrentNode() != node) {
					parserContext
							.addParseErrors(ParseErrorType.UnexpectedToken);
				}
				if (openElementStack.indexOf(node) != -1)
					openElementStack.removeElementAt(openElementStack
							.indexOf(node));
			}
			/*
			 * If there is a template element on the stack of open elements,
			 * 
			 * 1 If the stack of open elements does not have a form element in
			 * scope, then this is a parse error; abort these steps and ignore
			 * the token.
			 * 
			 * 2 Generate implied end tags.
			 * 
			 * 3 If the current node is not a form element, then this is a parse
			 * error.
			 * 
			 * 4 Pop elements from the stack of open elements until a form
			 * element has been popped from the stack.
			 */
			else {
				if (ElementInScope.isInScope(parserContext, "form")) {
					parserContext
							.addParseErrors(ParseErrorType.UnexpectedToken);
					return parserContext;
				}
				GenerateImpliedEndTags.run(parserContext);
				if (!parserContext.getCurrentNode().getNodeName()
						.equals("form")) {
					parserContext
							.addParseErrors(ParseErrorType.UnexpectedToken);
				}
				while (!parserContext.getOpenElements().isEmpty()) {
					Element element = parserContext.getOpenElements().pop();
					if (element.getNodeName().equals("form")) {
						break;
					}

				}

			}
		}
		/*
		 * An end tag whose tag name is "p" If the stack of open elements does
		 * not have a p element in button scope, then this is a parse error;
		 * insert an HTML element for a "p" start tag token with no attributes.
		 * Close a p element.
		 */
		else if (tokenType == TokenType.end_tag && token.getValue().equals("p")) {
			if (!ElementInScope.isInButtonScope(parserContext, "p")) {
				parserContext.addParseErrors(ParseErrorType.UnexpectedToken);
				InsertAnHTMLElement.run(parserContext, new TagToken(
						TokenType.start_tag, "p"));
			}
			closeApElement(parserContext);
		}
		/*
		 * An end tag whose tag name is "li" If the stack of open elements does
		 * not have an li element in list item scope, then this is a parse
		 * error; ignore the token. Otherwise, run these steps: Generate implied
		 * end tags, except for li elements. If the current node is not an li
		 * element, then this is a parse error. Pop elements from the stack of
		 * open elements until an li element has been popped from the stack.
		 */
		else if (tokenType == TokenType.end_tag
				&& token.getValue().equals("li")) {
			if (!ElementInScope.isInListItemScope(parserContext, "li")) {
				parserContext.addParseErrors(ParseErrorType.UnexpectedToken);
				return parserContext;
			} else {
				GenerateImpliedEndTags.run(parserContext, "li");
				if (!parserContext.getCurrentNode().getNodeName().equals("li")) {
					parserContext
							.addParseErrors(ParseErrorType.UnexpectedToken);
				}
				while (!parserContext.getOpenElements().isEmpty()) {
					Element element = parserContext.getOpenElements().pop();
					if (element.getNodeName().equals("li")) {
						break;
					}

				}
			}

		}
		/*
		 * An end tag whose tag name is one of: "dd", "dt" If the stack of open
		 * elements does not have an element in scope that is an HTML element
		 * and with the same tag name as that of the token, then this is a parse
		 * error; ignore the token. Otherwise, run these steps: Generate implied
		 * end tags, except for HTML elements with the same tag name as the
		 * token. If the current node is not an HTML element with the same tag
		 * name as that of the token, then this is a parse error. Pop elements
		 * from the stack of open elements until an HTML element with the same
		 * tag name as the token has been popped from the stack.
		 */
		else if (tokenType == TokenType.end_tag
				&& isOneOf(token.getValue(), new String[] { "dd", "dt" })) {

			if (!ElementInScope.isInScope(parserContext, token.getValue())) {
				parserContext
						.addParseErrors(ParseErrorType.UnexpectedInputCharacter);
			} else {
				GenerateImpliedEndTags.run(parserContext, token.getValue());
				if (!parserContext.getCurrentNode().getNodeName()
						.equals(token.getValue()))
					parserContext
							.addParseErrors(ParseErrorType.UnexpectedToken);
				while (true) {
					Element element = parserContext.getOpenElements().pop();
					if (element.getNodeName().equals(token.getValue())) {
						break;
					}
				}
			}

		}
		/*
		 * An end tag whose tag name is one of: "h1", "h2", "h3", "h4", "h5",
		 * "h6" If the stack of open elements does not have an element in scope
		 * that is an HTML element and whose tag name is one of "h1", "h2",
		 * "h3", "h4", "h5", or "h6", then this is a parse error; ignore the
		 * token. Otherwise, run these steps: Generate implied end tags. If the
		 * current node is not an HTML element with the same tag name as that of
		 * the token, then this is a parse error. Pop elements from the stack of
		 * open elements until an HTML element whose tag name is one of "h1",
		 * "h2", "h3", "h4", "h5", or "h6" has been popped from the stack.
		 */
		else if (tokenType == TokenType.end_tag
				&& isOneOf(token.getValue(), new String[] { "h1", "h2", "h3",
						"h4", "h5", "h6" })) {

			if (!ElementInScope.isInScope(parserContext, "h1,h2,h3,h4,h5,h6")) {
				parserContext
						.addParseErrors(ParseErrorType.UnexpectedInputCharacter);
			} else {
				GenerateImpliedEndTags.run(parserContext);
				if (!parserContext.getCurrentNode().getNodeName()
						.equals(token.getValue()))
					parserContext
							.addParseErrors(ParseErrorType.UnexpectedToken);
				while (true) {
					Element element = parserContext.getOpenElements().pop();
					if (isOneOf(element.getNodeName(), new String[] { "h1",
							"h2", "h3", "h4", "h5", "h6" })) {
						break;
					}
				}
			}

		}
		/*
		 * An end tag whose tag name is "sarcasm" Take a deep breath, then act
		 * as described in the "any other end tag" entry below.
		 */
		else if (tokenType == TokenType.end_tag
				&& token.getValue().equals("sarcasm")) {
			anyOtherEndTag(parserContext);
		}
		/*
		 * A start tag whose tag name is "a" If the list of active formatting
		 * elements contains an a element between the end of the list and the
		 * last marker on the list (or the start of the list if there is no
		 * marker on the list), then this is a parse error; run the adoption
		 * agency algorithm for the tag name "a", then remove that element from
		 * the list of active formatting elements and the stack of open elements
		 * if the adoption agency algorithm didn't already remove it (it might
		 * not have if the element is not in table scope).
		 * 
		 * In the non-conforming stream <a href="a">a<table><a
		 * href="b">b</table>x, the first a element would be closed upon seeing
		 * the second one, and the "x" character would be inside a link to "b",
		 * not to "a". This is despite the fact that the outer a element is not
		 * in table scope (meaning that a regular </a> end tag at the start of
		 * the table wouldn't close the outer a element). The result is that the
		 * two a elements are indirectly nested inside each other —
		 * non-conforming markup will often result in non-conforming DOMs when
		 * parsed.
		 * 
		 * Reconstruct the active formatting elements, if any.
		 * 
		 * Insert an HTML element for the token. Push onto the list of active
		 * formatting elements that element.
		 */
		else if (tokenType == TokenType.start_tag
				&& token.getValue().equals("a")) {
			ArrayList<Element> list = parserContext
					.getActiveFormattingElements();
			List<Element> sublist = new ArrayList<Element>(list.subList(
					list.lastIndexOf(null) + 1, list.size()));
			for (Element element : sublist)
				if (element.getNodeName().equals("a")) {
					parserContext
							.addParseErrors(ParseErrorType.UnexpectedToken);

					parserContext = AdoptionAgencyAlgorithm.Run(parserContext,
							token.getValue());

					list = parserContext.getActiveFormattingElements();
					list.remove(element);

					if (parserContext.getOpenElements().contains(element)) {
						parserContext.getOpenElements().remove(element);
					}
					break;
				}
			ListOfActiveFormattingElements.reconstruct(parserContext);
			Element e = InsertAnHTMLElement.run(parserContext, token);
			ListOfActiveFormattingElements.push(parserContext, e);
		}
		/*
		 * A start tag whose tag name is one of: "b", "big", "code", "em",
		 * "font", "i", "s", "small", "strike", "strong", "tt", "u" Reconstruct
		 * the active formatting elements, if any.
		 * 
		 * Insert an HTML element for the token. Push onto the list of active
		 * formatting elements that element.
		 */
		else if (tokenType == TokenType.start_tag
				&& isOneOf(token.getValue(), new String[] { "b", "big", "code",
						"em", "font", "i", "s", "small", "strike", "strong",
						"tt", "u" })) {
			ListOfActiveFormattingElements.reconstruct(parserContext);
			Element e = InsertAnHTMLElement.run(parserContext, token);
			ListOfActiveFormattingElements.push(parserContext, e);
		}
		/*
		 * A start tag whose tag name is "nobr" Reconstruct the active
		 * formatting elements, if any.
		 * 
		 * If the stack of open elements has a nobr element in scope, then this
		 * is a parse error; run the adoption agency algorithm for the tag name
		 * "nobr", then once again reconstruct the active formatting elements,
		 * if any.
		 * 
		 * Insert an HTML element for the token. Push onto the list of active
		 * formatting elements that element.
		 */

		else if (tokenType == TokenType.start_tag
				&& token.getValue().equals("nobr")) {
			ListOfActiveFormattingElements.reconstruct(parserContext);
			if (ElementInScope.isInScope(parserContext, token.getValue())) {
				parserContext.addParseErrors(ParseErrorType.UnexpectedToken);
				AdoptionAgencyAlgorithm.Run(parserContext, token.getValue());
				ListOfActiveFormattingElements.reconstruct(parserContext);
			}

			Element e = InsertAnHTMLElement.run(parserContext, token);
			ListOfActiveFormattingElements.push(parserContext, e);
		}
		/*
		 * An end tag whose tag name is one of: "a", "b", "big", "code", "em",
		 * "font", "i", "nobr", "s", "small", "strike", "strong", "tt", "u" Run
		 * the adoption agency algorithm for the token's tag name.
		 */
		else if (tokenType == TokenType.end_tag
				&& isOneOf(token.getValue(), new String[] { "a", "b", "big",
						"code", "em", "font", "i", "nobr", "s", "small",
						"strike", "strong", "tt", "u" })) {
			AdoptionAgencyAlgorithm.Run(parserContext, token.getValue());
		}
		/*
		 * A start tag whose tag name is one of: "applet", "marquee", "object"
		 * Reconstruct the active formatting elements, if any. Insert an HTML
		 * element for the token. Insert a marker at the end of the list of
		 * active formatting elements. Set the frameset-ok flag to "not ok".
		 */
		else if (tokenType == TokenType.start_tag
				&& isOneOf(token.getValue(), new String[] { "applet",
						"marquee", "object" })) {
			ListOfActiveFormattingElements.reconstruct(parserContext);
			InsertAnHTMLElement.run(parserContext, token);
			ListOfActiveFormattingElements.insertMarker(parserContext);
			parserContext.setFlagFramesetOk(false);
		}
		/*
		 * An end tag token whose tag name is one of: "applet", "marquee",
		 * "object" If the stack of open elements does not have an element in
		 * scope that is an HTML element and with the same tag name as that of
		 * the token, then this is a parse error; ignore the token. Otherwise,
		 * run these steps: Generate implied end tags. If the current node is
		 * not an HTML element with the same tag name as that of the token, then
		 * this is a parse error. Pop elements from the stack of open elements
		 * until an HTML element with the same tag name as the token has been
		 * popped from the stack. Clear the list of active formatting elements
		 * up to the last marker.
		 */
		else if (tokenType == TokenType.end_tag
				&& isOneOf(token.getValue(), new String[] { "applet",
						"marquee", "object" })) {

			if (!ElementInScope.isInScope(parserContext, token.getValue())) {
				parserContext
						.addParseErrors(ParseErrorType.UnexpectedInputCharacter);
			} else {
				GenerateImpliedEndTags.run(parserContext);
				if (!parserContext.getCurrentNode().getNodeName()
						.equals(token.getValue()))
					parserContext
							.addParseErrors(ParseErrorType.UnexpectedToken);
				while (!parserContext.getOpenElements().isEmpty()) {
					Element element = parserContext.getOpenElements().pop();
					if (element.getNodeName().equals(token.getValue())) {
						break;
					}
				}
			}
			ListOfActiveFormattingElements.clear(parserContext);
		}
		/*
		 * A start tag whose tag name is "table" If the Document is not set to
		 * quirks mode, and the stack of open elements has a p element in button
		 * scope, then close a p element. Insert an HTML element for the token.
		 * Set the frameset-ok flag to "not ok". Switch the insertion mode to
		 * "in table".
		 */
		else if (tokenType == TokenType.start_tag
				&& isOneOf(token.getValue(), new String[] { "table" })) {
			if (!parserContext.getDocument().isQuirksMode()
					&& ElementInScope.isInButtonScope(parserContext, "p"))
				closeApElement(parserContext);
			InsertAnHTMLElement.run(parserContext, token);
			parserContext.setFlagFramesetOk(false);
			parserContext.setInsertionMode(factory
					.getInsertionMode(InsertionMode.in_table));
		}
		/*
		 * An end tag whose tag name is "br"Parse error. Act as described in the
		 * next entry,as if this was a "br" start tag token, rather than an end
		 * tag token.A start tag whose tag name is one of: "area", "br",
		 * "embed", "img", "keygen", "wbr"Reconstruct the active formatting
		 * elements, if any.Insert an HTML element for the token.Immediately pop
		 * the current node off the stack of open elements.Acknowledge the
		 * token's self-closing flag, if it is set.Set the frameset-ok flag to
		 * "not ok".
		 */
		else if ((tokenType == TokenType.end_tag && isOneOf(token.getValue(),
				new String[] { "br" }))
				|| (tokenType == TokenType.start_tag && isOneOf(
						token.getValue(), new String[] { "area", "br", "embed",
								"img", "keygen", "wbr" }))) {

			if (tokenType == TokenType.end_tag && token.getValue().equals("br")) {
				parserContext.addParseErrors(ParseErrorType.UnexpectedToken);
			}
			ListOfActiveFormattingElements.reconstruct(parserContext);
			InsertAnHTMLElement.run(parserContext, token);
			parserContext.getOpenElements().pop();
			((TagToken) token).setFlagAcknowledgeSelfClosingTag(true);
			parserContext.setFlagFramesetOk(false);
		}
		/*
		 * A start tag whose tag name is "input" Reconstruct the active
		 * formatting elements, if any. Insert an HTML element for the token.
		 * Immediately pop the current node off the stack of open elements.
		 * Acknowledge the token's self-closing flag, if it is set.
		 * 
		 * If the token does not have an attribute with the name "type", or if
		 * it does, but that attribute's value is not an ASCII case-insensitive
		 * match for the string "hidden", then: set the frameset-ok flag to
		 * "not ok".
		 */
		else if (tokenType == TokenType.start_tag
				&& token.getValue().equals("input")) {
			if (!parserContext.getActiveFormattingElements().isEmpty()) {
				ListOfActiveFormattingElements.reconstruct(parserContext);
			}
			InsertAnHTMLElement.run(parserContext, token);
			parserContext.getOpenElements().pop();
			((TagToken) token).setFlagAcknowledgeSelfClosingTag(true);

			if (!((TagToken) token).hasAttribute(new String[] { "type" })
					|| !((TagToken) token).getAttribute("type").getValue()
							.equals("hidden"))
				parserContext.setFlagFramesetOk(false);
		}
		/*
		 * A start tag whose tag name is one of: "param", "source", "track"
		 * Insert an HTML element for the token. Immediately pop the current
		 * node off the stack of open elements. Acknowledge the token's
		 * self-closing flag, if it is set.
		 * 
		 * 
		 * Here is a difference wiht the WHATWG spec that have:
		 * 
		 * A start tag whose tag name is one of: "menuitem", "param", "source",
		 * "track"
		 * 
		 * Insert an HTML element for the token. Immediately pop the current
		 * node off the stack of open elements.
		 * 
		 * Acknowledge the token's self-closing flag, if it is set.
		 * 
		 * To pass the HTML5Lib5 tests, the WHATWG was implemented.
		 */
		else if (tokenType == TokenType.start_tag
				&& isOneOf(token.getValue(), new String[] { "menuitem",
						"param", "source", "track" })) {
			InsertAnHTMLElement.run(parserContext, token);
			parserContext.getOpenElements().pop();
			((TagToken) token).setFlagAcknowledgeSelfClosingTag(true);
		}
		/*
		 * A start tag whose tag name is "hr" If the stack of open elements has
		 * a p element in button scope, then close a p element. Insert an HTML
		 * element for the token. Immediately pop the current node off the stack
		 * of open elements. Acknowledge the token's self-closing flag, if it is
		 * set. Set the frameset-ok flag to "not ok".
		 */
		else if (tokenType == TokenType.start_tag
				&& isOneOf(token.getValue(), new String[] { "hr" })) {
			if (ElementInScope.isInButtonScope(parserContext, "p"))
				closeApElement(parserContext);
			InsertAnHTMLElement.run(parserContext, token);
			parserContext.getOpenElements().pop();
			((TagToken) token).setFlagAcknowledgeSelfClosingTag(true);
			parserContext.setFlagFramesetOk(false);
		}
		/*
		 * A start tag whose tag name is "image" Parse error. Change the token's
		 * tag name to "img" and reprocess it. (Don't ask.)
		 */
		else if (tokenType == TokenType.start_tag
				&& isOneOf(token.getValue(), new String[] { "image" })) {
			parserContext.addParseErrors(ParseErrorType.UnexpectedToken);
			token.setValue("img");
			parserContext.getTokenizerContext().setCurrentToken(token);
			parserContext.setFlagReconsumeToken(true);
		}
		/*
		 * A start tag whose tag name is "isindex"
		 */
		else if (tokenType == TokenType.start_tag
				&& token.getValue().equals("isindex")) {
			/*
			 * Parse error.
			 * 
			 * If there is no template element on the stack of open elements and
			 * the form element pointer is not null, then ignore the token.
			 */
			parserContext.addParseErrors(ParseErrorType.UnexpectedToken);
			if (!parserContext.openElementsContain("template")
					&& parserContext.getFormElementPointer() != null) {
				return parserContext;
				/*
				 * Otherwise:
				 */
			} else {
				/*
				 * Acknowledge the token's self-closing flag, if it is set.
				 * 
				 * Set the frameset-ok flag to "not ok".
				 * 
				 * If the stack of open elements has a p element in button
				 * scope, then close a p element.
				 */
				((TagToken) token).setFlagAcknowledgeSelfClosingTag(true);
				parserContext.setFlagFramesetOk(false);
				if (ElementInScope.isInButtonScope(parserContext, "p")) {
					closeApElement(parserContext);
				}

				/*
				 * Insert an HTML element for a "form" start tag token with no
				 * attributes, and, if there is no template element on the stack
				 * of open elements, set the form element pointer to point to
				 * the element created.
				 */
				/*
				 * If the token has an attribute called "action", set the action
				 * attribute on the resulting form element to the value of the
				 * "action" attribute of the token.
				 */
				TagToken newToken = new TagToken(TokenType.start_tag, "form");

				if (((TagToken) token).hasAttribute(new String[] { "action" })) {
					newToken.addAttribute("action", ((TagToken) token)
							.getAttribute("action").getValue());
				}
				Element newElement = InsertAnHTMLElement.run(parserContext,
						newToken);
				if (!parserContext.openElementsContain("template")) {
					parserContext.setFormElementPointer(newElement);
				}

				/*
				 * Insert an HTML element for an "hr" start tag token with no
				 * attributes. Immediately pop the current node off the stack of
				 * open elements.
				 */
				TagToken newHrToken = new TagToken(TokenType.start_tag, "hr");
				InsertAnHTMLElement.run(parserContext, newHrToken);
				parserContext.getOpenElements().pop();

				/*
				 * Reconstruct the active formatting elements, if any.
				 * 
				 * Insert an HTML element for a "label" start tag token with no
				 * attributes.
				 */

				if (!parserContext.getActiveFormattingElements().isEmpty()) {
					ListOfActiveFormattingElements.reconstruct(parserContext);
				}
				TagToken newLabelToken = new TagToken(TokenType.start_tag,
						"label");
				InsertAnHTMLElement.run(parserContext, newLabelToken);

				/* Insert characters (see below for what they should say). */
				if (((TagToken) token).hasAttribute(new String[] { "prompt" }))
					InsertCharacter.run(parserContext, ((TagToken) token)
							.getAttribute("prompt").getValue());
				else
					InsertCharacter
							.run(parserContext,
									"This is a searchable index. Enter search keywords: ");
				/*
				 * Insert an HTML element for an "input" start tag token with
				 * all the attributes from the "isindex" token except "name",
				 * "action", and "prompt", and with an attribute named "name"
				 * with the value "isindex". (This creates an input element with
				 * the name attribute set to the magic balue "isindex".)
				 * Immediately pop the current node off the stack of open
				 * elements.
				 */
				TagToken newInputToken = new TagToken(TokenType.start_tag,
						"input");
				newInputToken.addAttribute("name", "isindex");

				for (Attribute att : ((TagToken) token).getAttributes()) {
					if (!isOneOf(att.getName(), new String[] { "action",
							"prompt", "name" }))
						newInputToken.addAttribute(att.getName(),
								att.getValue());
				}
				InsertAnHTMLElement.run(parserContext, newInputToken);
				parserContext.getOpenElements().pop();

				/* Insert more characters (see below for what they should say). */
				// No more add because language is english so message is
				// complete before the input control

				/*
				 * Pop the current node (which will be the label element created
				 * earlier) off the stack of open elements.
				 */
				parserContext.getOpenElements().pop();
				/*
				 * Insert an HTML element for an "hr" start tag token with no
				 * attributes. Immediately pop the current node off the stack of
				 * open elements.
				 */
				TagToken anotherNewHrToken = new TagToken(TokenType.start_tag,
						"hr");
				InsertAnHTMLElement.run(parserContext, anotherNewHrToken);
				parserContext.getOpenElements().pop();
				/*
				 * Pop the current node (which will be the form element created
				 * earlier) off the stack of open elements, and, if there is no
				 * template element on the stack of open elements, set the form
				 * element pointer back to null.
				 */
				parserContext.getOpenElements().pop();
				if (!parserContext.openElementsContain("template")) {
					parserContext.setFormElementPointer(null);
				}

				/*
				 * Insert characters
				 * 
				 * Prompt: If the token has an attribute with the name "prompt",
				 * then the first stream of characters must be the same string
				 * as given in that attribute, and the second stream of
				 * characters must be empty. Otherwise, the two streams of
				 * character tokens together should, together with the input
				 * element, express the equivalent of
				 * "This is a searchable index. Enter search keywords: (input field)"
				 * in the user's preferred language.
				 */
			}
		}
		/*
		 * A start tag whose tag name is "textarea" Run these steps: Insert an
		 * HTML element for the token. If the next token is a "LF" (U+000A)
		 * character token, then ignore that token and move on to the next one.
		 * (Newlines at the start of textarea elements are ignored as an
		 * authoring convenience.) Switch the tokenizer to the RCDATA state. Let
		 * the original insertion mode be the current insertion mode. Set the
		 * frameset-ok flag to "not ok". Switch the insertion mode to "text".
		 */
		else if (tokenType == TokenType.start_tag
				&& isOneOf(token.getValue(), new String[] { "textarea" })) {
			InsertAnHTMLElement.run(parserContext, token);
			ignoreNextLFCharToken = true;
			TokenizerStateFactory tokenStateFactory = TokenizerStateFactory
					.getInstance();
			parserContext.getTokenizerContext().setNextState(
					tokenStateFactory.getState(TokenizerState.RCDATA_state));
			parserContext.setOriginalInsertionMode(parserContext
					.getInsertionMode());
			parserContext.setFlagFramesetOk(false);
			parserContext.setInsertionMode(factory
					.getInsertionMode(InsertionMode.text));
		}
		/*
		 * A start tag whose tag name is "xmp" If the stack of open elements has
		 * a p element in button scope, then close a p element. Reconstruct the
		 * active formatting elements, if any. Set the frameset-ok flag to
		 * "not ok". Follow the generic raw text element parsing algorithm.
		 */
		else if (tokenType == TokenType.start_tag
				&& isOneOf(token.getValue(), new String[] { "xmp" })) {
			if (ElementInScope.isInButtonScope(parserContext, "p"))
				closeApElement(parserContext);
			if (!parserContext.getActiveFormattingElements().isEmpty()) {
				ListOfActiveFormattingElements.reconstruct(parserContext);
			}
			parserContext.setFlagFramesetOk(false);
			GenericRawTextElementParsing.run(parserContext, (TagToken) token);
		}
		/*
		 * A start tag whose tag name is "iframe" Set the frameset-ok flag to
		 * "not ok". Follow the generic raw text element parsing algorithm.
		 */
		else if (tokenType == TokenType.start_tag
				&& isOneOf(token.getValue(), new String[] { "iframe" })) {
			parserContext.setFlagFramesetOk(false);
			GenericRawTextElementParsing.run(parserContext, (TagToken) token);
		}
		/*
		 * A start tag whose tag name is "noembed" A start tag whose tag name is
		 * "noscript", if the scripting flag is enabled Follow the generic raw
		 * text element parsing algorithm.
		 */
		else if ((tokenType == TokenType.start_tag && token.getValue().equals(
				"noembed"))
				|| (tokenType == TokenType.start_tag
						&& token.getValue().equals("noscript") && parserContext
							.isFlagScripting())) {
			GenericRawTextElementParsing.run(parserContext, (TagToken) token);
		}
		/*
		 * A start tag whose tag name is "select" Reconstruct the active
		 * formatting elements, if any. Insert an HTML element for the token.
		 * Set the frameset-ok flag to "not ok". TODO If the insertion mode is
		 * one of "in table", "in caption", "in table body", "in row", or
		 * "in cell", then switch the insertion mode to "in select in table".
		 * Otherwise, switch the insertion mode to "in select".
		 */
		else if ((tokenType == TokenType.start_tag && token.getValue().equals(
				"select"))) {
			if (!parserContext.getActiveFormattingElements().isEmpty()) {
				ListOfActiveFormattingElements.reconstruct(parserContext);
			}
			InsertAnHTMLElement.run(parserContext, token);
			parserContext.setFlagFramesetOk(false);
			IInsertionMode currentMode = parserContext.getInsertionMode();
			if (currentMode instanceof InTable
					|| currentMode instanceof InCaption
					|| currentMode instanceof InTableBody
					|| currentMode instanceof InRow
					|| currentMode instanceof InCell) {
				parserContext.setInsertionMode(factory
						.getInsertionMode(InsertionMode.in_select_in_table));
			} else {
				parserContext.setInsertionMode(factory
						.getInsertionMode(InsertionMode.in_select));

			}
		}
		/*
		 * A start tag whose tag name is one of: "optgroup", "option" If the
		 * current node is an option element, then pop the current node off the
		 * stack of open elements. Reconstruct the active formatting elements,
		 * if any. Insert an HTML element for the token.
		 */
		else if (tokenType == TokenType.start_tag
				&& isOneOf(token.getValue(), new String[] { "optgroup",
						"option" })) {
			if (parserContext.getOpenElements().peek().getNodeName()
					.equals("option")) {
				parserContext.getOpenElements().pop();
			}
			if (!parserContext.getActiveFormattingElements().isEmpty()) {
				ListOfActiveFormattingElements.reconstruct(parserContext);
			}
			InsertAnHTMLElement.run(parserContext, token);
		}
		/*
		 * A start tag whose tag name is one of: "rb", "rp", "rtc" If the stack
		 * of open elements has a ruby element in scope, then generate implied
		 * end tags. If the current node is not then a ruby element, this is a
		 * parse error. Insert an HTML element for the token.
		 */
		else if (tokenType == TokenType.start_tag
				&& isOneOf(token.getValue(), new String[] { "rb", "rp", "rtc" })) {
			if (ElementInScope.isInScope(parserContext, "ruby")) {
				GenerateImpliedEndTags.run(parserContext);
			}
			if (!parserContext.getCurrentNode().getNodeName().equals("ruby")) {
				parserContext.addParseErrors(ParseErrorType.UnexpectedToken);
			}
			InsertAnHTMLElement.run(parserContext, token);
		}
		/*
		 * A start tag whose tag name is "rt" If the stack of open elements has
		 * a ruby element in scope, then generate implied end tags, except for
		 * rtc elements. If the current node is not then a ruby element or an
		 * rtc element, this is a parse error. Insert an HTML element for the
		 * token.
		 */
		else if (tokenType == TokenType.start_tag
				&& token.getValue().equals("rt")) {
			if (ElementInScope.isInScope(parserContext, "ruby")) {
				GenerateImpliedEndTags.run(parserContext, "rtc");
			}
			if (!parserContext.getCurrentNode().getNodeName().equals("ruby")
					|| !parserContext.getCurrentNode().getNodeName()
							.equals("rtc")) {
				parserContext.addParseErrors(ParseErrorType.UnexpectedToken);
			}
			InsertAnHTMLElement.run(parserContext, token);
		}
		/*
		 * A start tag whose tag name is "math" Reconstruct the active
		 * formatting elements, if any. Adjust MathML attributes for the token.
		 * (This fixes the case of MathML attributes that are not all
		 * lowercase.) Adjust foreign attributes for the token. (This fixes the
		 * use of namespaced attributes, in particular XLink.) Insert a foreign
		 * element for the token, in the MathML namespace. If the token has its
		 * self-closing flag set, pop the current node off the stack of open
		 * elements and acknowledge the token's self-closing flag.
		 */
		else if (tokenType == TokenType.start_tag
				&& token.getValue().equals("math")) {
			if (!parserContext.getActiveFormattingElements().isEmpty()) {
				ListOfActiveFormattingElements.reconstruct(parserContext);
			}

			{
				parserContext.addParseEvent("8.2.5.1_5");
				parserContext.addParseEvent("8.2.5.1_7");
			}

			AdjustMathMLAttributes.run((TagToken) token);
			AdjustForeignAttributes.run((TagToken) token);
			InsertForeignElement.run(parserContext, token, Namespace.MathML);
			if (((TagToken) token).isFlagSelfClosingTag()) {
				parserContext.getOpenElements().pop();
				((TagToken) token).setFlagAcknowledgeSelfClosingTag(true);
			}

		}
		/*
		 * A start tag whose tag name is "svg" Reconstruct the active formatting
		 * elements, if any. Adjust SVG attributes for the token. (This fixes
		 * the case of SVG attributes that are not all lowercase.) Adjust
		 * foreign attributes for the token. (This fixes the use of namespaced
		 * attributes, in particular XLink in SVG.) Insert a foreign element for
		 * the token, in the SVG namespace. If the token has its self-closing
		 * flag set, pop the current node off the stack of open elements and
		 * acknowledge the token's self-closing flag.
		 */
		else if (tokenType == TokenType.start_tag
				&& token.getValue().equals("svg")) {
			if (!parserContext.getActiveFormattingElements().isEmpty()) {
				ListOfActiveFormattingElements.reconstruct(parserContext);
			}

			{
				parserContext.addParseEvent("8.2.5.1_6");
				parserContext.addParseEvent("8.2.5.1_7");
			}

			AdjustSVGAttributes.run((TagToken) token);
			AdjustForeignAttributes.run((TagToken) token);
			InsertForeignElement.run(parserContext, token, Namespace.SVG);
			if (((TagToken) token).isFlagSelfClosingTag()) {
				parserContext.getOpenElements().pop();
				((TagToken) token).setFlagAcknowledgeSelfClosingTag(true);
			}
		}
		/*
		 * A start tag whose tag name is one of: "caption", "col", "colgroup",
		 * "frame", "head", "tbody", "td", "tfoot", "th", "thead", "tr" Parse
		 * error. Ignore the token.
		 */
		else if (tokenType == TokenType.start_tag
				&& isOneOf(token.getValue(), new String[] { "caption", "col",
						"colgroup", "frame", "head", "tbody", "td", "tfoot",
						"th", "thead", "tr" })) {
			parserContext.addParseErrors(ParseErrorType.UnexpectedToken);
			return parserContext;
		}
		/*
		 * Any other start tag Reconstruct the active formatting elements, if
		 * any. Insert an HTML element for the token.
		 */
		else if (tokenType == TokenType.start_tag) {
			if (!parserContext.getActiveFormattingElements().isEmpty()) {
				ListOfActiveFormattingElements.reconstruct(parserContext);
			}
			InsertAnHTMLElement.run(parserContext, token);
		}

		else if (tokenType == TokenType.end_tag) {
			anyOtherEndTag(parserContext);
		}

		if (!(tokenType == TokenType.start_tag && isOneOf(token.getValue(),
				new String[] { "pre", "listing", "textarea" }))) {
			ignoreNextLFCharToken = false;
		}

		return parserContext;
	}

	public void anyOtherEndTag(ParserContext parserContext) {
		Token token = parserContext.getTokenizerContext().getCurrentToken();
		// Any other end tag
		// Run these steps:
		// 1 Initialize node to be the current node (the bottommost node of the
		// stack).
		ArrayList<Element> stack = new ArrayList<>();
		stack.addAll(parserContext.getOpenElements());
		// 2 Loop: If node is an HTML element with the same tag name as the
		// token, then:
		for (int i = stack.size() - 1; i >= 0; i--) {
			Element node = stack.get(i);
			if (node.getNodeName().equals(token.getValue())) {
				// 2.1 Generate implied end tags, except for HTML elements with
				// the same tag name as the token.
				GenerateImpliedEndTags.run(parserContext, token.getValue());
				// 2.2 If node is not the current node, then this is a parse
				// error.
				if (!node.equals(parserContext.getCurrentNode()))
					parserContext
							.addParseErrors(ParseErrorType.UnexpectedToken);
				// 2.3 Pop all the nodes from the current node up to node,
				// including node, then stop these steps.
				while (true) {
					Element e = parserContext.getOpenElements().pop();
					if (e.equals(node))
						return;
				}
			}
			// 3 Otherwise, if node is in the special category, then this is a
			// parse error; ignore the token, and abort these steps.
			else if (node.isSpecialElement()) {
				parserContext.addParseErrors(ParseErrorType.UnexpectedToken);
				return;
			}
			// 4 Set node to the previous entry in the stack of open elements.
			// 5 Return to the step labeled loop.
		}
	}

	private void closeApElement(ParserContext parserContext) {
		// it means that the user agent must run the following steps:
		// Generate implied end tags, except for p elements.
		// If the current node is not a p element, then this is a parse error.
		// Pop elements from the stack of open elements until a p element has
		// been popped from the stack.

		parserContext.addParseEvent("8.2.5.4.7_1");

		GenerateImpliedEndTags.run(parserContext, "p");
		if (!parserContext.getCurrentNode().getNodeName().equals("p"))
			parserContext.addParseErrors(ParseErrorType.UnexpectedToken);
		while (!parserContext.getOpenElements().isEmpty()) {
			Element e = parserContext.getOpenElements().pop();
			if (e.getNodeName().equals("p"))
				return;
		}
	}

	private Boolean isOneOf(String value, String[] values) {
		for (String s : values)
			if (s.equals(value))
				return true;

		return false;
	}

	private void done(ParserContext parserContext) {
		if (ElementInScope.isInButtonScope(parserContext, "p")) {
			closeApElement(parserContext);
		}
	}

	public boolean isIgnoreNextLFCharToken() {
		return ignoreNextLFCharToken;
	}

	public void setIgnoreNextLFCharToken(boolean ignoreNextLFCharToken) {
		this.ignoreNextLFCharToken = ignoreNextLFCharToken;
	}
}
