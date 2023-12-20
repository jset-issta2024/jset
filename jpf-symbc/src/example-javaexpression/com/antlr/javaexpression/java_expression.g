//
// Copyright 2015 Google Inc. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

// ANTLR2 grammar for Java Cloud Debugger expression compiler.
// This grammar was inspired by this ANTLR3 grammar:
//   third_party/antlr3/java_grammar/Java.g


options {
  language="Java";
}

//
// LEXER
//

class JavaExpressionLexer extends Lexer;

options {
  k = 3;  // Set lookahead.
  charVocabulary = '\3'..'\377';
}

// Tokens:
tokens {
  // We need a few "imaginary" tokens to inject common AST nodes in different
  // places. This technique allows common treatment for all such places in
  // tree walking.
  STATEMENT;
  BINARY_EXPRESSION;
  INSTANCEOF_BINARY_EXPRESSION;
  UNARY_EXPRESSION;
  PARENTHESES_EXPRESSION;
  TYPE_CAST;
  TYPE_NAME;
  PRIMARY_SELECTOR;
  DOT_SELECTOR;
  METHOD_CALL;
  EXPRESSION_LIST;

  JNULL         = "null";
  TRUE          = "true";
  FALSE         = "false";
  INSTANCEOF     = "instanceof";

  // No explicit lexer rule exists for DOT. See NumericLiteral lexer rule for
  // details.
  DOT;

  // Numeric literals.
  HEX_NUMERIC_LITERAL;
  OCT_NUMERIC_LITERAL;
  FP_NUMERIC_LITERAL;
  DEC_NUMERIC_LITERAL;
}

protected HexDigit
  : '0'..'9' | 'a'..'f' | 'A'..'F'
  ;

protected DecDigit
  : '0'..'9'
  ;

protected OctDigit
  : '0'..'7'
  ;

NumericLiteral
  : ("0x") => "0x" (HexDigit)+  ('l' | 'L')?
      // hex: 0x12AB, 0x34CDL
      { $setType(HEX_NUMERIC_LITERAL); }
  | ('0' OctDigit) => '0' (OctDigit)+  ('l' | 'L')?
      // oct: 01234, 05670L
      { $setType(OCT_NUMERIC_LITERAL); }
  | ((DecDigit)*  '.' DecDigit) => (DecDigit)* '.' (DecDigit)+ ('d' | 'D' | 'f' | 'F')?
      // fp: 12.3, .4, 5.6f, .6d
      { $setType(FP_NUMERIC_LITERAL); }
  | ((DecDigit)+ ('d' | 'D' | 'f' | 'F')) => (DecDigit)+ ('d' | 'D' | 'f' | 'F')
      // fp: 12f, 34d
      { $setType(FP_NUMERIC_LITERAL); }
  | (DecDigit)+ ('l' | 'L')?
      // dec: 123, 456L
      { $setType(DEC_NUMERIC_LITERAL); }
  | '.'
      { $setType(DOT); }
  ;

CharacterLiteral
  : '\''! SingleCharacter '\''!
  | '\''! EscapeSequence '\''!
  ;

protected SingleCharacter
  : ~('\'' | '\\')
  ;

StringLiteral
  : '"'! (StringCharacters)? '"'!
  ;

protected StringCharacters
  : (StringCharacter)+
  ;

protected StringCharacter
  : ~('"' | '\\')
  | EscapeSequence
  ;

protected EscapeSequence
  : '\\' ('b' | 't' | 'n' | 'f' | 'r' | '"' | '\'' | '\\')
  | OctalEscape
  | UnicodeEscape
  ;

protected OctalEscape
  : ('\\' OctDigit) => '\\' OctDigit
  | ('\\' OctDigit OctDigit) => '\\' OctDigit OctDigit
  | '\\' ZeroToThree OctDigit OctDigit
  ;

protected UnicodeEscape
  : '\\' 'u' HexDigit HexDigit HexDigit HexDigit
  ;

protected ZeroToThree
  : ('0'..'3')
  ;


// Only ASCII characters are supported right now.
// TODO: add support for Unicode characters in Java identifiers.
Identifier
  : ( 'a'..'z' | 'A'..'Z' | '$' | '_' )
    ( 'a'..'z' | 'A'..'Z' | '0'..'9' | '$' | '_' )*
  ;

LPAREN          : "(";
RPAREN          : ")";
LBRACE          : "{";
RBRACE          : "}";
LBRACK          : "[";
RBRACK          : "]";
SEMI            : ";";
COMMA           : ",";
ASSIGN          : "=";
CMP_GT          : ">";
CMP_LT          : "<";
BANG            : "!";
TILDE           : "~";
QUESTION        : "?";
COLON           : ":";
EQUAL           : "==";
CMP_LE          : "<=";
CMP_GE          : ">=";
NOTEQUAL        : "!=";
AND             : "&&";
OR              : "||";
ADD             : "+";
SUB             : "-";
MUL             : "*";
DIV             : "/";
BITAND          : "&";
BITOR           : "|";
CARET           : "^";
MOD             : "%";
SHIFT_LEFT      : "<<";
SHIFT_RIGHT_S   : ">>";
SHIFT_RIGHT_U   : ">>>";


// Skip whitespaces and comments.

WS
  : ('\t' | '\r' | '\n' | ' ')+
    { $setType(Token.SKIP); }
  ;

COMMENT
  : "/*" ( { LA(2) != '/' }? '*' | ~('*') )* "*/"
    { $setType(Token.SKIP); }
  ;

LINE_COMMENT
  : ("//" (~('\r' | '\n'))*)
    { $setType(Token.SKIP); }
  ;


//
// PARSER
//

class JavaExpressionParser extends Parser;

options {
  k = 2;  // Set lookahead.
  buildAST = true;
  importVocab = JavaExpressionLexer;
}


statement
  : expression
    { #statement = #([STATEMENT, "statement"], #statement); }
    EOF!
  ;

expression
  : conditionalExpression
  ;

expressionList
  : expression
    (
      COMMA! expressionList
    )?
    { #expressionList = #([EXPRESSION_LIST, "expression_list"], #expressionList); }
  ;

conditionalExpression
  : conditionalOrExpression
    (QUESTION^ expression COLON conditionalExpression)?
  ;

conditionalOrExpression
  : conditionalAndExpression
    (
      { #conditionalOrExpression = #([BINARY_EXPRESSION, "binary_expression"], #conditionalOrExpression); }
      OR conditionalAndExpression
    )*
  ;

conditionalAndExpression
  : inclusiveOrExpression
    (
      { #conditionalAndExpression = #([BINARY_EXPRESSION, "binary_expression"], #conditionalAndExpression); }
      AND inclusiveOrExpression
    )*
  ;

inclusiveOrExpression
  : exclusiveOrExpression
    (
      { #inclusiveOrExpression = #([BINARY_EXPRESSION, "binary_expression"], #inclusiveOrExpression); }
      BITOR exclusiveOrExpression
    )*
  ;

exclusiveOrExpression
  : andExpression
    (
      { #exclusiveOrExpression = #([BINARY_EXPRESSION, "binary_expression"], #exclusiveOrExpression); }
      CARET andExpression
    )*
  ;

andExpression
  : equalityExpression
    (
      { #andExpression = #([BINARY_EXPRESSION, "binary_expression"], #andExpression); }
      BITAND equalityExpression
    )*
  ;

equalityExpression
  : instanceofExpression
    (
      { #equalityExpression = #([BINARY_EXPRESSION, "binary_expression"], #equalityExpression); }
      (EQUAL | NOTEQUAL) instanceofExpression
    )*
  ;

instanceofExpression
  : relationalExpression
    (
      { #instanceofExpression = #([INSTANCEOF_BINARY_EXPRESSION, "instanceof_binary_expression"], #instanceofExpression); }
      INSTANCEOF classOrInterfaceType
    )?
  ;

relationalExpression
  : shiftExpression
    (
      { #relationalExpression = #([BINARY_EXPRESSION, "binary_expression"], #relationalExpression); }
      (CMP_LE | CMP_GE | CMP_LT | CMP_GT) shiftExpression
    )*
  ;

shiftExpression
  : additiveExpression
    (
      { #shiftExpression = #([BINARY_EXPRESSION, "binary_expression"], #shiftExpression); }
      (SHIFT_LEFT | SHIFT_RIGHT_S | SHIFT_RIGHT_U) additiveExpression
    )*
  ;

additiveExpression
  : multiplicativeExpression
    (
      { #additiveExpression = #([BINARY_EXPRESSION, "binary_expression"], #additiveExpression); }
      (ADD | SUB) multiplicativeExpression
    )*
  ;

multiplicativeExpression
  : unaryExpression
    (
      { #multiplicativeExpression = #([BINARY_EXPRESSION, "binary_expression"], #multiplicativeExpression); }
      (MUL | DIV | MOD) unaryExpression
    )*
  ;

unaryExpression
  : (ADD | SUB) unaryExpression
    { #unaryExpression = #([UNARY_EXPRESSION, "unary_expression"], #unaryExpression); }
  | unaryExpressionNotPlusMinus
  ;

unaryExpressionNotPlusMinus
  : (TILDE | BANG) unaryExpression
    { #unaryExpressionNotPlusMinus = #([UNARY_EXPRESSION, "unary_expression"], #unaryExpressionNotPlusMinus); }
  | (castExpression) => castExpression
  | primary
    (
      { #unaryExpressionNotPlusMinus = #([PRIMARY_SELECTOR, "primary_selector"], #unaryExpressionNotPlusMinus); }
      selector
    )*
  ;

castExpression
  : LPAREN! classOrInterfaceType RPAREN! unaryExpressionNotPlusMinus
    { #castExpression = #([TYPE_CAST, "type_cast"], #castExpression); }
  ;

primary
  : LPAREN! expression RPAREN!
    { #primary = #([PARENTHESES_EXPRESSION, "parentheses_expression"], #primary); }
  | Identifier
    (
      arguments
      { #primary = #([METHOD_CALL, "method_call"], #primary); }
    )?
  | literal
  ;

arguments
  : LPAREN! (expressionList)? RPAREN!
  ;

selector
  : DOT!
    Identifier
    (
      arguments
      { #selector = #([METHOD_CALL, "method_call"], #selector); }
    )?
    { #selector = #([DOT_SELECTOR, "dot_selector"], #selector); }
  | LBRACK^ expression RBRACK
  ;

literal
  : HEX_NUMERIC_LITERAL
  | OCT_NUMERIC_LITERAL
  | FP_NUMERIC_LITERAL
  | DEC_NUMERIC_LITERAL
  | CharacterLiteral
  | StringLiteral
  | TRUE
  | FALSE
  | JNULL
  ;

// TODO: add support for generic types.
classOrInterfaceType
  : Identifier
    (
      DOT!
      classOrInterfaceType
    )?
    { #classOrInterfaceType = #([TYPE_NAME, "type_name"], #classOrInterfaceType); }
  ;



