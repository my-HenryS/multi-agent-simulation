package org.socialforce.strategy.impl;

import org.socialforce.app.Scene;
import org.socialforce.geom.Point;
import org.socialforce.geom.Shape;
import org.socialforce.model.Agent;
import org.socialforce.strategy.Path;
import org.socialforce.strategy.PathFinder;

/**
 * Created by Whatever on 2016/10/22.
 */
public class StraightPathFinder implements PathFinder {
    Point goal;
    Shape agentShape;
    Scene scene;

    public StraightPathFinder(Scene targetScene, Agent agent, Point goal) {
        this.goal = goal;
        this.agentShape = agent.getShape().clone();
        this.scene = targetScene;

    }

    public StraightPathFinder(Scene targetScene){
        this.scene = targetScene;
    }

    /**
     * 从起点到目标点之间产生一条路径。
     * @return 搜索出的路径。
     */
    @Override
    public Path plan_for(){
        return new StraightPath(agentShape.getReferencePoint(),goal);
    }

    public void applyGoal(Point goal){
        this.goal = goal.clone();
    }

    public void applyAgent(Agent agent){
        this.agentShape = agent.getShape().clone();
    }

    public void postProcessing(){}
}
