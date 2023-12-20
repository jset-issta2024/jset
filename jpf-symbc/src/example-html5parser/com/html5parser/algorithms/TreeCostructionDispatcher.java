package com.html5parser.algorithms;

import com.html5dom.Element;
import com.html5parser.classes.ParserContext;
import com.html5parser.classes.Token;
import com.html5parser.classes.Token.TokenType;

public class TreeCostructionDispatcher {

	/**
	 * 
	 * @param parserContext
	 * @return true if process with an insertion mode false if process as a
	 *         foreign content.
	 */
	public static Boolean processTokenInInsertionMode(
			ParserContext parserContext) {

		
			parserContext.addParseEvent("8.2.5_1");

		/*
		 * If there is no adjusted current node
		 * 
		 * If the adjusted current node is an element in the HTML namespace
		 * 
		 * If the adjusted current node is a MathML text integration point and
		 * the token is a start tag whose tag name is neither "mglyph" nor
		 * "malignmark"
		 * 
		 * If the adjusted current node is a MathML text integration point and
		 * the token is a character token
		 * 
		 * If the adjusted current node is an annotation-xml element in the
		 * MathML namespace and the token is a start tag whose tag name is "svg"
		 * 
		 * If the adjusted current node is an HTML integration point and the
		 * token is a start tag
		 * 
		 * If the adjusted current node is an HTML integration point and the
		 * token is a character token
		 * 
		 * If the token is an end-of-file token
		 * 
		 * Process the token according to the rules given in the section
		 * corresponding to the current insertion mode in HTML content.
		 * 
		 * Otherwise
		 * 
		 * Process the token according to the rules given in the section for
		 * parsing tokens in foreign content.
		 */
		Element adjustedCurrentNode = parserContext.getAdjustedCurrentNode();
		Token currentToken = parserContext.getTokenizerContext()
				.getCurrentToken();
		if (adjustedCurrentNode == null
				|| (adjustedCurrentNode.isHTMLElement())
				|| ((IntegrationPoint
						.isMathMLTextIntegrationPoint(adjustedCurrentNode) && currentToken
						.getType().equals(TokenType.start_tag)) && (!currentToken
						.getValue().equals("mglyph") && !currentToken
						.getValue().equals("malignmark")))
				|| (IntegrationPoint
						.isMathMLTextIntegrationPoint(adjustedCurrentNode) && currentToken
						.getType().equals(TokenType.character))
				|| (adjustedCurrentNode.isMathMLElement()
						&& adjustedCurrentNode.getNodeName().equals(
								"annotation-xml")
						&& currentToken.getType().equals(TokenType.start_tag) && currentToken
						.getValue().equals("svg"))
				|| (IntegrationPoint
						.isHtmlIntegrationPoint(adjustedCurrentNode) && currentToken
						.getType().equals(TokenType.start_tag))
				|| (IntegrationPoint
						.isHtmlIntegrationPoint(adjustedCurrentNode) && currentToken
						.getType().equals(TokenType.character))
				|| currentToken.getType().equals(TokenType.end_of_file)) {
			return true;
		}
		return false;

	}

}
