package org.socialforce.app.impl;

import org.socialforce.app.ParameterPool;
import org.socialforce.app.SceneParameter;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by sunjh1999 on 2017/1/14.
 */
public class SimpleParameterPool implements ParameterPool {
    protected LinkedList<SceneParameter> values = new LinkedList<>();

    public int size() {
        return values.size();
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

    public boolean contains(SceneParameter value) {
        return values.contains(value);
    }

    public SceneParameter addLast(SceneParameter value) {
        values.addLast(value);
        return value;
    }

    public SceneParameter get(int index) {
        return values.get(index);
    }

    public SceneParameter remove(SceneParameter value) {
        if(values.remove(value)) return value;
        else return null;
    }

    public int indexOf(SceneParameter value){
        return values.indexOf(value);
    }

    public Iterator<SceneParameter> iterator(){
        return values.iterator();
    }


}
