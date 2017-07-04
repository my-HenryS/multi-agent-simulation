package org.socialforce.scene.impl;

import org.socialforce.geom.DistanceShape;
import org.socialforce.geom.Palstance;
import org.socialforce.geom.Velocity;
import org.socialforce.model.Agent;
import org.socialforce.model.AgentDecorator;
import org.socialforce.model.impl.RotateableAgentDecorator;
import org.socialforce.model.impl.SimpleSocialForceModel;
import org.socialforce.scene.Scene;
import org.socialforce.scene.SceneValue;

/**
 * Created by Administrator on 2017/7/4.
 */
public class SV_ExactAgentGenerator implements SceneValue<Agent>{
    protected String name;
    protected int pri;
    protected AgentDecorator agentDecorator;
    protected Agent agent;

    /**
     * 生成单个Agent，注意AgentShape和Template不同。
     * @param agentShape 包含agent的形状和位置信息，不仅仅是形状信息，在初始化时请注意。
     * @param velocity agent的初速度。
     * @param palstance agent的初角速度。
     * @see SV_RandomAgentGenerator 注意shape参数区别
     */
    public SV_ExactAgentGenerator(DistanceShape agentShape, Velocity velocity, Palstance palstance){
        pri = -1;
        agentDecorator = new RotateableAgentDecorator(palstance);
        agent = agentDecorator.createAgent(agentShape.getReferencePoint(),velocity,agentShape);
    }


    @Override
    public String getEntityName() {
        return name;
    }


    @Override
    public void setEntityName(String name) {
        this.name = name;
    }


    @Override
    public Agent getValue() {
        return agent;
    }


    @Override
    public void setValue(Agent value) {
        this.agent = value;
    }


    @Override
    public void apply(Scene scene) {
        scene.getAllAgents().add(agent);
        agent.setScene(scene);
        agent.setModel(new SimpleSocialForceModel());
    }


    @Override
    public int compareTo(SceneValue<Agent> o) {
        return 0;
    }

    @Override
    public int getPriority() {
        return pri;
    }

    @Override
    public void setPriority(int priority) {
        this.pri = priority;
    }
}
