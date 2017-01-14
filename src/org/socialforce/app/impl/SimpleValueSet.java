package org.socialforce.app.impl;

import org.socialforce.app.SceneValue;
import org.socialforce.app.ValueSet;

import java.util.*;

/**
 * Created by Whatever on 2016/12/2.
 * 老老实实用dictionary能死系列
 */
public class SimpleValueSet implements ValueSet {
    protected Set<SceneValue> values = new HashSet<SceneValue>();

    public int size() {
        return values.size();
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

    public boolean contains(SceneValue value) {
        return values.contains(value);
    }

    public SceneValue add(SceneValue value) {
        if(values.add(value)) return value;
        else return null;
    }

    public SceneValue remove(SceneValue value) {
        if(values.remove(value)) return value;
        else return null;
    }

    public Iterator<SceneValue> iterator(){
        return values.iterator();
    }
}
