package org.socialforce.strategy.impl;

import org.socialforce.scene.Scene;
import org.socialforce.geom.Point;
import org.socialforce.model.Agent;
import org.socialforce.strategy.DynamicStrategy;
import org.socialforce.strategy.Path;
import org.socialforce.strategy.PathFinder;

import java.util.Iterator;

/**
 * Created by sunjh1999 on 2016/12/24.
 */
public class DynamicLifeBeltStrategy extends LifeBeltStrategy implements DynamicStrategy {
    public DynamicLifeBeltStrategy(Scene scene, PathFinder pathFinder, Point... candidate_goals) {
        super(scene, pathFinder, candidate_goals);
    }

    public void dynamicDecision(){
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
                double velocity = agent.getVelocity().length();
                double t = pathLength / velocity + front_num / Width.widthOf(goal);
                if(t < factor_t){
                    factor_t = t;
                    designed_path = path;
                }
            }
            agent.setPath(designed_path);
        }
    }
}
