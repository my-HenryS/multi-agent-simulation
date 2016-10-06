package org.socialforce.app.impl;

import org.socialforce.app.SceneValue;
import org.socialforce.app.ValueSet;

import java.util.Collection;
import java.util.LinkedList;
import java.util.TreeMap;

/**
 * Created by Ledenel on 2016/10/6.
 */
public class LinkListValueSet extends LinkedList<SceneValue> implements ValueSet {

    @Override
    public SceneValue findValueByName(String name) {
        return this.stream()
                .filter(value -> name.equals(value.getEntityName()))
                .findFirst()
                .orElse(null);
    }

    /**
     * Compares the specified object with this list for equality.  Returns
     * {@code true} if and only if the specified object is also a list, both
     * lists have the same size, and all corresponding pairs of elements in
     * the two lists are <i>equal</i>.  (Two elements {@code e1} and
     * {@code e2} are <i>equal</i> if {@code (e1==null ? e2==null :
     * e1.equals(e2))}.)  In other words, two lists are defined to be
     * equal if they contain the same elements in the same order.<p>
     * <p>
     * This implementation first checks if the specified object is this
     * list. If so, it returns {@code true}; if not, it checks if the
     * specified object is a list. If not, it returns {@code false}; if so,
     * it iterates over both lists, comparing corresponding pairs of elements.
     * If any comparison returns {@code false}, this method returns
     * {@code false}.  If either iterator runs out of elements before the
     * other it returns {@code false} (as the lists are of unequal length);
     * otherwise it returns {@code true} when the iterations complete.
     *
     * @param o the object to be compared for equality with this list
     * @return {@code true} if the specified object is equal to this list
     */
    @Override
    public boolean equals(Object o) {
        return o instanceof ValueSet
                && ((Collection) o).containsAll(this)
                && this.containsAll((Collection)o);
    }
}
