package org.socialforce.strategy.impl;

import org.socialforce.app.Scene;
import org.socialforce.geom.Point;
import org.socialforce.model.Agent;
import org.socialforce.strategy.Path;
import org.socialforce.strategy.PathFinder;

/**
 * Created by Whatever on 2016/10/22.
 */
public class StraightPathFinder implements PathFinder {
    Point goal;
    Agent agent;
    Scene scene;

    public StraightPathFinder(Scene targetScene, Agent agent, Point goal) {
        this.goal = goal;
        this.agent = agent;
        this.scene = targetScene;

    }

    public StraightPathFinder(){};

    /**
     * 从起点到目标点之间产生一条路径。
     * @return 搜索出的路径。
     */
    @Override
    public Path plan_for(){
        return new StraightPath(agent.getShape().getReferencePoint(),goal);
    }

    public void setParameters(Scene targetScene, Agent agent, Point goal){
        this.goal = goal;
        this.agent = agent;
        this.scene = targetScene;
    }
}
