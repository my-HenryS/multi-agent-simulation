package org.socialforce.model.impl;

import org.socialforce.geom.DistanceShape;
import org.socialforce.geom.Point;
import org.socialforce.geom.Velocity;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.model.Agent;
import org.socialforce.model.AgentDecorator;
import org.socialforce.model.SocialForceModel;

import java.util.Random;


/**
 * Created by Whatever on 2016/11/15.
 */
public class BaseAgentDecorator implements AgentDecorator {
    private double AGENT_SIZE;
    @Override
    public Agent createAgent(Point position, Velocity velocity, DistanceShape template) {
        template = template.clone();
        template.moveTo(position);
        Agent agent = new BaseAgent(template, velocity);
        return agent;
    }
}
