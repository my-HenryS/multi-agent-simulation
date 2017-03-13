package org.socialforce.container;

import org.socialforce.geom.DistanceShape;
import org.socialforce.geom.Shape;
import org.socialforce.model.Agent;

/**
 * A specific pool for agent.
 *
 * @author Ledenel
 */

/*
 * generate agent, the agents generate from one pool have the same properties
 */
public interface AgentPool extends Pool<Agent> {
    /**
     * 选择与指定形状相交的所有实体。
     * @param shape 指定形状。
     * @return 一个只读的集合，包含了与指定形状相交的所有实体。
     */
    Iterable<Agent> select(Shape shape);

}
