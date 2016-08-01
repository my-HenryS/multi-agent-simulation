package org.socialforce.entity;

/**
 * Created by Ledenel on 2016/7/31.
 */
public interface Scene {
    void stepNext();
    AgentPool getAllAgents();
    EntityPool getStaticEntities();
}
