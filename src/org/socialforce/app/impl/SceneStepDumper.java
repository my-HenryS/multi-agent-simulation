package org.socialforce.app.impl;

import org.socialforce.app.DataListener;
import org.socialforce.app.DataProvider;
import org.socialforce.scene.Scene;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ledenel on 2017/1/29.
 */
public class SceneStepDumper extends DataProcessor<Scene,String> {
    public SceneStepDumper(DataListener<String>... outputs) {
        super(outputs);
    }

    @Override
    public String provide() {
        return data == null ? null : data.toString();
    }
}
