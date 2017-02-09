package org.socialforce.model.impl;

import org.socialforce.geom.DistanceModelShape;
import org.socialforce.geom.Point;
import org.socialforce.geom.Velocity;
import org.socialforce.model.Agent;
import org.socialforce.model.AgentDecorator;


/**
 * Created by Whatever on 2016/11/15.
 */
public class BaseAgentDecorator implements AgentDecorator {
    @Override
    public Agent createAgent(Point position, Velocity velocity, DistanceModelShape template) {
        template = template.clone();
        template.moveTo(position);
        Agent agent = new BaseAgent(template, velocity);
        return agent;
    }
}
