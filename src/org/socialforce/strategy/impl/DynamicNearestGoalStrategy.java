package org.socialforce.strategy.impl;

import org.socialforce.geom.Point;
import org.socialforce.model.Agent;
import org.socialforce.scene.Scene;
import org.socialforce.scene.SceneValue;
import org.socialforce.scene.impl.SVSR_SafetyRegion;
import org.socialforce.scene.impl.SVSR_Scenery;
import org.socialforce.strategy.DynamicStrategy;
import org.socialforce.strategy.Path;
import org.socialforce.strategy.PathFinder;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by sunjh1999 on 2017/1/21.
 */
public class DynamicNearestGoalStrategy implements DynamicStrategy {
    LinkedList<Point> goals = new LinkedList<>();
    LinkedList<Point> transits = new LinkedList<>();
    Scene scene;
    PathFinder pathFinder;

    public DynamicNearestGoalStrategy(Scene scene, PathFinder pathFinder){
        this.scene = scene;
        findGoals();
        this.pathFinder = pathFinder;
    }


    @Override
    public void pathDecision() {
        Agent agent;
        for (Iterator iter = scene.getAllAgents().iterator(); iter.hasNext(); ) {
            agent = (Agent) iter.next();
            if(agent.getPath() != null){
                if(agent.getPath().getGoal().equals(goals.get(0))){
                    continue;
                }
                if(agent.getPath().getGoal().distanceTo(agent.getShape().getReferencePoint()) < 0.3){
                    agent.setPath(pathFinder.plan_for(goals.get(0)));
                    continue;
                }
            }
            Path designed_path = null;
            double path_length = Double.POSITIVE_INFINITY;
            for (Point goal : transits) {
                //设置最优path
                Path path = pathFinder.plan_for(goal);
                double pathLength = path.length(agent.getShape().getReferencePoint());
                if (pathLength < path_length) {
                    path_length = pathLength;
                    designed_path = path;
                }
            }
            agent.setPath(designed_path);
        }
    }

    public void dynamicDecision(){
        pathDecision();
    }


    public void findGoals(){
        for(Iterator<SceneValue> iterator = scene.getValueSet().iterator(); iterator.hasNext();){
            SceneValue sceneValue = iterator.next();
            if(sceneValue instanceof SVSR_SafetyRegion){
                goals.addLast(((SVSR_SafetyRegion)sceneValue).getValue().getShape().getReferencePoint());
            }
            if(sceneValue instanceof SVSR_Scenery){
                transits.addLast(((SVSR_Scenery)sceneValue).getValue().getShape().getReferencePoint());
            }
        }
    }
}


