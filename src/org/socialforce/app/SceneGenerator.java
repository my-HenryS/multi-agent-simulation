package org.socialforce.app;

import org.socialforce.app.ParameterSet;
import org.socialforce.app.Scene;

/**
 * Created by Ledenel on 2016/8/24.
 */

/**
 * Generator a Scene
 */
public interface SceneGenerator {
    Iterable<Scene> generate(Scene template, ParameterSet params);
}
