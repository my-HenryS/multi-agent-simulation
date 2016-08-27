package org.socialforce.app;

import java.io.File;
import java.io.InputStream;

/**
 * Loader a Scene
 *  @see Scene
 * @see  ParameterSet
 */
public interface SceneLoader {
    void setSource(InputStream stream);
    void setSource(File file);
    Scene readScene();
    ParameterSet readParameterSet();
}
