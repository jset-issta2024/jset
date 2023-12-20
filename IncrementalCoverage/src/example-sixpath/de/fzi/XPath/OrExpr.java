/*========================================================================*
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License.
 *
 * You may obtain a copy of the License at http://www.mozilla.org/MPL/
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.
 * See the License for the specific language governing rights and
 * limitations under the License.
 *
 * The Original Code is all this file.
 *
 * The Initial Developer of the Original Code is
 * Aleksei Valikov, Forchungszentrum Informatik (valikov@fzi.de).
 *
 * Contributor(s): none.
 *========================================================================*/

package de.fzi.XPath;

/**
 * Class <code>OrExpr</code> represents a disjunction of other expressions.
 */
public class OrExpr extends NaryExpr
{
  /**
   * Adds expression to disjunction.
   * @param expr expression to be added.<BR>
   * @param operator operator.<BR>
   * If the expression is another disjunction
   * expression, the function does not add it as a subexpression. Instead,
   * its subexpressions are added.<BR>
   * Example<BR>
   * <code>a or (b or c)</code> will be <code>a or b or c</code>.
   */

  public void addExpr(Expr expr, int operator)
  {
    if (expr instanceof OrExpr)
    {
      OrExpr orexpr = (OrExpr) expr;
      Expr first = orexpr.getExpr(0);
      first.setOperator(operator);
      subs.addAll(orexpr.subs);
    }
    else
    {
      expr.setOperator(operator);
      subs.add(expr);
    }
  }
  /**
   * Adds expression to or-expression.
   * @param expr expression to be added.<BR>
   * Equivalent to <code>addExpr(expr, Expr.OR_OP);</code>
   */
  public void addExpr(Expr expr)
  {
    addExpr(expr, Expr.OR_OP);
  }
}

