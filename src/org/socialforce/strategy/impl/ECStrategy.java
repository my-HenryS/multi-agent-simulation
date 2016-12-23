package org.socialforce.strategy.impl;

import org.socialforce.app.Scene;
import org.socialforce.geom.Point;
import org.socialforce.model.Agent;
import org.socialforce.strategy.DynamicStrategy;
import org.socialforce.strategy.Path;
import org.socialforce.strategy.PathFinder;

import java.util.Iterator;

/**
 * Created by sunjh1999 on 2016/12/24.
 */
public class ECStrategy extends LifeBeltStrategy implements DynamicStrategy {

    public ECStrategy(Scene scene, PathFinder pathFinder, Point... candidate_goals) {
        super(scene, pathFinder, candidate_goals);
    }

    @Override
    public void dynamicDecision(){
        Agent agent;
        for (Iterator iter = scene.getAllAgents().iterator(); iter.hasNext(); ) {
            agent = (Agent) iter.next();
            Path designed_path = new AStarPath();
            double factor_t = Double.POSITIVE_INFINITY;
            pathFinder.applyAgent(agent);
            for (Point goal : goals) {
                int front_num = fronts(agent, goal);
                pathFinder.applyGoal(goal);
                //设置最优path
                Path path = pathFinder.plan_for();
                double pathLength = path.length();
                double velocity = agent.getVelocity().length();
                double t = pathLength / velocity + front_num / EC(Width.widthOf(goal), velocity);
                if(t < factor_t){
                    factor_t = t;
                    designed_path = path;
                }
            }
            agent.setPath(designed_path);
        }
    }

    @Override
    public double factorT(double pathLength, Agent agent, int front_num, Point goal){
        double velocity = agent.getModel().getExpectedSpeed();
        return pathLength / velocity + front_num / EC(Width.widthOf(goal), velocity);
    }

    public double EC(double width, double velocity){
        return (3.054+-0.44*Math.exp(-0.65*(velocity-3.57)))*(2.1*(width-0.386));
    }
}