package org.socialforce.strategy.impl;

import org.socialforce.app.Scene;
import org.socialforce.geom.Point;
import org.socialforce.model.Agent;
import org.socialforce.strategy.Path;
import org.socialforce.strategy.PathFinder;
import org.socialforce.strategy.StaticStrategy;

/**
 * Created by sunjh1999 on 2016/12/14.
 */
public class NearestGoalStrategy implements StaticStrategy{
    Point destination;
    Path designed_path;
    Point [] goals;
    Scene scene;
    Agent agent;
    PathFinder pathFinder;
    double path_length = Double.POSITIVE_INFINITY;

    public NearestGoalStrategy(Scene scene, Agent agent, PathFinder pathFinder, Point ... candidate_goals){
        this.goals = candidate_goals;
        this.scene = scene;
        this.agent = agent;
        pathFinder.setParameters(scene);
        this.pathFinder = pathFinder;
    }


    @Override
    public Point goal_decision() {
        for(int i = 0; i < goals.length; i++){
            if()
        }
    }
}
