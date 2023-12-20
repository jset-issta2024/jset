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
 * <code>FunctionCall</code> class represents function call.
 */
public class FunctionCall extends Expr
{

  private String name;
  private java.util.ArrayList arguments;

  /**
   * Constructs <code>FunctionCall</code> for given name.
   * @param thename name of the function.
   */
  public FunctionCall(String thename)
  {
    name = thename;
    arguments = new java.util.ArrayList(1);
  }

  /**
   * Appends argument.
   * @param argument argument expression.
   */
  public void addArgument(Expr argument)
  {
    arguments.add(argument);
  }

  /**
   * Returns argument at given position.
   * @param index postion of argument to return.
   * @return Returns argument at position <code>index</code>. If no argument
   * at this position, result is <code>null</code>. 
   */
  public Expr getArgument(int index)
  {
    if (index >=0 && index < arguments.size())
      return (Expr) arguments.get(index);
    else return null;
  }

  /**
   * Returns argument count
   * @return Returns argument count.
   */
  public int getArgumentCount()
  {
    return arguments.size();
  }

  /**
   * Returns function call as string.
   * @return Returns function call as string.
   */
  public String toString()
  {
    String res = super.toString();
    res = res + name + "(";
    for (int i=0; i < getArgumentCount(); i++)
    {
      res = res + getArgument(i);
      if (i < getArgumentCount() - 1) res = res + ", ";
    }
    res = res + ")";
    return res;
  }

}

 