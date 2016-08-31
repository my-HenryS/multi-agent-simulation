package org.socialforce.app;

import java.io.File;
import java.io.InputStream;

/**
 * 读取一个场景的静态部分
 * 如墙等
 * 场景的动态部分由一系列SceneValue决定
 *  @see Scene
 * @see  ParameterSet
 * @see SceneValue
 */
public interface SceneLoader {
    void setSource(InputStream stream);
    void setSource(File file);
    Scene readScene();
    ParameterSet readParameterSet();
}
