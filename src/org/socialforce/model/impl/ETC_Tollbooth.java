package org.socialforce.model.impl;

import org.socialforce.geom.Shape;
import org.socialforce.model.Agent;
import org.socialforce.model.Influential;
import org.socialforce.model.InteractiveEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunjh1999 on 2017/3/26.
 */
public class ETC_Tollbooth extends Entity implements Influential {
    Map<Agent, Integer> agentDictionary = new HashMap<>();
    double interval;
    public ETC_Tollbooth(Shape shape, double interval) {
        super(shape);
        this.interval = interval;
    }

    @Override
    public double getMass() {
        return 0;
    }

    @Override
    public InteractiveEntity standardclone() {
        return new ETC_Tollbooth(shape, interval);
    }

    @Override
    public Shape getView() {
        return this.shape;
    }

    @Override
    public void affect(Agent target) {
        BaseAgent agent = (BaseAgent) target;
        if(!agentDictionary.containsKey(agent)){
            agentDictionary.put(agent, (int)(scene.getCurrentSteps() + interval / agent.getModel().getTimePerStep()) );
            ((SimpleSocialForceModel)agent.getModel()).setExpectedSpeed(13);
            return;
        }
        if(agentDictionary.get(agent) > scene.getCurrentSteps()){
            ((SimpleSocialForceModel)agent.getModel()).setExpectedSpeed(13);
        }
        else if(agentDictionary.get(agent) == scene.getCurrentSteps()){
            ((SimpleSocialForceModel)agent.getModel()).setExpectedSpeed(((SimpleSocialForceModel)this.model).getExpectedSpeed());
        }
    }
}
