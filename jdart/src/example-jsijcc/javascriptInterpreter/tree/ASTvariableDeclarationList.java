/* Generated By:JJTree: Do not edit this line. ASTvariableDeclarationList.java Version 7.0 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package javascriptInterpreter.tree;

import javascriptInterpreter.parser.Javascript;

public
class ASTvariableDeclarationList extends SimpleNode {
  public ASTvariableDeclarationList(int id) {
    super(id);
  }

  public ASTvariableDeclarationList(Javascript p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public javascriptInterpreter.visitors.JavascriptType jjtAccept(JavascriptVisitor visitor, javascriptInterpreter.visitors.Context data) {

    return
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=a53e7c886f8c195ddf1e741114dfdab1 (do not edit this line) */
