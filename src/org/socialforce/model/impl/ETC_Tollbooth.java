package org.socialforce.model.impl;

import org.socialforce.geom.Shape;
import org.socialforce.model.InteractiveEntity;

/**
 * Created by sunjh1999 on 2017/1/21.
 */
public class ETC_Tollbooth extends SimpleTollbooth {
    public ETC_Tollbooth(Shape shape, double interval) {
        super(shape, interval);
    }

    @Override
    public void affect(InteractiveEntity affectedEntity) {
        if(affectedEntity instanceof BaseAgent ){
            BaseAgent agent = (BaseAgent) affectedEntity;
            if(!agentDictionary.containsKey(agent)){
                agentDictionary.put(agent, (int)(agent.currTimestamp + interval / agent.getModel().getTimePerStep()) );
                agent.getModel().setExpectedSpeed(13);
                return;
            }
            if(agentDictionary.get(agent) > agent.currTimestamp){
                agent.getModel().setExpectedSpeed(13);
            }
            else if(agentDictionary.get(agent) == agent.currTimestamp){
                agent.getModel().setExpectedSpeed(this.model.getExpectedSpeed());
            }
        }
    }

}
