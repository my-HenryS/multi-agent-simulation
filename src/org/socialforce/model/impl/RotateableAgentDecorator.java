package org.socialforce.model.impl;

import org.socialforce.geom.DistanceShape;
import org.socialforce.geom.Palstance;
import org.socialforce.geom.Point;
import org.socialforce.geom.Velocity;
import org.socialforce.model.Agent;
import org.socialforce.model.AgentDecorator;

/**
 * Created by Administrator on 2017/7/4.
 */
public class RotateableAgentDecorator implements AgentDecorator{
    public RotateableAgentDecorator(Palstance palstance){
        this.palstance = palstance;
    }

    protected Palstance palstance;


    @Override
    public Agent createAgent(Point position, Velocity velocity, DistanceShape template) {
        template = template.clone();
        template.moveTo(position);
        BaseAgent agent = new BaseAgent(template, velocity);
        agent.setCurrPal(palstance);
        return agent;
    }

    public void setPalstance(Palstance palstance){
        this.palstance = palstance;
    }
}
