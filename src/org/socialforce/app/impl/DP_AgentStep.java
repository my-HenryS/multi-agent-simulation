package org.socialforce.app.impl;

import org.socialforce.app.DataListener;
import org.socialforce.app.DataProvider;
import org.socialforce.container.AgentPool;
import org.socialforce.model.Agent;
import org.socialforce.scene.Scene;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/30.
 */
public class DP_AgentStep extends DataProcessor<Scene,AgentPool> {
    protected AgentPool pool;

    public DP_AgentStep(DataListener<AgentPool>[] outputs) {
        super(outputs);
    }

    @Override
    public AgentPool provide() {
        pool = data.getAllAgents();
        return pool;
    }

}