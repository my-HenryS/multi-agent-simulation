package org.socialforce.app;

import org.socialforce.app.ParameterSet;
import org.socialforce.app.Scene;

/**
 * Generator a Scene
 */
public interface SceneGenerator {
    Iterable<Scene> generate(Scene template, ParameterSet params);
    Iterable<Scene> generate(SceneLoader loader);
}
