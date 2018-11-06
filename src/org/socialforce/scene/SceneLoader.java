package org.socialforce.scene;

import org.socialforce.model.Model;

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
    LinkedList<Scene> readScene();
    ParameterPool readParameterSet(ParameterPool parameterSet);
    ParameterPool readValueSet(ValueSet valueSet);
    SceneLoader setModel(Model model);
}
