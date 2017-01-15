package org.socialforce.strategy.impl;

import org.socialforce.scene.Scene;
import org.socialforce.geom.Point;
import org.socialforce.model.Agent;
import org.socialforce.strategy.Path;
import org.socialforce.strategy.PathFinder;
import org.socialforce.strategy.StaticStrategy;

import java.util.Iterator;

/**
 * Created by sunjh1999 on 2016/12/23.
 */
public class LifeBeltStrategy extends NearestGoalStrategy implements StaticStrategy{


    public LifeBeltStrategy(Scene scene, PathFinder pathFinder, Point... candidate_goals) {
        super(scene, pathFinder, candidate_goals);

    }

    @Override
    public void pathDecision() {
        Agent agent;
        for (Iterator iter = scene.getAllAgents().iterator(); iter.hasNext(); ) {
            agent = (Agent) iter.next();
            Path designed_path = null;
            double factor_t = Double.POSITIVE_INFINITY;
            pathFinder.applyAgent(agent);
            for (Point goal : goals) {
                int front_num = fronts(agent, goal);
                pathFinder.applyGoal(goal);
                //设置最优path
                Path path = pathFinder.plan_for();
                double pathLength = path.length();
                double t =  factorT(pathLength, agent, front_num, goal);
                if(t < factor_t){
                    factor_t = t;
                    designed_path = path;
                }
            }
            agent.setPath(designed_path);
        }
        pathFinder.postProcessing();
    }

    public int fronts(Agent agent, Point goal){
        int front_num = 0;
        for (Iterator iter = scene.getAllAgents().iterator(); iter.hasNext(); ) {
            Agent target_agent = (Agent) iter.next();
            if(agent.getShape().getDistance(goal) > target_agent.getShape().getDistance(goal)){
                front_num += 1;
            }
        }
        return front_num;
    }

    public double factorT(double pathLength, Agent agent, int front_num, Point goal){
        return pathLength / agent.getModel().getExpectedSpeed() + front_num / Width.widthOf(goal);
    }


}
