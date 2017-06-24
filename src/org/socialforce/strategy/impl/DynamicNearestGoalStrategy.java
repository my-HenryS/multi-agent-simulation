package org.socialforce.strategy.impl;

import org.socialforce.geom.Point;
import org.socialforce.model.Agent;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.model.impl.Monitor;
import org.socialforce.model.impl.SafetyRegion;
import org.socialforce.scene.Scene;
import org.socialforce.scene.SceneValue;
import org.socialforce.scene.impl.SV_SafetyRegion;
import org.socialforce.scene.impl.SV_Monitor;
import org.socialforce.strategy.DynamicStrategy;
import org.socialforce.strategy.Path;
import org.socialforce.strategy.PathFinder;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by sunjh1999 on 2017/1/21.
 * MCM专用strategy 因为涉及到了门的嵌套 先保留这个硬编码的嵌套 待实现之后删除或更改
 */
public class DynamicNearestGoalStrategy implements DynamicStrategy {
    LinkedList<Point> goals = new LinkedList<>();
    LinkedList<Point> transits = new LinkedList<>();
    Scene scene;
    PathFinder pathFinder;

    public DynamicNearestGoalStrategy(Scene scene, PathFinder pathFinder){
        this.scene = scene;
        this.pathFinder = pathFinder;
        findGoals();
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
        for(Iterator<InteractiveEntity> iter = scene.getStaticEntities().selectClass(SafetyRegion.class).iterator(); iter.hasNext();){
            SafetyRegion safetyRegion = (SafetyRegion)iter.next();
            goals.addLast(safetyRegion.getShape().getReferencePoint());
        }
        for(Iterator<InteractiveEntity> iter = scene.getStaticEntities().selectClass(Monitor.class).iterator(); iter.hasNext();){
            Monitor monitor = (Monitor) iter.next();
            transits.addLast(monitor.getShape().getReferencePoint());
            pathFinder.addSituation(transits.getLast());
        }
    }
}


