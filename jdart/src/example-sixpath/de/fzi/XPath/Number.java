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
 * Number class.
 */
public class Number extends Expr
{
  double value;

  /**
   * Intantiates a number with given string value.
   * @param thevalue string value of the number. This value is converted to
   * <code>double</code>.
   */
  public Number(String thevalue)
  {
    value = Double.parseDouble(thevalue);
  }

  /**
   * Intantiates a number with given value.
   * @param thevalue string value of the number.
   */
  public Number(double thevalue)
  {
    value = thevalue;
  }

  /**
   * Returns number's value.
   * @return Return's number's value.
   */
  public double getValue()
  {
    return value;
  }

  /**
   * Returns number as string.
   * @return Return number as string.
   */
  public String toString()
  {
    return Double.toString(value);
  }

}

 