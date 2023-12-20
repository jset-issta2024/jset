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
 * A Class class.
 * <P>
 * @author Aleksei Valikov
 */
public class LocationPath extends Expr
{
  private boolean relative;
  private java.util.ArrayList steps;

  public LocationPath()
  {
    relative = true;
    steps = new java.util.ArrayList();
  }

  public LocationPath(boolean absolute)
  {
    relative = !absolute;
    steps = new java.util.ArrayList();
  }

  public void addStep(Step step)
  {
    steps.add(step);
  }

  public void addLocationPath(LocationPath lp)
  {
    if (lp.isAbsolute())
    {
      relative = false;
      steps.clear();
      steps.addAll(lp.steps);
    }
    else
    {
      steps.addAll(lp.steps);
    }
  }

  public int getStepsCount()
  {
    return steps.size();
  }

  public Step getStep(int index)
  {
    return (Step)steps.get(index);
  }

  public boolean isRelative()
  {
    return relative;
  }

  public boolean isAbsolute()
  {
    return !relative;
  }

  public void setRelative(boolean therelative)
  {
    relative = therelative; 
  }


  public String toString()
  {
    String res = super.toString();
    if (isAbsolute()) res = res + "/";
    for (int i=0; i < getStepsCount(); i++)
    {
      res = res + getStep(i);
      if (i < getStepsCount() - 1) res = res + "/";
    }
    return res;
  }
}

