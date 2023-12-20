/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * SableUtil, a clean room implementation of the Collection API.     *
 * Copyright (C) 1997, 1998 Raja Vallee-Rai (kor@sable.mcgill.ca).   *
 * All rights reserved.                                              *
 *                                                                   *
 * This work was done as a project of the Sable Research Group,      *
 * School of Computer Science, McGill University, Canada             *
 * (http://www.sable.mcgill.ca/).  It is understood that any         *
 * modification not identified as such is not covered by the         *
 * preceding statement.                                              *
 *                                                                   *
 * This work is free software; you can redistribute it and/or        *
 * modify it under the terms of the GNU Library General Public       *
 * License as published by the Free Software Foundation; either      *
 * version 2 of the License, or (at your option) any later version.  *
 *                                                                   *
 * This work is distributed in the hope that it will be useful,      *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of    *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU *
 * Library General Public License for more details.                  *
 *                                                                   *
 * You should have received a copy of the GNU Library General Public *
 * License along with this library; if not, write to the             *
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,      *
 * Boston, MA  02111-1307, USA.                                      *
 *                                                                   *
 * To submit a bug report, send a comment, or get the latest news on *
 * this project and other Sable Research Group projects, please      *
 * visit the web site: http://www.sable.mcgill.ca/                   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

/*
 Reference Version
 -----------------
 This is the latest official version on which this file is based.
 The reference version is: $SableUtilVersion: 1.11 $

 Change History
 --------------
 A) Notes:

 Please use the following template.  Most recent changes should
 appear at the top of the list.

 - Modified on [date (March 1, 1900)] by [name]. [(*) if appropriate]
   [description of modification].

 Any Modification flagged with "(*)" was done as a project of the
 Sable Research Group, School of Computer Science,
 McGill University, Canada (http://www.sable.mcgill.ca/).

 You should add your copyright, using the following template, at
 the top of this file, along with other copyrights.

 *                                                                   *
 * Modifications by [name] are                                       *
 * Copyright (C) [year(s)] [your name (or company)].  All rights     *
 * reserved.                                                         *
 *                                                                   *

 B) Changes:

 - Modified on July 21, 1998 by Raja Vallee-Rai (kor@sable.mcgill.ca). (*)
   Using HashMap.

 - Modified on June 15, 1998 by Raja Vallee-Rai (kor@sable.mcgill.ca). (*)
   First release of this file.

*/

package ca.mcgill.sable.util;

/**
 * Provides an implementation of the Set object using java.util.Hashtable.
 *
 * @author: Raja Vallee-Rai
 */

public class HashSet extends AbstractSet
{
    HashMap table;

    public HashSet()
    {
        table = new HashMap();
    }

      public HashSet(int cap)
    {
        table = new HashMap(cap);
    }

    public HashSet(int cap, float load)
    {
        table = new HashMap(cap, load);
    }

    /**
     * Create a set which contains the given elements.
     */

    public HashSet(Object[] elements)
    {
        table = new HashMap();

        for(int i = 0; i < elements.length; i++)
            add(elements[i]);
    }

    public boolean add(Object obj)
    {
        if(!table.containsKey(obj))
        {
            table.put(obj, obj);
            return true;
        }
        else
            return false;
    }

    public void clear()
    {
        table.clear();
    }

    public boolean contains(Object obj)
    {
        return table.containsKey(obj);
    }

    public boolean remove(Object obj)
    {
        if(!table.containsKey(obj))
        {
            return false;
        }
        else {
            table.remove(obj);
            return true;
        }
    }

    public int size()
    {
        return table.size();
    }

    public boolean isEmpty()
    {
        return table.isEmpty();
    }

    public Iterator iterator()
    {
        return table.values().iterator();
    }
}
