package org.socialforce.strategy.impl;

import org.socialforce.model.impl.BaseAgent;
import org.socialforce.model.impl.SimpleSocialForceModel;
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
public class ECStrategy implements DynamicStrategy {
    Point[] goals;
    Scene scene;
    PathFinder pathFinder;

    public ECStrategy(Scene scene, PathFinder pathFinder) {
        this.scene = scene;
        this.pathFinder = pathFinder;
        this.goals = pathFinder.getGoals();
    }

    @Override
    public void pathDecision(){
        Agent agent;
        int goalchanges = 0;
        for (Iterator iter = scene.getAllAgents().iterator(); iter.hasNext(); ) {
            agent = (Agent) iter.next();
            Path designed_path = null;
            double factor_t = Double.POSITIVE_INFINITY;
            for (Point goal : goals) {
                int front_num = fronts(agent, goal);
                //设置最优path
                Path path = pathFinder.plan_for(goal);
                double pathLength = path.length(agent.getShape().getReferencePoint());
                double velocity = ((SimpleSocialForceModel)agent.getModel()).getExpectedSpeed();
                double t = pathLength / velocity + front_num / EC(Width.widthOf(goal), ((SimpleSocialForceModel)agent.getModel()).getExpectedSpeed());
                if(t < factor_t){
                    factor_t = t;
                    designed_path = path;
                }
            }
            if(agent.getPath()!=null){
                if(!agent.getPath().getGoal().equals(designed_path.getGoal())) goalchanges += 1;
            }
            agent.setPath(designed_path);
        }
        //System.out.println(goalchanges);
    }

    @Override
    public void dynamicDecision(){
        pathDecision();
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

    public static double EC(double width, double velocity){
        double a0 = 8.515, b1 = 0.4852, k0 = 0.3712, b0 = 0.1291;
        double ec=a0*(1-Math.exp(-k0*velocity+b0))*(width-b1);
        return ec<0? 0.01:ec;
    }

    public static void main(String[] args) {
        double []widths = new double[]{0.75,1,1.5,1.75};
        double dV = 3, sigmaEC = 0;
        for(double width:widths){
            sigmaEC += ECStrategy.EC(width,dV);
        }
        System.out.println(sigmaEC);
    }
}
