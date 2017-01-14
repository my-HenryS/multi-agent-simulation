package org.socialforce.app;

import java.io.File;
import java.io.InputStream;
import java.util.LinkedList;

/**
 * Loader a Scene
 *  @see Scene
 * @see  ParameterPool
 */
public interface SceneLoader {
    void setSource(InputStream stream);
    void setSource(File file);
    LinkedList<Scene> readScene(SocialForceApplication application);
    ParameterPool readParameterSet(ParameterPool parameterSet);
    ParameterPool readValueSet(ValueSet valueSet);
    Scene staticScene();
}
