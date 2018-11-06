package org.socialforce.scene;

import org.socialforce.scene.impl.SimpleSceneParameter;

import java.util.LinkedList;

/**
 * the parameter of a scene
 */
public interface SceneParameter<T extends Comparable<T>> extends Comparable<SceneParameter<T>> {
    String getName();
    void setName(String name);
    boolean isValid(SceneValue<T> value);
    int getPreferedSize();
    Iterable<SceneValue<T>> sample(int size);
    Iterable<SceneValue<T>> sample();
    void addValue(SceneValue value);
    SceneValue removeValue();

    static SceneParameter genParameter(SceneValue... sceneValue){
        SceneParameter parameter;
        LinkedList<SceneValue> values = new LinkedList<>();
        for(SceneValue value : sceneValue){
            values.addLast(value);
        }
        parameter = new SimpleSceneParameter(values);
        return parameter;
    }
}
