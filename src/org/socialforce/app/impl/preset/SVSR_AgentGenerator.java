package org.socialforce.app.impl.preset;

import org.socialforce.app.Scene;
import org.socialforce.app.SceneValue;
import org.socialforce.geom.Box;
import org.socialforce.geom.Shape;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.geom.impl.Semicircle2D;
import org.socialforce.model.Agent;
import org.socialforce.model.AgentDecorator;
import org.socialforce.model.SocialForceModel;
import org.socialforce.model.impl.BaseAgentDecorator;
import org.socialforce.model.impl.SimpleSocialForceModel;

import java.util.Random;

/**
 * Created by Whatever on 2016/9/16.
 */
public class SVSR_AgentGenerator implements SceneValue<SVSR_AgentGenerator.AgentGenerator> {

    /**
     * 用于描述一个刷怪笼
     * 参数为XYZ方向的agent间距，生成人的范围还有在何种模型下生成人。
     */
    protected class AgentGenerator{
        protected double X_distance,Y_distance,Z_distance;
        protected double agent_num;
        protected Shape Area;
        protected AgentDecorator decorator;
        protected SocialForceModel model;

        public AgentGenerator(double X_distance,double Y_distance,double Z_distance,Shape Area){
            this.X_distance = X_distance;
            this.Y_distance = Y_distance;
            this.Z_distance = Z_distance;
            this.Area = Area;
        }

        public AgentGenerator(double agent_num,Shape Area){
            this.agent_num = agent_num;
            this.Area = Area;
        }

        public double getX_distance(){
            return X_distance;
        }
        public double getY_distance(){
            return Y_distance;
        }
        public double getZ_distance(){
            return Z_distance;
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

    public SVSR_AgentGenerator(double X_distance,double Y_distance,double Z_distance,Shape Area){
        agentGenerator = new AgentGenerator(X_distance,Y_distance,Z_distance,Area);
        agentGenerator.setDecorator(new BaseAgentDecorator());
        agentGenerator.setModel(new SimpleSocialForceModel());
    }

    public SVSR_AgentGenerator(double agent_num,Shape Area){
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
        if (agentGenerator.Area instanceof Box2D || agentGenerator.Area instanceof Semicircle2D || agentGenerator.Area instanceof Circle2D){
            if(agentGenerator.agent_num == 0){
                for (int i = 0; i < (agentGenerator.Area.getBounds().getEndPoint().getX() - agentGenerator.Area.getBounds().getStartPoint().getX()) / agentGenerator.X_distance; i++) {
                    for (int j = 0; j < (agentGenerator.Area.getBounds().getEndPoint().getY() - agentGenerator.Area.getBounds().getStartPoint().getY()) / agentGenerator.Y_distance; j++) {
                        Point2D point = new Point2D(agentGenerator.Area.getBounds().getStartPoint().getX()+i*agentGenerator.X_distance,agentGenerator.Area.getBounds().getStartPoint().getY()+j*agentGenerator.Y_distance);
                        if(agentGenerator.Area.contains(point)){
                            new_agent = agentGenerator.decorator.createAgent(point);
                            new_agent.setModel(agentGenerator.model);
                            scene.addAgent(new_agent);
                            new_agent.setScene(scene);
                        }

                    }
                }
            }
           else{
                while (cur_num < agentGenerator.agent_num) {
                    int is_able_flag = 0;
                    double rand_x = agentGenerator.Area.getBounds().getStartPoint().getX() + rand.nextDouble() * (agentGenerator.Area.getBounds().getEndPoint().getX() - agentGenerator.Area.getBounds().getStartPoint().getX());
                    double rand_y = agentGenerator.Area.getBounds().getStartPoint().getY() + rand.nextDouble() * (agentGenerator.Area.getBounds().getEndPoint().getY() - agentGenerator.Area.getBounds().getStartPoint().getY());
                    Point2D point = new Point2D(rand_x, rand_y);
                    Iterable<Agent> agents = scene.getAllAgents();
                    for (Agent agent : agents) {
                        if(point.distanceTo(agent.getShape().getReferencePoint()) < 0.486) is_able_flag = 1;
                    }
                    if (agentGenerator.Area.contains(point) && is_able_flag == 0) {
                        new_agent = agentGenerator.decorator.createAgent(point);
                        new_agent.setModel(agentGenerator.model);
                        scene.addAgent(new_agent);
                        new_agent.setScene(scene);
                        cur_num += 1;
                    }
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
