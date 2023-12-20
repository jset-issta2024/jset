package com.html5parser.parser;

//import gov.nasa.jpf.jdart.Debug;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import com.html5dom.Document;
import com.html5dom.Document.QuirksMode;
import com.html5dom.Element;
import com.html5dom.Node;
import com.html5parser.algorithms.ForeignContent;
import com.html5parser.algorithms.ParsingHTMLFragments;
import com.html5parser.algorithms.TreeCostructionDispatcher;
import com.html5parser.classes.ParserContext;
import com.html5parser.classes.Token;
import com.html5parser.classes.Token.TokenType;
import com.html5parser.classes.TokenizerContext;
import com.html5parser.classes.token.DocTypeToken;
import com.html5parser.classes.token.TagToken;
import com.html5parser.classes.token.TagToken.Attribute;
import com.html5parser.interfaces.IParser;
import com.html5parser.tracer.ParseError.ParseErrorType;
import com.html5parser.tracer.Tracer;

public class Parser implements IParser {

	Document doc;
	ParserContext parserContext;
	Tokenizer tokenizer;
	StreamPreprocessor streamPreprocessor;
	TreeConstructor treeConstructor;
	Tracer tracer;

	public Parser() {
		initialize(false, false);
	}

	public Parser(boolean scriptFlag) {
		initialize(scriptFlag, false);
	}

	public Parser(boolean scriptFlag, boolean trace) {
		initialize(scriptFlag, trace);
	}

	private void initialize(boolean scriptFlag, boolean trace) {
		parserContext = new ParserContext();
		tokenizer = new Tokenizer();
		streamPreprocessor = new StreamPreprocessor();
		treeConstructor = new TreeConstructor();
		doc = createNewDocument();
		if (trace) {
			tracer = new Tracer();
			parserContext.setTracer(tracer);
		}

		parserContext.setDocument(doc);
		parserContext.setFlagScripting(scriptFlag);

	}

	public Document parse(String htmlString) {
		return parse(new ByteArrayInputStream(
				htmlString.getBytes(StandardCharsets.UTF_8)));
	}

	public Document parse(InputStream stream) {

		BufferedReader in;
		try {

			in = new BufferedReader(new InputStreamReader(stream, "UTF-8"));

			int currentChar = in.read();
			// One leading U+FEFF BYTE ORDER MARK character must be ignored if
			// any are present in the input stream.
			if (currentChar == 0xFEFF)
				currentChar = in.read();
//			Debug.printCurrentPC();
			while (!parserContext.isFlagStopParsing()) {
				TokenizerContext tokenizerContext = parserContext
						.getTokenizerContext();
				/*
				 * Preprocess character
				 */
				Integer preProCurrentChar = streamPreprocessor
						.processLFAndCRCharacters(currentChar);
				// Ignore the LF characters that immediately follow a CR
				// character
				while (preProCurrentChar == 0x000D) {
					currentChar = in.read();
					preProCurrentChar = streamPreprocessor
							.processLFAndCRCharacters(currentChar);
				}
				// If invalid character add a parse error
				if (streamPreprocessor.isInvalidCharacter(preProCurrentChar)
						&& tokenizerContext.getCurrentInputCharacter() != preProCurrentChar) {
					tokenizerContext
							.setCurrentInputCharacter(preProCurrentChar);
					parserContext
							.addParseErrors(ParseErrorType.InvalidInputCharacter);
				}
				tokenizerContext.setCurrentInputCharacter(preProCurrentChar);
				parserContext = tokenizer.tokenize(parserContext);

				/*
				 * If not reconsume, then read next character of the stream
				 */
				if (!tokenizerContext.isFlagReconsumeCurrentInputCharacter()) {
					currentChar = in.read();
				} else {
					tokenizerContext
							.setFlagReconsumeCurrentInputCharacter(false);
				}

				// for (Token tok : parserContext.getTokenizerContext()
				// .getTokens()) {
				// System.out.println(tok.getType() + " : " + tok.getValue());
				// }

				/*
				 * Consume all the tokens emited
				 */
				if (tokenizerContext.isFlagEmitToken()) {
					while (!parserContext.getTokenizerContext().getTokens()
							.isEmpty()) {
						/*
						 * Get the next token of the queue for the tree
						 * constructor
						 */
						Token token = parserContext.getTokenizerContext()
								.pollCurrentToken();
						do {
							parserContext.setFlagReconsumeToken(false);

							if (TreeCostructionDispatcher
									.processTokenInInsertionMode(parserContext)) {
								parserContext = treeConstructor
										.consumeToken(parserContext);
							} else {
								parserContext = ForeignContent
										.run(parserContext);
							}

						} while (parserContext.isFlagReconsumeToken());

						/*
						 * If start tag with self-closing flag set and not
						 * acknowledged, then is a parse error.
						 */
						if (token.getType().equals(TokenType.start_tag)) {
							if (((TagToken) token).isFlagSelfClosingTag()
									&& !((TagToken) token)
											.isFlagAcknowledgeSelfClosingTag())
								parserContext
										.addParseErrors(ParseErrorType.StartTagWithSelfClosingFlag);
						}

						parserContext.countToken();
					}
					parserContext.getTokenizerContext().setFlagEmitToken(false);
				}

			}
			// TODO Run stop parsing algorithm
			// StopParsing.run(parserContext);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return parserContext.getDocument();
	}

	public Node parseFragment(String htmlString, String contextElement) {
		Document document = createNewDocument();
		ParserContext parserContext = new ParserContext();
		Element element = createElement(document, contextElement);

		try {
			List<Node> result = ParsingHTMLFragments.run(parserContext,
					element, htmlString);

			int size = result.size();
			for (int i = 0; i < size; i++) {
				Node node = result.get(i);
				// System.out.println(node);
				Node adopted = document.importNode(node, true);
				element.appendChild(adopted);
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return element;
	}

	private Document createNewDocument() {
		Document document = new Document();
		document.setQuirksMode(QuirksMode.noQuirks);
		return document;
	}

	private Element createElement(Document doc, String context) {
		context = context.trim();
		if (!context.contains(" "))
			return doc.createElementNS("http://www.w3.org/1999/xhtml", context);

		String[] contextSplit = context.split(" ");
		String namespace = null;
		String name = contextSplit[1];

		switch (contextSplit[0]) {
		case "math":
			namespace = "http://www.w3.org/1998/Math/MathML";
			break;
		case "svg":
			namespace = "http://www.w3.org/2000/svg";
			break;
		}
		return doc.createElementNS(namespace, name);
	}

	public ParserContext tokenize(ParserContext parserContext, String string) {
		BufferedReader in = null;

		Tokenizer tokenizer = new Tokenizer();
		StreamPreprocessor streamPreprocessor = new StreamPreprocessor();

		preprocessing(parserContext, string);

		try {
			in = new BufferedReader(new InputStreamReader(
					new ByteArrayInputStream(string.getBytes()), "UTF8"));

			int nextChar = 0;// for handling unicode surrogates
			int currentChar = in.read();
			Boolean stop = false;
			do {
				TokenizerContext tokenizerContext = parserContext
						.getTokenizerContext();

				/*
				 * Preprocess character
				 */
				Integer preProCurrentChar = streamPreprocessor
						.processLFAndCRCharacters(currentChar);
				// Ignore the LF characters that immediately follow a CR
				// character
				while (preProCurrentChar == 0x000D) {
					currentChar = in.read();
					preProCurrentChar = streamPreprocessor
							.processLFAndCRCharacters(currentChar);
				}

				if (preProCurrentChar >= 0xD800 && preProCurrentChar <= 0xDFBB) {// is
					// high
					// surrogate?
					in.mark(1);
					nextChar = in.read();
					if (nextChar >= 0xDC00 && nextChar <= 0xDFFF) {// is low
						// surrogate?
						preProCurrentChar = Character.toCodePoint(
								(char) preProCurrentChar.intValue(),
								(char) nextChar);
					}
				}

				// if (nextChar == 0) {
				// if (preProCurrentChar >= 0xD800
				// && preProCurrentChar <= 0xDFBB) {// is high
				// // surrogate?
				// nextChar = in.read();
				// if (nextChar >= 0xDC00 && nextChar <= 0xDFFF) {// is low
				// // surrogate?
				// preProCurrentChar = Character.toCodePoint(
				// (char) preProCurrentChar.intValue(),
				// (char) nextChar);
				// nextChar = 0;
				// } else {
				// // if not a surrogate, process both
				// tokenizerContext
				// .setFlagReconsumeCurrentInputCharacter(true);
				// }
				// }
				// } else {// process the second char if was not a surrogate
				// pair
				// preProCurrentChar = nextChar;
				// nextChar = 0;
				// }

				// If invalid character add a parse error
				if (streamPreprocessor.isInvalidCharacter(preProCurrentChar)
						&& tokenizerContext.getCurrentInputCharacter() != preProCurrentChar) {
					tokenizerContext
							.setCurrentInputCharacter(preProCurrentChar);
					parserContext
							.addParseErrors(ParseErrorType.InvalidInputCharacter);
				}

				tokenizerContext.setCurrentInputCharacter(preProCurrentChar);

				parserContext = tokenizer.tokenize(parserContext);
				stop = currentChar == -1
						&& !tokenizerContext
								.isFlagReconsumeCurrentInputCharacter();
				/*
				 * If not reconsume, then read next character of the stream
				 */
				if (!tokenizerContext.isFlagReconsumeCurrentInputCharacter()) {
					currentChar = in.read();
					nextChar = 0;
				} else {
					tokenizerContext
							.setFlagReconsumeCurrentInputCharacter(false);
					if (nextChar != 0)
						in.reset();
				}

			} while (!stop);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return parserContext;
	}

	private void preprocessing(ParserContext parserContext, String string) {
		char[] charArray = string.toCharArray();
		if (charArray.length == 0)
			return;
		if (charArray.length == 1) {
			if (Character.isSurrogate(charArray[0]))
				parserContext
						.addParseErrors(ParseErrorType.InvalidInputCharacter);

		} else {
			for (int i = 0; i < charArray.length - 1; i++) {
				if (Character.isHighSurrogate(charArray[i])) {
					if (!Character.isSurrogatePair(charArray[i],
							charArray[i + 1]))
						parserContext
								.addParseErrors(ParseErrorType.InvalidInputCharacter);
				}
			}

			for (int i = 1; i < charArray.length; i++) {
				if (Character.isLowSurrogate(charArray[i])) {
					if (!Character.isSurrogatePair(charArray[i - 1],
							charArray[i]))
						parserContext
								.addParseErrors(ParseErrorType.InvalidInputCharacter);
				}
			}
			if (Character.isLowSurrogate(charArray[0])) {
				parserContext
						.addParseErrors(ParseErrorType.InvalidInputCharacter);
			}
			if (Character.isHighSurrogate(charArray[charArray.length - 1])) {
				parserContext
						.addParseErrors(ParseErrorType.InvalidInputCharacter);
			}

		}

	}

	public void printTokens(ParserContext parserContext) {
		System.out.println("*** TOKENS ***\n");
		for (Token token : parserContext.getTokenizerContext().getTokens()) {
			switch (token.getType()) {
			case end_of_file:
				System.out.println("EOF");
				break;
			case character:
			case comment:
				System.out.println(token.getType() + " : " + token.getValue());
				break;
			case DOCTYPE:
				DocTypeToken docTypeToken = (DocTypeToken) token;
				System.out.println(docTypeToken.getType() + " : "
						+ docTypeToken.getValue() + " public id. "
						+ docTypeToken.getPublicIdentifier() + " system id. "
						+ docTypeToken.getSystemIdentifier()
						+ " force-quirks flag "
						+ docTypeToken.isForceQuircksFlag());
				break;
			case end_tag:
			case start_tag:
				TagToken tagToken = (TagToken) token;
				System.out.println(tagToken.getType() + " : "
						+ tagToken.getValue() + " self-closing flag "
						+ tagToken.isFlagSelfClosingTag() + " attributes: ");
				for (Attribute att : tagToken.getAttributes()) {
					System.out.println(att.getName() + " : " + att.getValue());
				}
				break;
			default:
				System.out.println("Error");
				break;
			}

		}
	}

	public Document getDoc() {
		return doc;
	}

	public void setDoc(Document doc) {
		parserContext.setDocument(doc);
		this.doc = doc;
	}

	public ParserContext getParserContext() {
		return parserContext;
	}

	public void setParserContext(ParserContext parserContext) {
		this.parserContext = parserContext;
	}

}