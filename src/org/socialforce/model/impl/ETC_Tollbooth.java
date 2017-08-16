package org.socialforce.model.impl;

import org.socialforce.geom.PhysicalEntity;
import org.socialforce.model.Agent;
import org.socialforce.model.Influential;
import org.socialforce.scene.Scene;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunjh1999 on 2017/3/26.
 */
public class ETC_Tollbooth extends Entity implements Influential {
    Map<Agent, Integer> agentDictionary = new HashMap<>();
    double interval;
    public ETC_Tollbooth(PhysicalEntity physicalEntity, double interval) {
        super(physicalEntity);
        this.interval = interval;
    }

    @Override
    public double getMass() {
        return 0;
    }

    @Override
    public ETC_Tollbooth clone() {
        return new ETC_Tollbooth(physicalEntity.clone(), interval);
    }

    @Override
    public PhysicalEntity getView() {
        return this.physicalEntity;
    }

    @Override
    public void affect(Agent target) {
        BaseAgent agent = (BaseAgent) target;
        if(!agentDictionary.containsKey(agent)){
            agentDictionary.put(agent, (int)(scene.getCurrentSteps() + interval / agent.getModel().getTimePerStep()) );
            ((SimpleForceModel)agent.getModel()).setExpectedSpeed(13);
            return;
        }
        if(agentDictionary.get(agent) > scene.getCurrentSteps()){
            ((SimpleForceModel)agent.getModel()).setExpectedSpeed(13);
        }
        else if(agentDictionary.get(agent) == scene.getCurrentSteps()){
            ((SimpleForceModel)agent.getModel()).setExpectedSpeed(((SimpleForceModel)this.model).getExpectedSpeed());
        }
    }

    @Override
    public boolean onAdded(Scene scene) {
        return true;
    }

    @Override
    public void onStep(Scene scene) {

    }
}
