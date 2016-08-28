package org.socialforce.app;

/**
 * get the value of scene
 */
public interface SceneValue<T> extends Comparable<SceneValue<T>> {
    String getName();
    T getValue();
    void apply(Scene scene);
}
