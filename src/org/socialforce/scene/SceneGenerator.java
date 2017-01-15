package org.socialforce.scene;

import java.util.Iterator;

/**
 * Generator a Scene
 */
public interface SceneGenerator {
    static Scene generate(Scene scene, ValueSet values) {
        for(Iterator<SceneValue> iterator = values.iterator(); iterator.hasNext();){
            SceneValue sceneValue = iterator.next();
            if (sceneValue == null){
                break;
            }
            else sceneValue.apply(scene);
        }
        return scene;
    }
}
