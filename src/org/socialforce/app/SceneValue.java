package org.socialforce.app;

/**
 * Created by Ledenel on 2016/8/24.
 */
public interface SceneValue extends Comparable<SceneValue> {
    String getName();
    Object getValue();
    void apply(Scene scene);
}
