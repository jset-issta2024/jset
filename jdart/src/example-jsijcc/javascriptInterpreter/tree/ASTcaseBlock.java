/* Generated By:JJTree: Do not edit this line. ASTcaseBlock.java Version 7.0 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package javascriptInterpreter.tree;

import javascriptInterpreter.parser.Javascript;

public
class ASTcaseBlock extends SimpleNode {
  public ASTcaseBlock(int id) {
    super(id);
  }

  public ASTcaseBlock(Javascript p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public javascriptInterpreter.visitors.JavascriptType jjtAccept(JavascriptVisitor visitor, javascriptInterpreter.visitors.Context data) {

    return
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=88d2d90cd60967cd65a9b92cd5a9a894 (do not edit this line) */
