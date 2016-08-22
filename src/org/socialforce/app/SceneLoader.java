package org.socialforce.app;

import java.io.File;
import java.io.InputStream;

/**
 * Created by Ledenel on 2016/8/22.
 */
public interface SceneLoader {
    Iterable<Scene> loadDefault();
    Iterable<Scene> loadFrom(InputStream stream);
    Iterable<Scene> loadFrom(File file);
}
