package org.socialforce.app.impl;

import org.socialforce.app.DataListener;
import org.socialforce.scene.impl.SimpleReigion;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/30.
 */
public class DP_ReigionVelocity extends DataProcessor<ArrayList<SimpleReigion>,String> {
    public DP_ReigionVelocity(DataListener<String>[] outputs) {
        super(outputs);
    }

    @Override
    public String provide() {
        return null;
    }
}
