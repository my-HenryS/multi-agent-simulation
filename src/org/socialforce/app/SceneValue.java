package org.socialforce.app;

/**
 * get the value of scene
 */
public interface SceneValue extends Comparable<SceneValue> {
    String getName();
    Object getValue();
    void apply(Scene scene);
}
