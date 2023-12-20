package com.html5parser.algorithms;

import com.html5dom.Element;
import com.html5dom.Node;
import com.html5parser.classes.ParserContext;

public class AppropiatePlaceForInsertingANode {

	public static AdjustedInsertionLocation run(ParserContext parserContext) {
		return AppropiatePlaceForInsertingANode.run(parserContext, null);
	}

	public static AdjustedInsertionLocation run(ParserContext parserContext,
			Node overrideTarget) {

		Node target;
		Node adjustedInsertionLocation = null;

		if (overrideTarget != null)
			target = overrideTarget;
		else
			target = parserContext.getOpenElements().peek();

		String name = target.getNodeName();

		
			parserContext.addParseEvent("8.2.5.1_1",
					"Override target \"" + name + "\"");

		if (parserContext.isFlagFosterParenting()
				&& (name.equals("table") || name.equals("tbody")
						|| name.equals("tfoot") || name.equals("thead") || name
							.equals("tr"))) {

			Element lastTemplate = null;
			int templateIndex = 0;
			for (int i = parserContext.getOpenElements().size() - 1; i >= 0; i--) {
				Element element = parserContext.getOpenElements().get(i);
				if (element.getNodeName().equals("template")) {
					lastTemplate = element;
					templateIndex = i;
					break;
				}
			}

			Element lastTable = null;
			int tableIndex = 0;
			for (int i = parserContext.getOpenElements().size() - 1; i >= 0; i--) {
				Element element = parserContext.getOpenElements().get(i);
				if (element.getNodeName().equals("table")) {
					lastTable = element;
					tableIndex = i;
					break;
				}
			}

			// If there is a last template and either there is no last
			// table, or there is one, but last template is lower (more
			// recently added) than last table in the stack of open
			// elements, then: let adjusted insertion location be inside
			// last template's template contents, after its last child (if
			// any), and abort these substeps.
			// templateIndex>tableIndex means if the last template was added
			// later than the last table
			if (lastTemplate != null
					&& (lastTable == null || templateIndex > tableIndex)) {
				// adjustedInsertionLocation =
				// getDocumentFragment(adjustedInsertionLocation);
				adjustedInsertionLocation = lastTemplate;
				return new AdjustedInsertionLocation(adjustedInsertionLocation,
						null);
			}

			// If there is no last table, then let adjusted insertion
			// location be inside the first element in the stack of open
			// elements (the html element), after its last child (if any),
			// and abort these substeps. (fragment case)
			if (lastTable == null) {
				return new AdjustedInsertionLocation(parserContext
						.getOpenElements().get(0), null);
			}

			// If last table has a parent node, then let adjusted insertion
			// location be inside last table's parent node, immediately
			// before last table, and abort these substeps.
			if (lastTable.getParentNode() != null) {
				return new AdjustedInsertionLocation(lastTable.getParentNode(),
						lastTable);
			}

			// Let previous element be the element immediately above last
			// table in the stack of open elements.
			Node previousElement = parserContext.getOpenElements().get(
					tableIndex - 1);

			// Let adjusted insertion location be inside previous element,
			// after its last child (if any).
			adjustedInsertionLocation = previousElement;
		} else {
			adjustedInsertionLocation = target;
		}

		// If the adjusted insertion location is inside a template element, let
		// it instead be inside the template element's template contents, after
		// its last child (if any).

		if (adjustedInsertionLocation.getNodeName().equals("template")) {
			adjustedInsertionLocation = getDocumentFragment(adjustedInsertionLocation);
		}

		return new AdjustedInsertionLocation(adjustedInsertionLocation, null);
	}

	private static Node getDocumentFragment(Node node) {
		// NodeList children = node.getChildNodes();
		// for (int i = 0; i < children.getLength(); i++) {
		// if (children.item(i).getNodeType() == Node.DOCUMENT_FRAGMENT_NODE) {
		// return children.item(i);
		// }
		// }
		// return null;
		return node;
	}

}
