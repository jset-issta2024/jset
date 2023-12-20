package com.antlr.javaexpression;// $ANTLR 2.7.7 (20060906): "java_expression.g" -> "JavaExpressionParser.java"$

import antlr.TokenBuffer;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.ANTLRException;
import antlr.LLkParser;
import antlr.Token;
import antlr.TokenStream;
import antlr.RecognitionException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.ParserSharedInputState;
import antlr.collections.impl.BitSet;
import antlr.collections.AST;
import java.util.Hashtable;
import antlr.ASTFactory;
import antlr.ASTPair;
import antlr.collections.impl.ASTArray;

public class JavaExpressionParser extends LLkParser       implements JavaExpressionParserTokenTypes
 {

protected JavaExpressionParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public JavaExpressionParser(TokenBuffer tokenBuf) {
  this(tokenBuf,2);
}

protected JavaExpressionParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public JavaExpressionParser(TokenStream lexer) {
  this(lexer,2);
}

public JavaExpressionParser(ParserSharedInputState state) {
  super(state,2);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

	public final void statement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST statement_AST = null;
		
		try {      // for error handling
			expression();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				statement_AST = (AST)currentAST.root;
				statement_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(STATEMENT,"statement")).add(statement_AST));
				currentAST.root = statement_AST;
				currentAST.child = statement_AST!=null &&statement_AST.getFirstChild()!=null ?
					statement_AST.getFirstChild() : statement_AST;
				currentAST.advanceChildToEnd();
			}
			match(Token.EOF_TYPE);
			statement_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		returnAST = statement_AST;
	}
	
	public final void expression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expression_AST = null;
		
		try {      // for error handling
			conditionalExpression();
			astFactory.addASTChild(currentAST, returnAST);
			expression_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		returnAST = expression_AST;
	}
	
	public final void conditionalExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST conditionalExpression_AST = null;
		
		try {      // for error handling
			conditionalOrExpression();
			astFactory.addASTChild(currentAST, returnAST);
			{
			switch ( LA(1)) {
			case QUESTION:
			{
				AST tmp2_AST = null;
				tmp2_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp2_AST);
				match(QUESTION);
				expression();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp3_AST = null;
				tmp3_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp3_AST);
				match(COLON);
				conditionalExpression();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case EOF:
			case RPAREN:
			case RBRACK:
			case COMMA:
			case COLON:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			conditionalExpression_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		returnAST = conditionalExpression_AST;
	}
	
	public final void expressionList() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expressionList_AST = null;
		
		try {      // for error handling
			expression();
			astFactory.addASTChild(currentAST, returnAST);
			{
			switch ( LA(1)) {
			case COMMA:
			{
				match(COMMA);
				expressionList();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case RPAREN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				expressionList_AST = (AST)currentAST.root;
				expressionList_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(EXPRESSION_LIST,"expression_list")).add(expressionList_AST));
				currentAST.root = expressionList_AST;
				currentAST.child = expressionList_AST!=null &&expressionList_AST.getFirstChild()!=null ?
					expressionList_AST.getFirstChild() : expressionList_AST;
				currentAST.advanceChildToEnd();
			}
			expressionList_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_2);
			} else {
			  throw ex;
			}
		}
		returnAST = expressionList_AST;
	}
	
	public final void conditionalOrExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST conditionalOrExpression_AST = null;
		
		try {      // for error handling
			conditionalAndExpression();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop111:
			do {
				if ((LA(1)==OR)) {
					if ( inputState.guessing==0 ) {
						conditionalOrExpression_AST = (AST)currentAST.root;
						conditionalOrExpression_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(BINARY_EXPRESSION,"binary_expression")).add(conditionalOrExpression_AST));
						currentAST.root = conditionalOrExpression_AST;
						currentAST.child = conditionalOrExpression_AST!=null &&conditionalOrExpression_AST.getFirstChild()!=null ?
							conditionalOrExpression_AST.getFirstChild() : conditionalOrExpression_AST;
						currentAST.advanceChildToEnd();
					}
					AST tmp5_AST = null;
					tmp5_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp5_AST);
					match(OR);
					conditionalAndExpression();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop111;
				}
				
			} while (true);
			}
			conditionalOrExpression_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_3);
			} else {
			  throw ex;
			}
		}
		returnAST = conditionalOrExpression_AST;
	}
	
	public final void conditionalAndExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST conditionalAndExpression_AST = null;
		
		try {      // for error handling
			inclusiveOrExpression();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop114:
			do {
				if ((LA(1)==AND)) {
					if ( inputState.guessing==0 ) {
						conditionalAndExpression_AST = (AST)currentAST.root;
						conditionalAndExpression_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(BINARY_EXPRESSION,"binary_expression")).add(conditionalAndExpression_AST));
						currentAST.root = conditionalAndExpression_AST;
						currentAST.child = conditionalAndExpression_AST!=null &&conditionalAndExpression_AST.getFirstChild()!=null ?
							conditionalAndExpression_AST.getFirstChild() : conditionalAndExpression_AST;
						currentAST.advanceChildToEnd();
					}
					AST tmp6_AST = null;
					tmp6_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp6_AST);
					match(AND);
					inclusiveOrExpression();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop114;
				}
				
			} while (true);
			}
			conditionalAndExpression_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_4);
			} else {
			  throw ex;
			}
		}
		returnAST = conditionalAndExpression_AST;
	}
	
	public final void inclusiveOrExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST inclusiveOrExpression_AST = null;
		
		try {      // for error handling
			exclusiveOrExpression();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop117:
			do {
				if ((LA(1)==BITOR)) {
					if ( inputState.guessing==0 ) {
						inclusiveOrExpression_AST = (AST)currentAST.root;
						inclusiveOrExpression_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(BINARY_EXPRESSION,"binary_expression")).add(inclusiveOrExpression_AST));
						currentAST.root = inclusiveOrExpression_AST;
						currentAST.child = inclusiveOrExpression_AST!=null &&inclusiveOrExpression_AST.getFirstChild()!=null ?
							inclusiveOrExpression_AST.getFirstChild() : inclusiveOrExpression_AST;
						currentAST.advanceChildToEnd();
					}
					AST tmp7_AST = null;
					tmp7_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp7_AST);
					match(BITOR);
					exclusiveOrExpression();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop117;
				}
				
			} while (true);
			}
			inclusiveOrExpression_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_5);
			} else {
			  throw ex;
			}
		}
		returnAST = inclusiveOrExpression_AST;
	}
	
	public final void exclusiveOrExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST exclusiveOrExpression_AST = null;
		
		try {      // for error handling
			andExpression();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop120:
			do {
				if ((LA(1)==CARET)) {
					if ( inputState.guessing==0 ) {
						exclusiveOrExpression_AST = (AST)currentAST.root;
						exclusiveOrExpression_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(BINARY_EXPRESSION,"binary_expression")).add(exclusiveOrExpression_AST));
						currentAST.root = exclusiveOrExpression_AST;
						currentAST.child = exclusiveOrExpression_AST!=null &&exclusiveOrExpression_AST.getFirstChild()!=null ?
							exclusiveOrExpression_AST.getFirstChild() : exclusiveOrExpression_AST;
						currentAST.advanceChildToEnd();
					}
					AST tmp8_AST = null;
					tmp8_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp8_AST);
					match(CARET);
					andExpression();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop120;
				}
				
			} while (true);
			}
			exclusiveOrExpression_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_6);
			} else {
			  throw ex;
			}
		}
		returnAST = exclusiveOrExpression_AST;
	}
	
	public final void andExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST andExpression_AST = null;
		
		try {      // for error handling
			equalityExpression();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop123:
			do {
				if ((LA(1)==BITAND)) {
					if ( inputState.guessing==0 ) {
						andExpression_AST = (AST)currentAST.root;
						andExpression_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(BINARY_EXPRESSION,"binary_expression")).add(andExpression_AST));
						currentAST.root = andExpression_AST;
						currentAST.child = andExpression_AST!=null &&andExpression_AST.getFirstChild()!=null ?
							andExpression_AST.getFirstChild() : andExpression_AST;
						currentAST.advanceChildToEnd();
					}
					AST tmp9_AST = null;
					tmp9_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp9_AST);
					match(BITAND);
					equalityExpression();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop123;
				}
				
			} while (true);
			}
			andExpression_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_7);
			} else {
			  throw ex;
			}
		}
		returnAST = andExpression_AST;
	}
	
	public final void equalityExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST equalityExpression_AST = null;
		
		try {      // for error handling
			instanceofExpression();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop127:
			do {
				if ((LA(1)==EQUAL||LA(1)==NOTEQUAL)) {
					if ( inputState.guessing==0 ) {
						equalityExpression_AST = (AST)currentAST.root;
						equalityExpression_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(BINARY_EXPRESSION,"binary_expression")).add(equalityExpression_AST));
						currentAST.root = equalityExpression_AST;
						currentAST.child = equalityExpression_AST!=null &&equalityExpression_AST.getFirstChild()!=null ?
							equalityExpression_AST.getFirstChild() : equalityExpression_AST;
						currentAST.advanceChildToEnd();
					}
					{
					switch ( LA(1)) {
					case EQUAL:
					{
						AST tmp10_AST = null;
						tmp10_AST = astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp10_AST);
						match(EQUAL);
						break;
					}
					case NOTEQUAL:
					{
						AST tmp11_AST = null;
						tmp11_AST = astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp11_AST);
						match(NOTEQUAL);
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					instanceofExpression();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop127;
				}
				
			} while (true);
			}
			equalityExpression_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_8);
			} else {
			  throw ex;
			}
		}
		returnAST = equalityExpression_AST;
	}
	
	public final void instanceofExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST instanceofExpression_AST = null;
		
		try {      // for error handling
			relationalExpression();
			astFactory.addASTChild(currentAST, returnAST);
			{
			switch ( LA(1)) {
			case INSTANCEOF:
			{
				if ( inputState.guessing==0 ) {
					instanceofExpression_AST = (AST)currentAST.root;
					instanceofExpression_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(INSTANCEOF_BINARY_EXPRESSION,"instanceof_binary_expression")).add(instanceofExpression_AST));
					currentAST.root = instanceofExpression_AST;
					currentAST.child = instanceofExpression_AST!=null &&instanceofExpression_AST.getFirstChild()!=null ?
						instanceofExpression_AST.getFirstChild() : instanceofExpression_AST;
					currentAST.advanceChildToEnd();
				}
				AST tmp12_AST = null;
				tmp12_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp12_AST);
				match(INSTANCEOF);
				classOrInterfaceType();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case EOF:
			case RPAREN:
			case RBRACK:
			case COMMA:
			case QUESTION:
			case COLON:
			case EQUAL:
			case NOTEQUAL:
			case AND:
			case OR:
			case BITAND:
			case BITOR:
			case CARET:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			instanceofExpression_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_9);
			} else {
			  throw ex;
			}
		}
		returnAST = instanceofExpression_AST;
	}
	
	public final void relationalExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST relationalExpression_AST = null;
		
		try {      // for error handling
			shiftExpression();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop133:
			do {
				if ((_tokenSet_10.member(LA(1)))) {
					if ( inputState.guessing==0 ) {
						relationalExpression_AST = (AST)currentAST.root;
						relationalExpression_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(BINARY_EXPRESSION,"binary_expression")).add(relationalExpression_AST));
						currentAST.root = relationalExpression_AST;
						currentAST.child = relationalExpression_AST!=null &&relationalExpression_AST.getFirstChild()!=null ?
							relationalExpression_AST.getFirstChild() : relationalExpression_AST;
						currentAST.advanceChildToEnd();
					}
					{
					switch ( LA(1)) {
					case CMP_LE:
					{
						AST tmp13_AST = null;
						tmp13_AST = astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp13_AST);
						match(CMP_LE);
						break;
					}
					case CMP_GE:
					{
						AST tmp14_AST = null;
						tmp14_AST = astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp14_AST);
						match(CMP_GE);
						break;
					}
					case CMP_LT:
					{
						AST tmp15_AST = null;
						tmp15_AST = astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp15_AST);
						match(CMP_LT);
						break;
					}
					case CMP_GT:
					{
						AST tmp16_AST = null;
						tmp16_AST = astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp16_AST);
						match(CMP_GT);
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					shiftExpression();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop133;
				}
				
			} while (true);
			}
			relationalExpression_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_11);
			} else {
			  throw ex;
			}
		}
		returnAST = relationalExpression_AST;
	}
	
	public final void classOrInterfaceType() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST classOrInterfaceType_AST = null;
		
		try {      // for error handling
			AST tmp17_AST = null;
			tmp17_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp17_AST);
			match(Identifier);
			{
			switch ( LA(1)) {
			case DOT:
			{
				match(DOT);
				classOrInterfaceType();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case EOF:
			case RPAREN:
			case RBRACK:
			case COMMA:
			case QUESTION:
			case COLON:
			case EQUAL:
			case NOTEQUAL:
			case AND:
			case OR:
			case BITAND:
			case BITOR:
			case CARET:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				classOrInterfaceType_AST = (AST)currentAST.root;
				classOrInterfaceType_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(TYPE_NAME,"type_name")).add(classOrInterfaceType_AST));
				currentAST.root = classOrInterfaceType_AST;
				currentAST.child = classOrInterfaceType_AST!=null &&classOrInterfaceType_AST.getFirstChild()!=null ?
					classOrInterfaceType_AST.getFirstChild() : classOrInterfaceType_AST;
				currentAST.advanceChildToEnd();
			}
			classOrInterfaceType_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_9);
			} else {
			  throw ex;
			}
		}
		returnAST = classOrInterfaceType_AST;
	}
	
	public final void shiftExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST shiftExpression_AST = null;
		
		try {      // for error handling
			additiveExpression();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop137:
			do {
				if (((LA(1) >= SHIFT_LEFT && LA(1) <= SHIFT_RIGHT_U))) {
					if ( inputState.guessing==0 ) {
						shiftExpression_AST = (AST)currentAST.root;
						shiftExpression_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(BINARY_EXPRESSION,"binary_expression")).add(shiftExpression_AST));
						currentAST.root = shiftExpression_AST;
						currentAST.child = shiftExpression_AST!=null &&shiftExpression_AST.getFirstChild()!=null ?
							shiftExpression_AST.getFirstChild() : shiftExpression_AST;
						currentAST.advanceChildToEnd();
					}
					{
					switch ( LA(1)) {
					case SHIFT_LEFT:
					{
						AST tmp19_AST = null;
						tmp19_AST = astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp19_AST);
						match(SHIFT_LEFT);
						break;
					}
					case SHIFT_RIGHT_S:
					{
						AST tmp20_AST = null;
						tmp20_AST = astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp20_AST);
						match(SHIFT_RIGHT_S);
						break;
					}
					case SHIFT_RIGHT_U:
					{
						AST tmp21_AST = null;
						tmp21_AST = astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp21_AST);
						match(SHIFT_RIGHT_U);
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					additiveExpression();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop137;
				}
				
			} while (true);
			}
			shiftExpression_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_12);
			} else {
			  throw ex;
			}
		}
		returnAST = shiftExpression_AST;
	}
	
	public final void additiveExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST additiveExpression_AST = null;
		
		try {      // for error handling
			multiplicativeExpression();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop141:
			do {
				if ((LA(1)==ADD||LA(1)==SUB)) {
					if ( inputState.guessing==0 ) {
						additiveExpression_AST = (AST)currentAST.root;
						additiveExpression_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(BINARY_EXPRESSION,"binary_expression")).add(additiveExpression_AST));
						currentAST.root = additiveExpression_AST;
						currentAST.child = additiveExpression_AST!=null &&additiveExpression_AST.getFirstChild()!=null ?
							additiveExpression_AST.getFirstChild() : additiveExpression_AST;
						currentAST.advanceChildToEnd();
					}
					{
					switch ( LA(1)) {
					case ADD:
					{
						AST tmp22_AST = null;
						tmp22_AST = astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp22_AST);
						match(ADD);
						break;
					}
					case SUB:
					{
						AST tmp23_AST = null;
						tmp23_AST = astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp23_AST);
						match(SUB);
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					multiplicativeExpression();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop141;
				}
				
			} while (true);
			}
			additiveExpression_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_13);
			} else {
			  throw ex;
			}
		}
		returnAST = additiveExpression_AST;
	}
	
	public final void multiplicativeExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST multiplicativeExpression_AST = null;
		
		try {      // for error handling
			unaryExpression();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop145:
			do {
				if ((LA(1)==MUL||LA(1)==DIV||LA(1)==MOD)) {
					if ( inputState.guessing==0 ) {
						multiplicativeExpression_AST = (AST)currentAST.root;
						multiplicativeExpression_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(BINARY_EXPRESSION,"binary_expression")).add(multiplicativeExpression_AST));
						currentAST.root = multiplicativeExpression_AST;
						currentAST.child = multiplicativeExpression_AST!=null &&multiplicativeExpression_AST.getFirstChild()!=null ?
							multiplicativeExpression_AST.getFirstChild() : multiplicativeExpression_AST;
						currentAST.advanceChildToEnd();
					}
					{
					switch ( LA(1)) {
					case MUL:
					{
						AST tmp24_AST = null;
						tmp24_AST = astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp24_AST);
						match(MUL);
						break;
					}
					case DIV:
					{
						AST tmp25_AST = null;
						tmp25_AST = astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp25_AST);
						match(DIV);
						break;
					}
					case MOD:
					{
						AST tmp26_AST = null;
						tmp26_AST = astFactory.create(LT(1));
						astFactory.addASTChild(currentAST, tmp26_AST);
						match(MOD);
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					unaryExpression();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop145;
				}
				
			} while (true);
			}
			multiplicativeExpression_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_14);
			} else {
			  throw ex;
			}
		}
		returnAST = multiplicativeExpression_AST;
	}
	
	public final void unaryExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST unaryExpression_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case ADD:
			case SUB:
			{
				{
				switch ( LA(1)) {
				case ADD:
				{
					AST tmp27_AST = null;
					tmp27_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp27_AST);
					match(ADD);
					break;
				}
				case SUB:
				{
					AST tmp28_AST = null;
					tmp28_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp28_AST);
					match(SUB);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				unaryExpression();
				astFactory.addASTChild(currentAST, returnAST);
				if ( inputState.guessing==0 ) {
					unaryExpression_AST = (AST)currentAST.root;
					unaryExpression_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(UNARY_EXPRESSION,"unary_expression")).add(unaryExpression_AST));
					currentAST.root = unaryExpression_AST;
					currentAST.child = unaryExpression_AST!=null &&unaryExpression_AST.getFirstChild()!=null ?
						unaryExpression_AST.getFirstChild() : unaryExpression_AST;
					currentAST.advanceChildToEnd();
				}
				unaryExpression_AST = (AST)currentAST.root;
				break;
			}
			case JNULL:
			case TRUE:
			case FALSE:
			case HEX_NUMERIC_LITERAL:
			case OCT_NUMERIC_LITERAL:
			case FP_NUMERIC_LITERAL:
			case DEC_NUMERIC_LITERAL:
			case CharacterLiteral:
			case StringLiteral:
			case Identifier:
			case LPAREN:
			case BANG:
			case TILDE:
			{
				unaryExpressionNotPlusMinus();
				astFactory.addASTChild(currentAST, returnAST);
				unaryExpression_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_15);
			} else {
			  throw ex;
			}
		}
		returnAST = unaryExpression_AST;
	}
	
	public final void unaryExpressionNotPlusMinus() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST unaryExpressionNotPlusMinus_AST = null;
		
		try {      // for error handling
			if ((LA(1)==BANG||LA(1)==TILDE)) {
				{
				switch ( LA(1)) {
				case TILDE:
				{
					AST tmp29_AST = null;
					tmp29_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp29_AST);
					match(TILDE);
					break;
				}
				case BANG:
				{
					AST tmp30_AST = null;
					tmp30_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp30_AST);
					match(BANG);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				unaryExpression();
				astFactory.addASTChild(currentAST, returnAST);
				if ( inputState.guessing==0 ) {
					unaryExpressionNotPlusMinus_AST = (AST)currentAST.root;
					unaryExpressionNotPlusMinus_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(UNARY_EXPRESSION,"unary_expression")).add(unaryExpressionNotPlusMinus_AST));
					currentAST.root = unaryExpressionNotPlusMinus_AST;
					currentAST.child = unaryExpressionNotPlusMinus_AST!=null &&unaryExpressionNotPlusMinus_AST.getFirstChild()!=null ?
						unaryExpressionNotPlusMinus_AST.getFirstChild() : unaryExpressionNotPlusMinus_AST;
					currentAST.advanceChildToEnd();
				}
				unaryExpressionNotPlusMinus_AST = (AST)currentAST.root;
			}
			else {
				boolean synPredMatched151 = false;
				if (((LA(1)==LPAREN) && (LA(2)==Identifier))) {
					int _m151 = mark();
					synPredMatched151 = true;
					inputState.guessing++;
					try {
						{
						castExpression();
						}
					}
					catch (RecognitionException pe) {
						synPredMatched151 = false;
					}
					rewind(_m151);
inputState.guessing--;
				}
				if ( synPredMatched151 ) {
					castExpression();
					astFactory.addASTChild(currentAST, returnAST);
					unaryExpressionNotPlusMinus_AST = (AST)currentAST.root;
				}
				else if ((_tokenSet_16.member(LA(1))) && (_tokenSet_17.member(LA(2)))) {
					primary();
					astFactory.addASTChild(currentAST, returnAST);
					{
					_loop153:
					do {
						if ((LA(1)==DOT||LA(1)==LBRACK)) {
							if ( inputState.guessing==0 ) {
								unaryExpressionNotPlusMinus_AST = (AST)currentAST.root;
								unaryExpressionNotPlusMinus_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(PRIMARY_SELECTOR,"primary_selector")).add(unaryExpressionNotPlusMinus_AST));
								currentAST.root = unaryExpressionNotPlusMinus_AST;
								currentAST.child = unaryExpressionNotPlusMinus_AST!=null &&unaryExpressionNotPlusMinus_AST.getFirstChild()!=null ?
									unaryExpressionNotPlusMinus_AST.getFirstChild() : unaryExpressionNotPlusMinus_AST;
								currentAST.advanceChildToEnd();
							}
							selector();
							astFactory.addASTChild(currentAST, returnAST);
						}
						else {
							break _loop153;
						}
						
					} while (true);
					}
					unaryExpressionNotPlusMinus_AST = (AST)currentAST.root;
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			catch (RecognitionException ex) {
				if (inputState.guessing==0) {
					reportError(ex);
					recover(ex,_tokenSet_15);
				} else {
				  throw ex;
				}
			}
			returnAST = unaryExpressionNotPlusMinus_AST;
		}
		
	public final void castExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST castExpression_AST = null;
		
		try {      // for error handling
			match(LPAREN);
			classOrInterfaceType();
			astFactory.addASTChild(currentAST, returnAST);
			match(RPAREN);
			unaryExpressionNotPlusMinus();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				castExpression_AST = (AST)currentAST.root;
				castExpression_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(TYPE_CAST,"type_cast")).add(castExpression_AST));
				currentAST.root = castExpression_AST;
				currentAST.child = castExpression_AST!=null &&castExpression_AST.getFirstChild()!=null ?
					castExpression_AST.getFirstChild() : castExpression_AST;
				currentAST.advanceChildToEnd();
			}
			castExpression_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_15);
			} else {
			  throw ex;
			}
		}
		returnAST = castExpression_AST;
	}
	
	public final void primary() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST primary_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case LPAREN:
			{
				match(LPAREN);
				expression();
				astFactory.addASTChild(currentAST, returnAST);
				match(RPAREN);
				if ( inputState.guessing==0 ) {
					primary_AST = (AST)currentAST.root;
					primary_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(PARENTHESES_EXPRESSION,"parentheses_expression")).add(primary_AST));
					currentAST.root = primary_AST;
					currentAST.child = primary_AST!=null &&primary_AST.getFirstChild()!=null ?
						primary_AST.getFirstChild() : primary_AST;
					currentAST.advanceChildToEnd();
				}
				primary_AST = (AST)currentAST.root;
				break;
			}
			case Identifier:
			{
				AST tmp35_AST = null;
				tmp35_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp35_AST);
				match(Identifier);
				{
				switch ( LA(1)) {
				case LPAREN:
				{
					arguments();
					astFactory.addASTChild(currentAST, returnAST);
					if ( inputState.guessing==0 ) {
						primary_AST = (AST)currentAST.root;
						primary_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(METHOD_CALL,"method_call")).add(primary_AST));
						currentAST.root = primary_AST;
						currentAST.child = primary_AST!=null &&primary_AST.getFirstChild()!=null ?
							primary_AST.getFirstChild() : primary_AST;
						currentAST.advanceChildToEnd();
					}
					break;
				}
				case EOF:
				case INSTANCEOF:
				case DOT:
				case RPAREN:
				case LBRACK:
				case RBRACK:
				case COMMA:
				case CMP_GT:
				case CMP_LT:
				case QUESTION:
				case COLON:
				case EQUAL:
				case CMP_LE:
				case CMP_GE:
				case NOTEQUAL:
				case AND:
				case OR:
				case ADD:
				case SUB:
				case MUL:
				case DIV:
				case BITAND:
				case BITOR:
				case CARET:
				case MOD:
				case SHIFT_LEFT:
				case SHIFT_RIGHT_S:
				case SHIFT_RIGHT_U:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				primary_AST = (AST)currentAST.root;
				break;
			}
			case JNULL:
			case TRUE:
			case FALSE:
			case HEX_NUMERIC_LITERAL:
			case OCT_NUMERIC_LITERAL:
			case FP_NUMERIC_LITERAL:
			case DEC_NUMERIC_LITERAL:
			case CharacterLiteral:
			case StringLiteral:
			{
				literal();
				astFactory.addASTChild(currentAST, returnAST);
				primary_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_18);
			} else {
			  throw ex;
			}
		}
		returnAST = primary_AST;
	}
	
	public final void selector() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST selector_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case DOT:
			{
				match(DOT);
				AST tmp37_AST = null;
				tmp37_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp37_AST);
				match(Identifier);
				{
				switch ( LA(1)) {
				case LPAREN:
				{
					arguments();
					astFactory.addASTChild(currentAST, returnAST);
					if ( inputState.guessing==0 ) {
						selector_AST = (AST)currentAST.root;
						selector_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(METHOD_CALL,"method_call")).add(selector_AST));
						currentAST.root = selector_AST;
						currentAST.child = selector_AST!=null &&selector_AST.getFirstChild()!=null ?
							selector_AST.getFirstChild() : selector_AST;
						currentAST.advanceChildToEnd();
					}
					break;
				}
				case EOF:
				case INSTANCEOF:
				case DOT:
				case RPAREN:
				case LBRACK:
				case RBRACK:
				case COMMA:
				case CMP_GT:
				case CMP_LT:
				case QUESTION:
				case COLON:
				case EQUAL:
				case CMP_LE:
				case CMP_GE:
				case NOTEQUAL:
				case AND:
				case OR:
				case ADD:
				case SUB:
				case MUL:
				case DIV:
				case BITAND:
				case BITOR:
				case CARET:
				case MOD:
				case SHIFT_LEFT:
				case SHIFT_RIGHT_S:
				case SHIFT_RIGHT_U:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				if ( inputState.guessing==0 ) {
					selector_AST = (AST)currentAST.root;
					selector_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(DOT_SELECTOR,"dot_selector")).add(selector_AST));
					currentAST.root = selector_AST;
					currentAST.child = selector_AST!=null &&selector_AST.getFirstChild()!=null ?
						selector_AST.getFirstChild() : selector_AST;
					currentAST.advanceChildToEnd();
				}
				selector_AST = (AST)currentAST.root;
				break;
			}
			case LBRACK:
			{
				AST tmp38_AST = null;
				tmp38_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp38_AST);
				match(LBRACK);
				expression();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp39_AST = null;
				tmp39_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp39_AST);
				match(RBRACK);
				selector_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_18);
			} else {
			  throw ex;
			}
		}
		returnAST = selector_AST;
	}
	
	public final void arguments() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST arguments_AST = null;
		
		try {      // for error handling
			match(LPAREN);
			{
			switch ( LA(1)) {
			case JNULL:
			case TRUE:
			case FALSE:
			case HEX_NUMERIC_LITERAL:
			case OCT_NUMERIC_LITERAL:
			case FP_NUMERIC_LITERAL:
			case DEC_NUMERIC_LITERAL:
			case CharacterLiteral:
			case StringLiteral:
			case Identifier:
			case LPAREN:
			case BANG:
			case TILDE:
			case ADD:
			case SUB:
			{
				expressionList();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case RPAREN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(RPAREN);
			arguments_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_18);
			} else {
			  throw ex;
			}
		}
		returnAST = arguments_AST;
	}
	
	public final void literal() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST literal_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case HEX_NUMERIC_LITERAL:
			{
				AST tmp42_AST = null;
				tmp42_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp42_AST);
				match(HEX_NUMERIC_LITERAL);
				literal_AST = (AST)currentAST.root;
				break;
			}
			case OCT_NUMERIC_LITERAL:
			{
				AST tmp43_AST = null;
				tmp43_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp43_AST);
				match(OCT_NUMERIC_LITERAL);
				literal_AST = (AST)currentAST.root;
				break;
			}
			case FP_NUMERIC_LITERAL:
			{
				AST tmp44_AST = null;
				tmp44_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp44_AST);
				match(FP_NUMERIC_LITERAL);
				literal_AST = (AST)currentAST.root;
				break;
			}
			case DEC_NUMERIC_LITERAL:
			{
				AST tmp45_AST = null;
				tmp45_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp45_AST);
				match(DEC_NUMERIC_LITERAL);
				literal_AST = (AST)currentAST.root;
				break;
			}
			case CharacterLiteral:
			{
				AST tmp46_AST = null;
				tmp46_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp46_AST);
				match(CharacterLiteral);
				literal_AST = (AST)currentAST.root;
				break;
			}
			case StringLiteral:
			{
				AST tmp47_AST = null;
				tmp47_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp47_AST);
				match(StringLiteral);
				literal_AST = (AST)currentAST.root;
				break;
			}
			case TRUE:
			{
				AST tmp48_AST = null;
				tmp48_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp48_AST);
				match(TRUE);
				literal_AST = (AST)currentAST.root;
				break;
			}
			case FALSE:
			{
				AST tmp49_AST = null;
				tmp49_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp49_AST);
				match(FALSE);
				literal_AST = (AST)currentAST.root;
				break;
			}
			case JNULL:
			{
				AST tmp50_AST = null;
				tmp50_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp50_AST);
				match(JNULL);
				literal_AST = (AST)currentAST.root;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_18);
			} else {
			  throw ex;
			}
		}
		returnAST = literal_AST;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"STATEMENT",
		"BINARY_EXPRESSION",
		"INSTANCEOF_BINARY_EXPRESSION",
		"UNARY_EXPRESSION",
		"PARENTHESES_EXPRESSION",
		"TYPE_CAST",
		"TYPE_NAME",
		"PRIMARY_SELECTOR",
		"DOT_SELECTOR",
		"METHOD_CALL",
		"EXPRESSION_LIST",
		"\"null\"",
		"\"true\"",
		"\"false\"",
		"\"instanceof\"",
		"DOT",
		"HEX_NUMERIC_LITERAL",
		"OCT_NUMERIC_LITERAL",
		"FP_NUMERIC_LITERAL",
		"DEC_NUMERIC_LITERAL",
		"HexDigit",
		"DecDigit",
		"OctDigit",
		"NumericLiteral",
		"CharacterLiteral",
		"SingleCharacter",
		"StringLiteral",
		"StringCharacters",
		"StringCharacter",
		"EscapeSequence",
		"OctalEscape",
		"UnicodeEscape",
		"ZeroToThree",
		"Identifier",
		"LPAREN",
		"RPAREN",
		"LBRACE",
		"RBRACE",
		"LBRACK",
		"RBRACK",
		"SEMI",
		"COMMA",
		"ASSIGN",
		"CMP_GT",
		"CMP_LT",
		"BANG",
		"TILDE",
		"QUESTION",
		"COLON",
		"EQUAL",
		"CMP_LE",
		"CMP_GE",
		"NOTEQUAL",
		"AND",
		"OR",
		"ADD",
		"SUB",
		"MUL",
		"DIV",
		"BITAND",
		"BITOR",
		"CARET",
		"MOD",
		"SHIFT_LEFT",
		"SHIFT_RIGHT_S",
		"SHIFT_RIGHT_U",
		"WS",
		"COMMENT",
		"LINE_COMMENT"
	};
	
	protected void buildTokenTypeASTClassMap() {
		tokenTypeToASTClassMap=null;
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 2L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 4548129848295426L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	private static final long[] mk_tokenSet_2() {
		long[] data = { 549755813888L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	private static final long[] mk_tokenSet_3() {
		long[] data = { 6799929661980674L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
	private static final long[] mk_tokenSet_4() {
		long[] data = { 295030305813692418L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
	private static final long[] mk_tokenSet_5() {
		long[] data = { 439145493889548290L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
	private static final long[] mk_tokenSet_6() {
		long[] data = { 439145493889548290L, 1L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
	private static final long[] mk_tokenSet_7() {
		long[] data = { 439145493889548290L, 3L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
	private static final long[] mk_tokenSet_8() {
		long[] data = { -8784226542965227518L, 3L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
	private static final long[] mk_tokenSet_9() {
		long[] data = { -8703161749672558590L, 3L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_9 = new BitSet(mk_tokenSet_9());
	private static final long[] mk_tokenSet_10() {
		long[] data = { 54465407993511936L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_10 = new BitSet(mk_tokenSet_10());
	private static final long[] mk_tokenSet_11() {
		long[] data = { -8703161749672296446L, 3L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_11 = new BitSet(mk_tokenSet_11());
	private static final long[] mk_tokenSet_12() {
		long[] data = { -8648696341678784510L, 3L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_12 = new BitSet(mk_tokenSet_12());
	private static final long[] mk_tokenSet_13() {
		long[] data = { -8648696341678784510L, 59L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_13 = new BitSet(mk_tokenSet_13());
	private static final long[] mk_tokenSet_14() {
		long[] data = { -6919314084768514046L, 59L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_14 = new BitSet(mk_tokenSet_14());
	private static final long[] mk_tokenSet_15() {
		long[] data = { -1785057127432190L, 63L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_15 = new BitSet(mk_tokenSet_15());
	private static final long[] mk_tokenSet_16() {
		long[] data = { 413674995712L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_16 = new BitSet(mk_tokenSet_16());
	private static final long[] mk_tokenSet_17() {
		long[] data = { -91395545137150L, 63L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_17 = new BitSet(mk_tokenSet_17());
	private static final long[] mk_tokenSet_18() {
		long[] data = { -1780659080396798L, 63L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_18 = new BitSet(mk_tokenSet_18());
	
	}
