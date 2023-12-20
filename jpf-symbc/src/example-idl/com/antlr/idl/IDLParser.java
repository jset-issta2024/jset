package com.antlr.idl;// $ANTLR 2.7.7 (20060906): "idl.g" -> "IDLParser.java"$
 
  import java.io.*;
  import java.util.Vector;
  import java.util.Hashtable;
 
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

/**
 *  This is a complete parser for the IDL language as defined
 *  by the CORBA 2.0 specification.  It will allow those who
 *  need an IDL parser to get up-and-running very quickly.
 *  Though IDL's syntax is very similar to C++, it is also
 *  much simpler, due in large part to the fact that it is
 *  a declarative-only language.
 *  
 *  Some things that are not included are: Symbol table construction
 *  (it is not necessary for parsing, btw) and preprocessing (for
 *  IDL compiler #pragma directives). You can use just about any
 *  C or C++ preprocessor, but there is an interesting semantic
 *  issue if you are going to generate code: In C, #include is
 *  a literal include, in IDL, #include is more like Java's import:
 *  It adds definitions to the scope of the parse, but included
 *  definitions are not generated.
 *
 *  Jim Coker, jcoker@magelang.com
 */
public class IDLParser extends LLkParser       implements IDLTokenTypes
 {

protected IDLParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
}

public IDLParser(TokenBuffer tokenBuf) {
  this(tokenBuf,1);
}

protected IDLParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
}

public IDLParser(TokenStream lexer) {
  this(lexer,1);
}

public IDLParser(ParserSharedInputState state) {
  super(state,1);
  tokenNames = _tokenNames;
}

	public final void specification() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			{
			int _cnt3=0;
			_loop3:
			do {
				if ((_tokenSet_0.member(LA(1)))) {
					definition();
				}
				else {
					if ( _cnt3>=1 ) { break _loop3; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt3++;
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_1);
		}
	}
	
	public final void definition() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case SEMI:
			case LITERAL_typedef:
			case LITERAL_native:
			case LITERAL_struct:
			case LITERAL_union:
			case LITERAL_enum:
			{
				type_dcl();
				match(SEMI);
				break;
			}
			case LITERAL_const:
			{
				const_dcl();
				match(SEMI);
				break;
			}
			case LITERAL_exception:
			{
				except_dcl();
				match(SEMI);
				break;
			}
			case LITERAL_interface:
			{
				interf();
				match(SEMI);
				break;
			}
			case LITERAL_module:
			{
				module();
				match(SEMI);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_2);
		}
	}
	
	public final void type_dcl() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_typedef:
			{
				match(LITERAL_typedef);
				type_declarator();
				break;
			}
			case LITERAL_struct:
			{
				struct_type();
				break;
			}
			case LITERAL_union:
			{
				union_type();
				break;
			}
			case LITERAL_enum:
			{
				enum_type();
				break;
			}
			case SEMI:
			{
				break;
			}
			case LITERAL_native:
			{
				match(LITERAL_native);
				simple_declarator();
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_3);
		}
	}
	
	public final void const_dcl() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(LITERAL_const);
			const_type();
			identifier();
			match(ASSIGN);
			const_exp();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_3);
		}
	}
	
	public final void except_dcl() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(LITERAL_exception);
			identifier();
			match(LCURLY);
			opt_member_list();
			match(RCURLY);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_3);
		}
	}
	
	public final void interf() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(LITERAL_interface);
			identifier();
			inheritance_spec();
			{
			switch ( LA(1)) {
			case LCURLY:
			{
				interface_body();
				break;
			}
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_3);
		}
	}
	
	public final void module() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(LITERAL_module);
			identifier();
			match(LCURLY);
			definition_list();
			match(RCURLY);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_3);
		}
	}
	
	public final void identifier() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(IDENT);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_4);
		}
	}
	
	public final void definition_list() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			{
			int _cnt9=0;
			_loop9:
			do {
				if ((_tokenSet_0.member(LA(1)))) {
					definition();
				}
				else {
					if ( _cnt9>=1 ) { break _loop9; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt9++;
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_5);
		}
	}
	
	public final void inheritance_spec() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case COLON:
			{
				match(COLON);
				scoped_name_list();
				break;
			}
			case SEMI:
			case LCURLY:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_6);
		}
	}
	
	public final void interface_body() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(LCURLY);
			{
			_loop14:
			do {
				if ((_tokenSet_7.member(LA(1)))) {
					export();
				}
				else {
					break _loop14;
				}
				
			} while (true);
			}
			match(RCURLY);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_3);
		}
	}
	
	public final void export() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case SEMI:
			case LITERAL_typedef:
			case LITERAL_native:
			case LITERAL_struct:
			case LITERAL_union:
			case LITERAL_enum:
			{
				type_dcl();
				match(SEMI);
				break;
			}
			case LITERAL_const:
			{
				const_dcl();
				match(SEMI);
				break;
			}
			case LITERAL_exception:
			{
				except_dcl();
				match(SEMI);
				break;
			}
			case LITERAL_readonly:
			case LITERAL_attribute:
			{
				attr_dcl();
				match(SEMI);
				break;
			}
			case SCOPEOP:
			case LITERAL_octet:
			case LITERAL_any:
			case LITERAL_unsigned:
			case LITERAL_short:
			case LITERAL_long:
			case LITERAL_char:
			case LITERAL_float:
			case LITERAL_double:
			case LITERAL_boolean:
			case LITERAL_string:
			case LITERAL_oneway:
			case LITERAL_void:
			case IDENT:
			{
				op_dcl();
				match(SEMI);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_8);
		}
	}
	
	public final void attr_dcl() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case LITERAL_readonly:
			{
				match(LITERAL_readonly);
				break;
			}
			case LITERAL_attribute:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(LITERAL_attribute);
			param_type_spec();
			simple_declarator_list();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_3);
		}
	}
	
	public final void op_dcl() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			op_attribute();
			op_type_spec();
			identifier();
			parameter_dcls();
			opt_raises_expr();
			opt_context_expr();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_3);
		}
	}
	
	public final void scoped_name_list() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			scoped_name();
			{
			_loop20:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					scoped_name();
				}
				else {
					break _loop20;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_9);
		}
	}
	
	public final void scoped_name() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			opt_scope_op();
			identifier();
			{
			_loop23:
			do {
				if ((LA(1)==SCOPEOP)) {
					match(SCOPEOP);
					identifier();
				}
				else {
					break _loop23;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_10);
		}
	}
	
	public final void opt_scope_op() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case SCOPEOP:
			{
				match(SCOPEOP);
				break;
			}
			case IDENT:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_11);
		}
	}
	
	public final void const_type() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_unsigned:
			case LITERAL_short:
			case LITERAL_long:
			{
				integer_type();
				break;
			}
			case LITERAL_char:
			{
				char_type();
				break;
			}
			case LITERAL_boolean:
			{
				boolean_type();
				break;
			}
			case LITERAL_float:
			case LITERAL_double:
			{
				floating_pt_type();
				break;
			}
			case LITERAL_string:
			{
				string_type();
				break;
			}
			case SCOPEOP:
			case IDENT:
			{
				scoped_name();
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_11);
		}
	}
	
	public final void const_exp() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			or_expr();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_12);
		}
	}
	
	public final void integer_type() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case LITERAL_unsigned:
			{
				match(LITERAL_unsigned);
				break;
			}
			case LITERAL_short:
			case LITERAL_long:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case LITERAL_short:
			{
				match(LITERAL_short);
				break;
			}
			case LITERAL_long:
			{
				match(LITERAL_long);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_13);
		}
	}
	
	public final void char_type() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(LITERAL_char);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_13);
		}
	}
	
	public final void boolean_type() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(LITERAL_boolean);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_13);
		}
	}
	
	public final void floating_pt_type() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_float:
			{
				match(LITERAL_float);
				break;
			}
			case LITERAL_double:
			{
				match(LITERAL_double);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_14);
		}
	}
	
	public final void string_type() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(LITERAL_string);
			opt_pos_int_br();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_14);
		}
	}
	
	public final void or_expr() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			xor_expr();
			{
			_loop30:
			do {
				if ((LA(1)==OR)) {
					or_op();
					xor_expr();
				}
				else {
					break _loop30;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_12);
		}
	}
	
	public final void xor_expr() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			and_expr();
			{
			_loop34:
			do {
				if ((LA(1)==XOR)) {
					xor_op();
					and_expr();
				}
				else {
					break _loop34;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_15);
		}
	}
	
	public final void or_op() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(OR);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_16);
		}
	}
	
	public final void and_expr() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			shift_expr();
			{
			_loop38:
			do {
				if ((LA(1)==AND)) {
					and_op();
					shift_expr();
				}
				else {
					break _loop38;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_17);
		}
	}
	
	public final void xor_op() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(XOR);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_16);
		}
	}
	
	public final void shift_expr() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			add_expr();
			{
			_loop42:
			do {
				if ((LA(1)==LSHIFT||LA(1)==RSHIFT)) {
					shift_op();
					add_expr();
				}
				else {
					break _loop42;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_18);
		}
	}
	
	public final void and_op() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(AND);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_16);
		}
	}
	
	public final void add_expr() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			mult_expr();
			{
			_loop46:
			do {
				if ((LA(1)==PLUS||LA(1)==MINUS)) {
					add_op();
					mult_expr();
				}
				else {
					break _loop46;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_19);
		}
	}
	
	public final void shift_op() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case LSHIFT:
			{
				match(LSHIFT);
				break;
			}
			case RSHIFT:
			{
				match(RSHIFT);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_16);
		}
	}
	
	public final void mult_expr() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			unary_expr();
			{
			_loop50:
			do {
				if (((LA(1) >= STAR && LA(1) <= MOD))) {
					mult_op();
					unary_expr();
				}
				else {
					break _loop50;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_20);
		}
	}
	
	public final void add_op() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case PLUS:
			{
				match(PLUS);
				break;
			}
			case MINUS:
			{
				match(MINUS);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_16);
		}
	}
	
	public final void unary_expr() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case PLUS:
			case MINUS:
			case TILDE:
			{
				unary_operator();
				primary_expr();
				break;
			}
			case SCOPEOP:
			case LPAREN:
			case LITERAL_TRUE:
			case LITERAL_FALSE:
			case INT:
			case OCTAL:
			case HEX:
			case STRING_LITERAL:
			case CHAR_LITERAL:
			case FLOAT:
			case IDENT:
			{
				primary_expr();
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_21);
		}
	}
	
	public final void mult_op() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case STAR:
			{
				match(STAR);
				break;
			}
			case DIV:
			{
				match(DIV);
				break;
			}
			case MOD:
			{
				match(MOD);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_16);
		}
	}
	
	public final void unary_operator() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case MINUS:
			{
				match(MINUS);
				break;
			}
			case PLUS:
			{
				match(PLUS);
				break;
			}
			case TILDE:
			{
				match(TILDE);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_22);
		}
	}
	
	public final void primary_expr() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case SCOPEOP:
			case IDENT:
			{
				scoped_name();
				break;
			}
			case LITERAL_TRUE:
			case LITERAL_FALSE:
			case INT:
			case OCTAL:
			case HEX:
			case STRING_LITERAL:
			case CHAR_LITERAL:
			case FLOAT:
			{
				literal();
				break;
			}
			case LPAREN:
			{
				match(LPAREN);
				const_exp();
				match(RPAREN);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_21);
		}
	}
	
	public final void literal() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case INT:
			case OCTAL:
			case HEX:
			{
				integer_literal();
				break;
			}
			case STRING_LITERAL:
			{
				string_literal();
				break;
			}
			case CHAR_LITERAL:
			{
				character_literal();
				break;
			}
			case FLOAT:
			{
				floating_pt_literal();
				break;
			}
			case LITERAL_TRUE:
			case LITERAL_FALSE:
			{
				boolean_literal();
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_21);
		}
	}
	
	public final void integer_literal() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case INT:
			{
				match(INT);
				break;
			}
			case OCTAL:
			{
				match(OCTAL);
				break;
			}
			case HEX:
			{
				match(HEX);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_21);
		}
	}
	
	public final void string_literal() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			{
			int _cnt140=0;
			_loop140:
			do {
				if ((LA(1)==STRING_LITERAL)) {
					match(STRING_LITERAL);
				}
				else {
					if ( _cnt140>=1 ) { break _loop140; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt140++;
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_23);
		}
	}
	
	public final void character_literal() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(CHAR_LITERAL);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_21);
		}
	}
	
	public final void floating_pt_literal() throws RecognitionException, TokenStreamException {
		
		Token  f = null;
		
		try {      // for error handling
			f = LT(1);
			match(FLOAT);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_21);
		}
	}
	
	public final void boolean_literal() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_TRUE:
			{
				match(LITERAL_TRUE);
				break;
			}
			case LITERAL_FALSE:
			{
				match(LITERAL_FALSE);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_21);
		}
	}
	
	public final void positive_int_const() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			const_exp();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_24);
		}
	}
	
	public final void type_declarator() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			type_spec();
			declarators();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_3);
		}
	}
	
	public final void struct_type() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(LITERAL_struct);
			identifier();
			match(LCURLY);
			member_list();
			match(RCURLY);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_25);
		}
	}
	
	public final void union_type() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(LITERAL_union);
			identifier();
			match(LITERAL_switch);
			match(LPAREN);
			switch_type_spec();
			match(RPAREN);
			match(LCURLY);
			switch_body();
			match(RCURLY);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_25);
		}
	}
	
	public final void enum_type() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(LITERAL_enum);
			identifier();
			match(LCURLY);
			enumerator_list();
			match(RCURLY);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_26);
		}
	}
	
	public final void simple_declarator() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			identifier();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_27);
		}
	}
	
	public final void type_spec() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case SCOPEOP:
			case LITERAL_octet:
			case LITERAL_any:
			case LITERAL_unsigned:
			case LITERAL_short:
			case LITERAL_long:
			case LITERAL_char:
			case LITERAL_float:
			case LITERAL_double:
			case LITERAL_boolean:
			case LITERAL_sequence:
			case LITERAL_string:
			case IDENT:
			{
				simple_type_spec();
				break;
			}
			case LITERAL_struct:
			case LITERAL_union:
			case LITERAL_enum:
			{
				constr_type_spec();
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_11);
		}
	}
	
	public final void declarators() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			declarator();
			{
			_loop73:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					declarator();
				}
				else {
					break _loop73;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_3);
		}
	}
	
	public final void simple_type_spec() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_octet:
			case LITERAL_any:
			case LITERAL_unsigned:
			case LITERAL_short:
			case LITERAL_long:
			case LITERAL_char:
			case LITERAL_float:
			case LITERAL_double:
			case LITERAL_boolean:
			{
				base_type_spec();
				break;
			}
			case LITERAL_sequence:
			case LITERAL_string:
			{
				template_type_spec();
				break;
			}
			case SCOPEOP:
			case IDENT:
			{
				scoped_name();
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_14);
		}
	}
	
	public final void constr_type_spec() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_struct:
			{
				struct_type();
				break;
			}
			case LITERAL_union:
			{
				union_type();
				break;
			}
			case LITERAL_enum:
			{
				enum_type();
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_11);
		}
	}
	
	public final void base_type_spec() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_unsigned:
			case LITERAL_short:
			case LITERAL_long:
			{
				integer_type();
				break;
			}
			case LITERAL_char:
			{
				char_type();
				break;
			}
			case LITERAL_boolean:
			{
				boolean_type();
				break;
			}
			case LITERAL_float:
			case LITERAL_double:
			{
				floating_pt_type();
				break;
			}
			case LITERAL_octet:
			{
				match(LITERAL_octet);
				break;
			}
			case LITERAL_any:
			{
				match(LITERAL_any);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_14);
		}
	}
	
	public final void template_type_spec() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_sequence:
			{
				sequence_type();
				break;
			}
			case LITERAL_string:
			{
				string_type();
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_14);
		}
	}
	
	public final void sequence_type() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(LITERAL_sequence);
			match(LT);
			simple_type_spec();
			opt_pos_int();
			match(GT);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_14);
		}
	}
	
	public final void declarator() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			identifier();
			opt_fixed_array_size();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_28);
		}
	}
	
	public final void opt_fixed_array_size() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			{
			_loop77:
			do {
				if ((LA(1)==LBRACK)) {
					fixed_array_size();
				}
				else {
					break _loop77;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_28);
		}
	}
	
	public final void fixed_array_size() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(LBRACK);
			positive_int_const();
			match(RBRACK);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_29);
		}
	}
	
	public final void member_list() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			{
			int _cnt82=0;
			_loop82:
			do {
				if ((_tokenSet_30.member(LA(1)))) {
					member();
				}
				else {
					if ( _cnt82>=1 ) { break _loop82; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt82++;
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_5);
		}
	}
	
	public final void member() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			type_spec();
			declarators();
			match(SEMI);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_31);
		}
	}
	
	public final void switch_type_spec() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_unsigned:
			case LITERAL_short:
			case LITERAL_long:
			{
				integer_type();
				break;
			}
			case LITERAL_char:
			{
				char_type();
				break;
			}
			case LITERAL_boolean:
			{
				boolean_type();
				break;
			}
			case LITERAL_enum:
			{
				enum_type();
				break;
			}
			case SCOPEOP:
			case IDENT:
			{
				scoped_name();
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_32);
		}
	}
	
	public final void switch_body() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			case_stmt_list();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_5);
		}
	}
	
	public final void case_stmt_list() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			{
			int _cnt89=0;
			_loop89:
			do {
				if ((LA(1)==LITERAL_case||LA(1)==LITERAL_default)) {
					case_stmt();
				}
				else {
					if ( _cnt89>=1 ) { break _loop89; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt89++;
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_5);
		}
	}
	
	public final void case_stmt() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			case_label_list();
			element_spec();
			match(SEMI);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_33);
		}
	}
	
	public final void case_label_list() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			{
			int _cnt93=0;
			_loop93:
			do {
				if ((LA(1)==LITERAL_case||LA(1)==LITERAL_default)) {
					case_label();
				}
				else {
					if ( _cnt93>=1 ) { break _loop93; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt93++;
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_30);
		}
	}
	
	public final void element_spec() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			type_spec();
			declarator();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_3);
		}
	}
	
	public final void case_label() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_case:
			{
				match(LITERAL_case);
				const_exp();
				match(COLON);
				break;
			}
			case LITERAL_default:
			{
				match(LITERAL_default);
				match(COLON);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_34);
		}
	}
	
	public final void enumerator_list() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			enumerator();
			{
			_loop99:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					enumerator();
				}
				else {
					break _loop99;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_5);
		}
	}
	
	public final void enumerator() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			identifier();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_35);
		}
	}
	
	public final void opt_pos_int() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case COMMA:
			{
				match(COMMA);
				positive_int_const();
				break;
			}
			case GT:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_36);
		}
	}
	
	public final void opt_pos_int_br() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case LT:
			{
				match(LT);
				positive_int_const();
				match(GT);
				break;
			}
			case COMMA:
			case GT:
			case IDENT:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_14);
		}
	}
	
	public final void param_type_spec() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_octet:
			case LITERAL_any:
			case LITERAL_unsigned:
			case LITERAL_short:
			case LITERAL_long:
			case LITERAL_char:
			case LITERAL_float:
			case LITERAL_double:
			case LITERAL_boolean:
			{
				base_type_spec();
				break;
			}
			case LITERAL_string:
			{
				string_type();
				break;
			}
			case SCOPEOP:
			case IDENT:
			{
				scoped_name();
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_11);
		}
	}
	
	public final void simple_declarator_list() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			simple_declarator();
			{
			_loop112:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					simple_declarator();
				}
				else {
					break _loop112;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_3);
		}
	}
	
	public final void opt_member_list() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			{
			_loop116:
			do {
				if ((_tokenSet_30.member(LA(1)))) {
					member();
				}
				else {
					break _loop116;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_5);
		}
	}
	
	public final void op_attribute() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_oneway:
			{
				match(LITERAL_oneway);
				break;
			}
			case SCOPEOP:
			case LITERAL_octet:
			case LITERAL_any:
			case LITERAL_unsigned:
			case LITERAL_short:
			case LITERAL_long:
			case LITERAL_char:
			case LITERAL_float:
			case LITERAL_double:
			case LITERAL_boolean:
			case LITERAL_string:
			case LITERAL_void:
			case IDENT:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_37);
		}
	}
	
	public final void op_type_spec() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case SCOPEOP:
			case LITERAL_octet:
			case LITERAL_any:
			case LITERAL_unsigned:
			case LITERAL_short:
			case LITERAL_long:
			case LITERAL_char:
			case LITERAL_float:
			case LITERAL_double:
			case LITERAL_boolean:
			case LITERAL_string:
			case IDENT:
			{
				param_type_spec();
				break;
			}
			case LITERAL_void:
			{
				match(LITERAL_void);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_11);
		}
	}
	
	public final void parameter_dcls() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(LPAREN);
			{
			switch ( LA(1)) {
			case LITERAL_in:
			case LITERAL_out:
			case LITERAL_inout:
			{
				param_dcl_list();
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
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_38);
		}
	}
	
	public final void opt_raises_expr() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case LITERAL_raises:
			{
				raises_expr();
				break;
			}
			case SEMI:
			case LITERAL_context:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_39);
		}
	}
	
	public final void opt_context_expr() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case LITERAL_context:
			{
				context_expr();
				break;
			}
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_3);
		}
	}
	
	public final void raises_expr() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(LITERAL_raises);
			match(LPAREN);
			scoped_name_list();
			match(RPAREN);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_39);
		}
	}
	
	public final void context_expr() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(LITERAL_context);
			match(LPAREN);
			string_literal_list();
			match(RPAREN);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_3);
		}
	}
	
	public final void param_dcl_list() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			param_dcl();
			{
			_loop128:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					param_dcl();
				}
				else {
					break _loop128;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_32);
		}
	}
	
	public final void param_dcl() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			param_attribute();
			param_type_spec();
			simple_declarator();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_40);
		}
	}
	
	public final void param_attribute() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_in:
			{
				match(LITERAL_in);
				break;
			}
			case LITERAL_out:
			{
				match(LITERAL_out);
				break;
			}
			case LITERAL_inout:
			{
				match(LITERAL_inout);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_41);
		}
	}
	
	public final void string_literal_list() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			string_literal();
			{
			_loop135:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					string_literal();
				}
				else {
					break _loop135;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_32);
		}
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		";",
		"\"module\"",
		"{",
		"}",
		"\"interface\"",
		":",
		",",
		"::",
		"\"const\"",
		"=",
		"|",
		"^",
		"&",
		"<<",
		">>",
		"+",
		"-",
		"*",
		"/",
		"%",
		"~",
		"(",
		")",
		"\"TRUE\"",
		"\"FALSE\"",
		"\"typedef\"",
		"\"native\"",
		"\"octet\"",
		"\"any\"",
		"\"unsigned\"",
		"\"short\"",
		"\"long\"",
		"\"char\"",
		"\"float\"",
		"\"double\"",
		"\"boolean\"",
		"\"struct\"",
		"\"union\"",
		"\"switch\"",
		"\"case\"",
		"\"default\"",
		"\"enum\"",
		"\"sequence\"",
		"<",
		">",
		"\"string\"",
		"[",
		"]",
		"\"readonly\"",
		"\"attribute\"",
		"\"exception\"",
		"\"oneway\"",
		"\"void\"",
		"\"in\"",
		"\"out\"",
		"\"inout\"",
		"\"raises\"",
		"\"context\"",
		"an integer value",
		"OCTAL",
		"a hexadecimal value value",
		"a string literal",
		"a character literal",
		"an floating point value",
		"an identifer",
		"?",
		".",
		"!",
		"white space",
		"a preprocessor directive",
		"a comment",
		"a comment",
		"an escape sequence",
		"an escaped character value",
		"a digit",
		"an octal digit",
		"a hexadecimal digit"
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 18052883027071280L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 2L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	private static final long[] mk_tokenSet_2() {
		long[] data = { 18052883027071410L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	private static final long[] mk_tokenSet_3() {
		long[] data = { 16L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
	private static final long[] mk_tokenSet_4() {
		long[] data = { 3663572861185744L, 16L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
	private static final long[] mk_tokenSet_5() {
		long[] data = { 128L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
	private static final long[] mk_tokenSet_6() {
		long[] data = { 80L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
	private static final long[] mk_tokenSet_7() {
		long[] data = { 140214120283641872L, 16L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
	private static final long[] mk_tokenSet_8() {
		long[] data = { 140214120283642000L, 16L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
	private static final long[] mk_tokenSet_9() {
		long[] data = { 67108944L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_9 = new BitSet(mk_tokenSet_9());
	private static final long[] mk_tokenSet_10() {
		long[] data = { 2533274874267216L, 16L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_10 = new BitSet(mk_tokenSet_10());
	private static final long[] mk_tokenSet_11() {
		long[] data = { 0L, 16L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_11 = new BitSet(mk_tokenSet_11());
	private static final long[] mk_tokenSet_12() {
		long[] data = { 2533274857505296L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_12 = new BitSet(mk_tokenSet_12());
	private static final long[] mk_tokenSet_13() {
		long[] data = { 281475043820544L, 16L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_13 = new BitSet(mk_tokenSet_13());
	private static final long[] mk_tokenSet_14() {
		long[] data = { 281474976711680L, 16L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_14 = new BitSet(mk_tokenSet_14());
	private static final long[] mk_tokenSet_15() {
		long[] data = { 2533274857521680L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_15 = new BitSet(mk_tokenSet_15());
	private static final long[] mk_tokenSet_16() {
		long[] data = { -4611686017972828160L, 31L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_16 = new BitSet(mk_tokenSet_16());
	private static final long[] mk_tokenSet_17() {
		long[] data = { 2533274857554448L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_17 = new BitSet(mk_tokenSet_17());
	private static final long[] mk_tokenSet_18() {
		long[] data = { 2533274857619984L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_18 = new BitSet(mk_tokenSet_18());
	private static final long[] mk_tokenSet_19() {
		long[] data = { 2533274858013200L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_19 = new BitSet(mk_tokenSet_19());
	private static final long[] mk_tokenSet_20() {
		long[] data = { 2533274859586064L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_20 = new BitSet(mk_tokenSet_20());
	private static final long[] mk_tokenSet_21() {
		long[] data = { 2533274874266128L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_21 = new BitSet(mk_tokenSet_21());
	private static final long[] mk_tokenSet_22() {
		long[] data = { -4611686017991178240L, 31L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_22 = new BitSet(mk_tokenSet_22());
	private static final long[] mk_tokenSet_23() {
		long[] data = { 2533274874267152L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_23 = new BitSet(mk_tokenSet_23());
	private static final long[] mk_tokenSet_24() {
		long[] data = { 2533274790395904L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_24 = new BitSet(mk_tokenSet_24());
	private static final long[] mk_tokenSet_25() {
		long[] data = { 16L, 16L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_25 = new BitSet(mk_tokenSet_25());
	private static final long[] mk_tokenSet_26() {
		long[] data = { 67108880L, 16L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_26 = new BitSet(mk_tokenSet_26());
	private static final long[] mk_tokenSet_27() {
		long[] data = { 67109904L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_27 = new BitSet(mk_tokenSet_27());
	private static final long[] mk_tokenSet_28() {
		long[] data = { 1040L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_28 = new BitSet(mk_tokenSet_28());
	private static final long[] mk_tokenSet_29() {
		long[] data = { 1125899906843664L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_29 = new BitSet(mk_tokenSet_29());
	private static final long[] mk_tokenSet_30() {
		long[] data = { 672898968717312L, 16L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_30 = new BitSet(mk_tokenSet_30());
	private static final long[] mk_tokenSet_31() {
		long[] data = { 672898968717440L, 16L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_31 = new BitSet(mk_tokenSet_31());
	private static final long[] mk_tokenSet_32() {
		long[] data = { 67108864L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_32 = new BitSet(mk_tokenSet_32());
	private static final long[] mk_tokenSet_33() {
		long[] data = { 26388279066752L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_33 = new BitSet(mk_tokenSet_33());
	private static final long[] mk_tokenSet_34() {
		long[] data = { 699287247783936L, 16L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_34 = new BitSet(mk_tokenSet_34());
	private static final long[] mk_tokenSet_35() {
		long[] data = { 1152L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_35 = new BitSet(mk_tokenSet_35());
	private static final long[] mk_tokenSet_36() {
		long[] data = { 281474976710656L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_36 = new BitSet(mk_tokenSet_36());
	private static final long[] mk_tokenSet_37() {
		long[] data = { 72621641355495424L, 16L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_37 = new BitSet(mk_tokenSet_37());
	private static final long[] mk_tokenSet_38() {
		long[] data = { 3458764513820540944L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_38 = new BitSet(mk_tokenSet_38());
	private static final long[] mk_tokenSet_39() {
		long[] data = { 2305843009213693968L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_39 = new BitSet(mk_tokenSet_39());
	private static final long[] mk_tokenSet_40() {
		long[] data = { 67109888L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_40 = new BitSet(mk_tokenSet_40());
	private static final long[] mk_tokenSet_41() {
		long[] data = { 564047317567488L, 16L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_41 = new BitSet(mk_tokenSet_41());
	
	}
