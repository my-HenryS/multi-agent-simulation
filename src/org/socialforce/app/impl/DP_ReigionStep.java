package org.socialforce.app.impl;

import org.socialforce.app.DataListener;
import org.socialforce.container.AgentPool;
import org.socialforce.geom.Shape;
import org.socialforce.scene.Scene;
import org.socialforce.scene.impl.SimpleReigion;
import org.socialforce.scene.impl.SimpleScene;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/30.
 */
public class DP_ReigionStep extends DataProcessor<Scene,ArrayList<SimpleReigion>>{

    protected ArrayList<SimpleReigion> reigions;

    public DP_ReigionStep(DataListener[] outputs) {
        super(outputs);
    }


    @Override
    public ArrayList<SimpleReigion> provide() {
        reigions = ((SimpleScene)data).sample();
        return reigions;
    }
}
