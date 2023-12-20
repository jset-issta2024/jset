package com.html5parser.algorithms;

import com.html5dom.Document;
import com.html5dom.Element;
import com.html5dom.Node;
import com.html5parser.classes.ParserContext;
import com.html5parser.classes.Token;
import com.html5parser.classes.Token.TokenType;
import com.html5parser.classes.token.TagToken;
import com.html5parser.classes.token.TagToken.Attribute;
import com.html5parser.constants.Namespace;
import com.html5parser.tracer.ParseError.ParseErrorType;

public class CreateAnElementForAToken {
	public static Element run(Node intendedParentElement, String namespace,
			Token currentToken, ParserContext parserContext) {

		parserContext.addParseEvent("8.2.5.1_2", currentToken);

		// Create a node implementing the interface appropriate for the element
		// type corresponding to the tag name of the token in given namespace
		// (as given in the specification that defines that element, e.g. for an
		// a element in the HTML namespace, this specification defines it to be
		// the HTMLAnchorElement interface), with the tag name being the name of
		// that element, with the node being in the given namespace, and with
		// the attributes on the node being those given in the given token.
		//
		// The interface appropriate for an element in the HTML namespace that
		// is not defined in this specification (or other applicable
		// specifications) is HTMLUnknownElement. Elements in other namespaces
		// whose interface is not defined by that namespace's specification must
		// use the interface Element.
		//
		// The node document of the newly created element must be the node
		// document of the intended parent.

		Document doc = intendedParentElement.getOwnerDocument();

		// If doc is null it means it is the document
		if (doc == null)
			doc = ((Document) intendedParentElement);

		Element element = doc.createElementNS(namespace,
				currentToken.getValue());
		// Element element = doc.createElement(currentToken.getValue());
		// The new element saves a reference of the token that created it
		element.setUserData("0", currentToken);
		
		parserContext.countElement(element);

		if (currentToken.getType().equals(TokenType.start_tag)) {
			for (Attribute attribute : ((TagToken) currentToken)
					.getAttributes()) {

				element.setAttributeNS(attribute.getNamespace(),
						attribute.getName(), attribute.getValue());

				// If the newly created element has an xmlns attribute in
				// the XMLNS namespace whose value is not exactly the same as
				// the element's namespace, that is a parse error. Similarly, if
				// the newly created element has an xmlns:xlink attribute in the
				// XMLNS namespace whose value is not the XLink Namespace, that
				// is a parse error.
				if ((attribute.getName().equals("xmlns")
						&& namespace.equals(Namespace.XMLNS) && !attribute
						.getValue().equals(Namespace.XMLNS))
						|| (attribute.getName().equals("xmlns:xlink")
								&& namespace.equals(Namespace.XMLNS) && !attribute
								.getValue().equals(Namespace.XLink))) {
					parserContext.addParseErrors(
							ParseErrorType.InvalidNamespace,
							attribute.getValue(), "8.2.5.1_2_2");
				}

			}
		}

		// If the newly created element is a resettable element, invoke its
		// reset algorithm. (This initialises the element's value and
		// checkedness based on the element's attributes.)

		// input
		// keygen
		// output
		// select
		// textarea

		String elementName = element.getTagName();

		if (elementName.equalsIgnoreCase("input")
				|| elementName.equalsIgnoreCase("keygen")
				|| elementName.equalsIgnoreCase("output")
				|| elementName.equalsIgnoreCase("select")
				|| elementName.equalsIgnoreCase("textarea")) {
			// TODO perform reset algorithm. this is different for each type of
			// element
		}

		// TODO If the element is a form-associated element, and the form
		// element
		// pointer is not null, and there is no template element on the stack of
		// open elements, and the newly created element is either not
		// reassociateable or doesn't have a form attribute, and the intended
		// parent is in the same home subtree as the element pointed to by the
		// form element pointer, associate the newly created element with the
		// form element pointed to by the form element pointer, and suppress the
		// running of the reset the form owner algorithm when the parser
		// subsequently attempts to insert the element.
		return element;
	}

}