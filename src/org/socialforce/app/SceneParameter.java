package org.socialforce.app;

/**
 * Created by Ledenel on 2016/8/24.
 */

/**
 * the parameter of a scene
 */
public interface SceneParameter {
    boolean isValid(SceneValue value);
    int getSize();
    Iterable<SceneValue> sample(int size);
}
