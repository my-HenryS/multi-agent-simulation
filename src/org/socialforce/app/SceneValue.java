package org.socialforce.app;

/**
 * Created by Ledenel on 2016/8/24.
 */

/**
 * get the value of scene
 */
public interface SceneValue extends Comparable<SceneValue> {
    String getName();
    Object getValue();
    void apply(Scene scene);
}
