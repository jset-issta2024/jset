/* Generated By:JJTree: Do not edit this line. ASTprimaryExpression.java Version 7.0 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package javascriptInterpreter.tree;

import javascriptInterpreter.parser.Javascript;

public
class ASTprimaryExpression extends SimpleNode {
  public ASTprimaryExpression(int id) {
    super(id);
  }

  public ASTprimaryExpression(Javascript p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public javascriptInterpreter.visitors.JavascriptType jjtAccept(JavascriptVisitor visitor, javascriptInterpreter.visitors.Context data) {

    return
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=9c71ac27e5578b735b3df9fece9c6f80 (do not edit this line) */
