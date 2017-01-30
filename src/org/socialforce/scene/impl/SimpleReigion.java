package org.socialforce.scene.impl;

import org.socialforce.container.AgentPool;
import org.socialforce.geom.Box;
import org.socialforce.geom.Shape;
import org.socialforce.geom.impl.Sample2D;
import org.socialforce.model.Agent;
import org.socialforce.scene.Region;
import org.socialforce.scene.Scene;

/**
 * Created by Administrator on 2017/1/30.
 */
public class SimpleReigion implements Region {
    protected Sample2D bounds;
    protected Iterable<Agent> agents;


    @Override
    public Iterable<Agent> getAllAgents() {
        return agents;
    }

    @Override
    public Shape getShape() {
        return bounds;
    }

    protected String name;
    @Override
    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setBounds(Sample2D bounds){
        this.bounds = bounds;
    }

    public void readScene(Scene scene){
        agents = scene.getAllAgents().select(bounds);
    }
}
