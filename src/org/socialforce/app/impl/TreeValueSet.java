package org.socialforce.app.impl;

import org.socialforce.app.SceneValue;
import org.socialforce.app.ValueSet;

import java.util.TreeMap;

/**
 * Created by Ledenel on 2016/10/6.
 */
public class TreeValueSet extends TreeMap<String, SceneValue> implements ValueSet {
    /**
     * Compares the specified object with this map for equality.  Returns
     * <tt>true</tt> if the given object is also a map and the two maps
     * represent the same mappings.  More formally, two maps <tt>m1</tt> and
     * <tt>m2</tt> represent the same mappings if
     * <tt>m1.entrySet().equals(m2.entrySet())</tt>.  This ensures that the
     * <tt>equals</tt> method works properly across different implementations
     * of the <tt>Map</tt> interface.
     *
     * @param o object to be compared for equality with this map
     * @return <tt>true</tt> if the specified object is equal to this map
     * @implSpec This implementation first checks if the specified object is this map;
     * if so it returns <tt>true</tt>.  Then, it checks if the specified
     * object is a map whose size is identical to the size of this map; if
     * not, it returns <tt>false</tt>.  If so, it iterates over this map's
     * <tt>entrySet</tt> collection, and checks that the specified map
     * contains each mapping that this map contains.  If the specified map
     * fails to contain such a mapping, <tt>false</tt> is returned.  If the
     * iteration completes, <tt>true</tt> is returned.
     */
    @Override
    public boolean equals(Object o) {
        return o instanceof ValueSet
                && ((ValueSet) o).entrySet().containsAll(this.entrySet())
                && this.entrySet().containsAll(((ValueSet) o).entrySet());
    }
}
