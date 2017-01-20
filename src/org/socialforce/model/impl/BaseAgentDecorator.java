package org.socialforce.model.impl;

import org.socialforce.geom.Point;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.model.Agent;
import org.socialforce.model.AgentDecorator;
import org.socialforce.model.SocialForceModel;


/**
 * Created by Whatever on 2016/11/15.
 */
public class BaseAgentDecorator implements AgentDecorator {
    public static final double AGENT_SIZE = 1.8/2;
    @Override
    public Agent createAgent(Point position) {
        Circle2D size = new Circle2D(position,AGENT_SIZE);
        Agent agent = new BaseAgent(size);
        return agent;
    }

}
