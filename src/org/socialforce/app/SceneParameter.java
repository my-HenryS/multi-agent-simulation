package org.socialforce.app;

/**
 * the parameter of a scene
 */
public interface SceneParameter<T> extends Comparable<SceneParameter<T>> {
    String getName();
    boolean isValid(SceneValue<T> value);
    int getPreferedSize();
    Iterable<SceneValue<T>> sample(int size);
    Iterable<SceneValue<T>> sample();
}
