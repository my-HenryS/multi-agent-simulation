package org.socialforce.app.impl;

import org.socialforce.app.DataListener;
import org.socialforce.app.DataProvider;
import org.socialforce.scene.Scene;
import org.socialforce.scene.SceneListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ledenel on 2017/1/29.
 */
public class SceneStepDataProvider implements DataProvider<Scene>, SceneListener {
    @Override
    public Scene provide() {
        return scene;
    }

    @Override
    public String getName() {
        return "Scene";
    }

    @Override
    public void addListener(DataListener<Scene> listener) {
        this.listeners.add(listener);
    }

    @Override
    public void removeListener(DataListener<Scene> listener) {
        this.listeners.remove(listener);
    }

    List<DataListener<Scene>> listeners = new ArrayList<>();

    /**
     * 场景全部加载完毕时触发的事件。
     *
     * @param scene 触发的场景。
     */
    @Override
    public boolean onAdded(Scene scene) {
        this.scene = scene;
        return true;
    }

    private Scene scene;

    /**
     * 场景进行一步时触发的事件。
     *
     * @param scene 触发的场景。
     */
    @Override
    public void onStep(Scene scene) {
        this.scene = scene;
        for(DataListener<Scene> l : listeners) {
            l.update(scene);
        }
    }
}
