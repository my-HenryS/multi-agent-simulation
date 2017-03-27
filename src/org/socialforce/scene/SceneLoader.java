package org.socialforce.scene;

import org.socialforce.app.SocialForceApplication;
import org.socialforce.scene.impl.SimpleSceneParameter;

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
    Scene staticScene();

    static SceneParameter genParameter(SceneValue... sceneValue){
        SceneParameter parameter;
        LinkedList<SceneValue> values = new LinkedList<>();
        for(SceneValue value : sceneValue){
            values.addLast(value);
        }
        parameter = new SimpleSceneParameter(values);
        return parameter;
    }
}
