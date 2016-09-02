package org.socialforce.app;

/**
 * get the value of scene
 */
public interface SceneValue<T extends Comparable<T>> extends Comparable<SceneValue<T>> {
    String getName();
    void setName();
    T getValue();
    void apply(Scene scene);
}
