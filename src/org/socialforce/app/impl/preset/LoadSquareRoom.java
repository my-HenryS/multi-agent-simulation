package org.socialforce.app.impl.preset;

import org.socialforce.app.ParameterSet;
import org.socialforce.app.Scene;
import org.socialforce.app.SceneLoader;
import org.socialforce.model.impl.SafetyRegion;
import org.socialforce.model.impl.Wall;

import java.io.File;
import java.io.InputStream;

/**
 * Created by Whatever on 2016/8/31.
 */
public class LoadSquareRoom implements SceneLoader {
    public Wall w1,w2,w3,w4,w5,w6,w7,w8;
    public SafetyRegion s1,s2,s3,s4;

    @Override
    public void setSource(InputStream stream) {
        //目前不从流中读取
    }

    @Override
    public void setSource(File file) {
        //目前不从文件中读取
    }

    @Override
    public Scene readScene() {
        return null;
    }

    @Override
    public ParameterSet readParameterSet() {
        return null;
    }
}
