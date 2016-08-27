package org.socialforce.app;

/**
 * the parameter of a scene
 */
public interface SceneParameter {
    boolean isValid(SceneValue value);
    int getSize();
    Iterable<SceneValue> sample(int size);
}
