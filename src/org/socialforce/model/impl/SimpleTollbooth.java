package org.socialforce.model.impl;

import org.socialforce.geom.Shape;
import org.socialforce.geom.impl.Vector2D;
import org.socialforce.model.Agent;
import org.socialforce.model.InteractiveEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunjh1999 on 2017/1/20.
 */
public class SimpleTollbooth extends Entity {
    Map<Agent, Integer> agentDictionary = new HashMap<>();
    double interval;
    public SimpleTollbooth(Shape shape, double interval) {
        super(shape);
        this.interval = interval;
    }

    @Override
    public void affect(InteractiveEntity affectedEntity) {
        if(affectedEntity instanceof BaseAgent && isValid((BaseAgent) affectedEntity)){
            ((BaseAgent)affectedEntity).stop();
        }
    }

    @Override
    public double getMass() {
        return 0;
    }

    @Override
    public InteractiveEntity standardclone() {
        return new SimpleTollbooth(shape.clone(), interval);
    }

    public boolean isValid(BaseAgent agent){
        if(!agentDictionary.containsKey(agent)){
            if(agent.getShape().distanceTo(this.shape) < 0){
                agentDictionary.put(agent, (int)(agent.currTimestamp + interval / agent.getModel().getTimePerStep()) );
                return true;
            }
            else{
                return false;
            }
        }
        if(agentDictionary.get(agent) > agent.currTimestamp){
            return true;
        }
        else return false;
    }

    public double getInterval(){
        return interval;
    }

}
