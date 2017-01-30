package org.socialforce.scene;

import org.socialforce.container.AgentPool;
import org.socialforce.geom.Shape;
import org.socialforce.model.Agent;

/**
 * Created by Administrator on 2017/1/30.
 */
public interface Region {
    Iterable<Agent> getAllAgents();
    Shape getShape();
    String getName();
}
