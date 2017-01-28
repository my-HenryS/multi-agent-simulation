package org.socialforce.model.impl;

import org.socialforce.container.impl.LinkListAgentPool;
import org.socialforce.geom.Shape;
import org.socialforce.geom.impl.Vector2D;
import org.socialforce.model.Agent;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.scene.Scene;

import java.util.Iterator;

/**
 * Created by sunjh1999 on 2017/1/21.
 */
public class Scenery extends Entity {
    double volume = 0, active_time = 0, time_step = 0;
    LinkListAgentPool agents = new LinkListAgentPool();
    public Scenery(Shape shape) {
        super(shape);
    }

    @Override
    public void affect(InteractiveEntity affectedEntity) {
        if (affectedEntity instanceof Agent) {
            if(active_time==0){
                active_time = ((Agent)affectedEntity).getCurrentSteps();
                time_step = ((Agent)affectedEntity).getModel().getTimePerStep();
            }
            if(!agents.contains(affectedEntity)){
                agents.addLast((Agent)affectedEntity);
                volume += 1;
            }
        }
    }

    @Override
    public double getMass() {
        return 0;
    }

    @Override
    public InteractiveEntity standardclone() {
        return new Scenery(shape.clone());
    }

    public double speak(int current_time){
       return volume/((current_time-active_time) * time_step);
    }

    public double[] say(){
        double [] speeds = new double [2];
        for(Iterator<Agent> iter = scene.getAllAgents().iterator(); iter.hasNext();){
            Agent agent = iter.next();
            if(agent.getShape().distanceTo(shape) < 6 && agent.getShape().directionTo(shape).dot(new Vector2D(0,-1)) > 0){
                speeds[0] += ((BaseAgent)agent).currVelocity.length();
                speeds[1] += 1;
            }
        }
        if(speeds[1] !=0) return speeds;
        else return null;
    }
}
