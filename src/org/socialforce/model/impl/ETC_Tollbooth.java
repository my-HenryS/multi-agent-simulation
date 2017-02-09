package org.socialforce.model.impl;

import org.socialforce.geom.ModelShape;
import org.socialforce.model.InteractiveEntity;

/**
 * Created by sunjh1999 on 2017/1/21.
 */
public class ETC_Tollbooth extends SimpleTollbooth {
    static double maxVelocity = 13;
    public ETC_Tollbooth(ModelShape modelShape, double interval) {
        super(modelShape, interval);
    }

    @Override
    public void affect(InteractiveEntity affectedEntity) {
        if(affectedEntity instanceof BaseAgent ){
            BaseAgent agent = (BaseAgent) affectedEntity;
            if(!agentDictionary.containsKey(agent)){
                agentDictionary.put(agent, (int)(agent.currTimestamp + interval / agent.getModel().getTimePerStep()) );
                agent.getModel().setExpectedSpeed(maxVelocity);
                return;
            }
            if(agentDictionary.get(agent) > agent.currTimestamp){
                agent.getModel().setExpectedSpeed(maxVelocity);
            }
            else if(agentDictionary.get(agent) == agent.currTimestamp){
                agent.getModel().setExpectedSpeed(this.model.getExpectedSpeed());
            }
        }
    }

}
