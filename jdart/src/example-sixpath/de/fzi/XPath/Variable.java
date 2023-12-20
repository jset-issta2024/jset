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
 * This class represents a variable.
 */
public class Variable extends Expr
{

  String name;
  /**
   * Intantiates a variable with given name.
   * @param thename name of the variable.
   */

  public Variable(String thename)
  {
    name = thename;
  }

  /**
   * Returns string representation of this variable.
   * @return Returns string representation of this variable which is
   * <code>"$"</code> plus variable name, for example <code>"$foo"</code>.
   */

  public String toString()
  {
    return "$" + name;
  }

  /**
   * Returns the name of this variable.
   * @return Returns the name of this variable. The name does not include
   * <code>"$"</code> sign.
   */

  public String getName()
  {
    return name;
  }
}

