/* Generated By:JJTree: Do not edit this line. ASTblock.java Version 7.0 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package javascriptInterpreter.tree;

import javascriptInterpreter.parser.Javascript;

public
class ASTblock extends SimpleNode {
  public ASTblock(int id) {
    super(id);
  }

  public ASTblock(Javascript p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public javascriptInterpreter.visitors.JavascriptType jjtAccept(JavascriptVisitor visitor, javascriptInterpreter.visitors.Context data) {

    return
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=c3aac5966ff0a354392c98054408d359 (do not edit this line) */
