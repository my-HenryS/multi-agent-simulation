package org.socialforce.scene.impl;

import org.socialforce.scene.Scene;
import org.socialforce.scene.SceneValue;
import org.socialforce.container.EntityPool;
import org.socialforce.geom.Shape;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.geom.impl.Semicircle2D;
import org.socialforce.model.Agent;
import org.socialforce.model.AgentDecorator;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.model.SocialForceModel;
import org.socialforce.model.impl.BaseAgentDecorator;
import org.socialforce.model.impl.SafetyRegion;
import org.socialforce.model.impl.SimpleSocialForceModel;

import java.util.Random;

/**
 * Created by sunjh1999 on 2016/12/10.
 */
public class SVSR_RandomAgentGenerator implements SceneValue<SVSR_RandomAgentGenerator.AgentGenerator> {

    /**
     * 用于描述一个刷怪笼
     * 参数为XYZ方向的agent间距，生成人的范围还有在何种模型下生成人。
     */
    protected class AgentGenerator{
        protected double agent_num;
        protected Shape Area;
        protected AgentDecorator decorator;
        protected SocialForceModel model;

        public AgentGenerator(double agent_num,Shape Area){
            this.agent_num = agent_num;
            this.Area = Area;
        }

        public double getAgent_num(){
            return agent_num;
        }
        public Shape getArea(){
            return Area;
        }
        public void setDecorator(AgentDecorator decorator){
            this.decorator = decorator;
        }
        public void setModel(SocialForceModel model){this.model = model;}
    }


    private int priority;
    protected AgentGenerator agentGenerator;

    public SVSR_RandomAgentGenerator(double agent_num,Shape Area){
        agentGenerator = new AgentGenerator(agent_num,Area);
        agentGenerator.setDecorator(new BaseAgentDecorator());
        agentGenerator.setModel(new SimpleSocialForceModel());
    }
    @Override
    public String getEntityName() {
        return null;
    }

    @Override
    public void setEntityName(String name) {

    }

    @Override
    public AgentGenerator getValue() {
        return null;
    }

    @Override
    public void setValue(AgentGenerator value) {

    }

    /**
     * 注意生成agent使用的是SocialForceModel里的createAgent方法
     * @param scene 要被更改的场景。
     */
    @Override
    public void apply(Scene scene) {
        Agent new_agent;
        Random rand = new Random();
        int cur_num = 0;
        int is_able_flag = 0;
        if (agentGenerator.Area instanceof Box2D || agentGenerator.Area instanceof Semicircle2D || agentGenerator.Area instanceof Circle2D){
            while (cur_num < agentGenerator.agent_num) {
                double rand_x = agentGenerator.Area.getBounds().getStartPoint().getX() + rand.nextDouble() * (agentGenerator.Area.getBounds().getEndPoint().getX() - agentGenerator.Area.getBounds().getStartPoint().getX());
                double rand_y = agentGenerator.Area.getBounds().getStartPoint().getY() + rand.nextDouble() * (agentGenerator.Area.getBounds().getEndPoint().getY() - agentGenerator.Area.getBounds().getStartPoint().getY());
                Point2D point = new Point2D(rand_x, rand_y);
                Iterable<Agent> agents = scene.getAllAgents();
                new_agent = agentGenerator.decorator.createAgent(point);
                for (Agent agent : agents) {
                    if(new_agent.getShape().distanceTo(agent.getShape()) < 0){
                        is_able_flag = 1;
                        break;
                    }
                }
                if(is_able_flag == 0){
                    EntityPool all_blocks = scene.getStaticEntities();
                    for (InteractiveEntity entity : all_blocks) {
                        if (!(entity instanceof SafetyRegion) && new_agent.getShape().distanceTo(entity.getShape()) < 0){
                            is_able_flag = 1;
                            break;
                        }
                    }
                }

                if(is_able_flag == 1){
                    is_able_flag = 0;
                    continue;
                }
                if (agentGenerator.Area.contains(point)) {
                    new_agent.setModel(agentGenerator.model);
                    scene.addAgent(new_agent);
                    new_agent.setScene(scene);
                    cur_num += 1;
                }
            }
        }

        else throw new IllegalArgumentException("暂未实现非二维的生成区块");
    }

    @Override
    public int compareTo(SceneValue<AgentGenerator> o) {
        return o.getPriority() - this.getPriority();
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public void setPriority(int priority) {
        this.priority = priority;
    }
}

