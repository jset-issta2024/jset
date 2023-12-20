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
 * <code>OrderedExpr</code> is a <code>NaryExpr</code> in which the order of
 * operands is significant. Thus, <code>a * (b div c)</code> is not equivalent
 * to <code>a * b div c</code> but <code>a | (b | c)</code> is equivalent
 * to <code>a | b | c</code>. First expression will be
 * <code>OrderedExpr</code>, last - <code>NaryExpr</code>. 
 */
public class OrderedExpr extends NaryExpr
{
  /**
   * Adds expression.
   * @param expr expression to be added.<BR>
   * @param operator operator that precedes expression being added.
   */

  public void addExpr(Expr expr, int operator)
  {
      expr.setOperator(operator);
      subs.add(expr);
  }
}

 