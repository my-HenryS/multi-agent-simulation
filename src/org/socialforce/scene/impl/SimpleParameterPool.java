package org.socialforce.scene.impl;

import org.socialforce.model.InteractiveEntity;
import org.socialforce.scene.ParameterPool;
import org.socialforce.scene.SceneParameter;
import org.socialforce.scene.SceneValue;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by sunjh1999 on 2017/1/14.
 */
public class SimpleParameterPool implements ParameterPool {
    protected LinkedList<SceneParameter> parameters = new LinkedList<>();

    public int size() {
        return parameters.size();
    }

    public boolean isEmpty() {
        return parameters.isEmpty();
    }

    public boolean contains(SceneParameter value) {
        return parameters.contains(value);
    }

    public SimpleParameterPool addLast(SceneParameter value) {
        parameters.addLast(value);
        return this;
    }

    public SimpleParameterPool addValuesAsParameter(SceneValue ... values){
        parameters.add(SceneParameter.genParameter(values));
        return this;
    }

    public SceneParameter get(int index) {
        return parameters.get(index);
    }

    public SceneParameter remove(SceneParameter value) {
        if(parameters.remove(value)) return value;
        else return null;
    }

    public int indexOf(SceneParameter value){
        return parameters.indexOf(value);
    }

    public Iterator<SceneParameter> iterator(){
        return parameters.iterator();
    }


}
