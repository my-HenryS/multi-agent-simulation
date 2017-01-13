package org.socialforce.strategy.impl;

import org.socialforce.app.Scene;
import org.socialforce.geom.Point;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.model.Agent;
import org.socialforce.strategy.Path;
import org.socialforce.strategy.PathFinder;
import org.socialforce.strategy.StaticStrategy;

import java.util.Iterator;

/**
 * Created by sunjh1999 on 2016/12/14.
 */
public class NearestGoalStrategy implements StaticStrategy{
    Point [] goals;
    Scene scene;
    PathFinder pathFinder;

    public NearestGoalStrategy(Scene scene, PathFinder pathFinder, Point ... candidate_goals){
        this.goals = candidate_goals;
        this.scene = scene;
        this.pathFinder = pathFinder;
    }


    @Override
    public void pathDecision(){
        Agent agent;
        for (Iterator iter = scene.getAllAgents().iterator(); iter.hasNext(); ) {
            agent = (Agent) iter.next();
            Path designed_path = null;
            double path_length = Double.POSITIVE_INFINITY;
            pathFinder.applyAgent(agent);
            for (Point goal : goals) {
                pathFinder.applyGoal(goal);
                //设置最优path
                Path path = pathFinder.plan_for();
                double pathLength = path.length();
                if (pathLength < path_length) {
                    path_length = pathLength;
                    designed_path = path;
                }
            }
            agent.setPath(designed_path);
        }
        pathFinder.postProcessing();
    }
}
