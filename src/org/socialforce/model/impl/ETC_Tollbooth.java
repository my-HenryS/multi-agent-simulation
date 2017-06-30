package org.socialforce.model.impl;

import org.socialforce.geom.Shape;
import org.socialforce.geom.impl.Box2D;
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
    double interval, maxSpeed;
    boolean stopAgent;
    public ETC_Tollbooth(Shape shape, double interval, double maxAllowedSpeed) {
        super(shape);
        this.interval = interval;
        this.maxSpeed = maxAllowedSpeed;
        stopAgent = maxSpeed <= 3;
    }

    @Override
    public double getMass() {
        return 0;
    }

    @Override
    public InteractiveEntity standardclone() {
        return new ETC_Tollbooth(shape.clone(), interval, maxSpeed);
    }

    @Override
    public Shape getView() {
        Box2D shape = (Box2D)(this.shape);
        return new Box2D(shape.getXmin(),shape.getYmin() - 20,shape.getWidth(),shape.getHeight() + 40);
    }

    @Override
    public void affect(Agent target) {
        BaseAgent agent = (BaseAgent) target;
        if(!agentDictionary.containsKey(agent)){
            agentDictionary.put(agent, (int)(scene.getCurrentSteps() + interval / agent.getModel().getTimePerStep()) );
            ((SimpleSocialForceModel)agent.getModel()).setExpectedSpeed(maxSpeed);
            return;
        }
        if(agentDictionary.get(agent) > scene.getCurrentSteps()){
            ((SimpleSocialForceModel)agent.getModel()).setExpectedSpeed(maxSpeed);
            if(stopAgent && agent.getShape().intersects(shape)) agent.stopWhatEver();

        }
        else if(agentDictionary.get(agent) <= scene.getCurrentSteps()){
            ((SimpleSocialForceModel)agent.getModel()).setExpectedSpeed(((SimpleSocialForceModel)this.model).getExpectedSpeed());
        }
    }
}
