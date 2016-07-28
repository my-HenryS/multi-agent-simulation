package org.socialforce.entity;

import org.socialforce.entity.Agent;

import java.util.Collection;

/**
 * Created by Ledenel on 2016/7/28.
 */
public interface AgentPool extends Collection<Agent>{
    Iterable<Agent> select(Shape shape);

}
