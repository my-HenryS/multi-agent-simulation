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
public class SceneStepDumper implements DataProvider<String>, DataListener<Scene> {
    @Override
    public void update(Scene data) {
        this.data = data;
        for(DataListener<String> l : listeners) {
            l.update(provide());
        }
    }

    Scene data = null;

    @Override
    public String provide() {
        return data == null ? null : data.toString();
    }

    @Override
    public String getName() {
        return "Scene Dumper";
    }

    List<DataListener<String>> listeners = new ArrayList<>();



    @Override
    public void addListener(DataListener<String> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(DataListener<String> listener) {
        listeners.remove(listener);
    }
}
