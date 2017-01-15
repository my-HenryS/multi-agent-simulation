package org.socialforce.strategy.impl;

import org.socialforce.scene.Scene;
import org.socialforce.geom.Point;
import org.socialforce.model.Agent;
import org.socialforce.scene.SceneParameter;
import org.socialforce.scene.SceneValue;
import org.socialforce.scene.impl.SVSR_Exit;
import org.socialforce.scene.impl.SVSR_SafetyRegion;
import org.socialforce.scene.impl.SimpleSceneParameter;
import org.socialforce.strategy.Path;
import org.socialforce.strategy.PathFinder;
import org.socialforce.strategy.StaticStrategy;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by sunjh1999 on 2016/12/14.
 */
public class NearestGoalStrategy implements StaticStrategy{
    LinkedList<Point> goals = new LinkedList<>();
    Scene scene;
    PathFinder pathFinder;

    public NearestGoalStrategy(Scene scene, PathFinder pathFinder){
        this.scene = scene;
        findGoals();
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
    }

    private void findGoals(){
        for(Iterator<SceneValue> iterator = scene.getValueSet().iterator(); iterator.hasNext();){
            SceneValue sceneValue = iterator.next();
            if(sceneValue instanceof SVSR_SafetyRegion){
                goals.addLast(((SVSR_SafetyRegion)sceneValue).getValue().getShape().getReferencePoint());
            }
        }
    }
}
