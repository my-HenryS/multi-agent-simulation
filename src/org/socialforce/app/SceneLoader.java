package org.socialforce.app;

import java.io.File;
import java.io.InputStream;

/**
 * Created by Ledenel on 2016/8/22.
 */
public interface SceneLoader {
    void setSource(InputStream stream);
    void setSource(File file);
    Scene readScene();
    ParameterSet readParameterSet();
}
