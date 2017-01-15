package org.socialforce.app;

import org.socialforce.scene.ParameterPool;
import org.socialforce.scene.Scene;
import org.socialforce.scene.SceneLoader;

import java.io.File;
import java.lang.reflect.Parameter;

/**
 * Created by sunjh1999 on 2017/1/15.
 */
public interface Interpreter {
    void loadFile(File file);

    String getContent();

    SceneLoader setLoader();
}
