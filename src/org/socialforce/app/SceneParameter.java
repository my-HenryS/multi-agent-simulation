package org.socialforce.app;

/**
 * the parameter of a scene
 */
public interface SceneParameter extends Comparable<SceneParameter> {
    String getName();
    boolean isValid(SceneValue value);
    int getPreferedSize();
    Iterable<SceneValue> sample(int size);
    Iterable<SceneValue> sample();
}
