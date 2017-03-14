package org.socialforce.scene.impl;
import org.socialforce.container.EntityPool;
import org.socialforce.geom.DistanceShape;
import org.socialforce.geom.Velocity;
import org.socialforce.model.*;
import org.socialforce.scene.Scene;
import org.socialforce.scene.SceneValue;
import org.socialforce.geom.Shape;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.geom.impl.Semicircle2D;
import org.socialforce.model.impl.BaseAgentDecorator;
import org.socialforce.model.impl.SimpleSocialForceModel;
/**
 * Created by Whatever on 2016/9/16.
 */
public class SV_AgentGenerator implements SceneValue<SV_AgentGenerator.AgentGenerator> {
    /**
     * 用于描述一个刷怪笼
     * 参数为XYZ方向的agent间距，生成人的范围还有在何种模型下生成人。
     */
    protected class AgentGenerator{
        protected double X_distance,Y_distance,Z_distance;
        protected Shape Area;
        protected AgentDecorator decorator;
        protected Model model;
        protected Velocity velocity;
        protected DistanceShape shape;
        public AgentGenerator(double X_distance,double Y_distance,double Z_distance,Shape Area){
            this.X_distance = X_distance;
            this.Y_distance = Y_distance;
            this.Z_distance = Z_distance;
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
        public void setModel(Model model){this.model = model;}
    }
    private int priority;
    protected AgentGenerator agentGenerator;
    public SV_AgentGenerator(double X_distance, double Y_distance, double Z_distance, Shape Area, DistanceShape template, Velocity velocity){
        agentGenerator = new AgentGenerator(X_distance,Y_distance,Z_distance,Area);
        agentGenerator.setDecorator(new BaseAgentDecorator());
        agentGenerator.setModel(new SimpleSocialForceModel());
        agentGenerator.velocity = velocity;
        agentGenerator.shape = template;
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
        if (agentGenerator.Area instanceof Box2D || agentGenerator.Area instanceof Semicircle2D || agentGenerator.Area instanceof Circle2D) {
            for (int i = 0; i < (agentGenerator.Area.getBounds().getEndPoint().getX() - agentGenerator.Area.getBounds().getStartPoint().getX()) / agentGenerator.X_distance; i++) {
                for (int j = 0; j < (agentGenerator.Area.getBounds().getEndPoint().getY() - agentGenerator.Area.getBounds().getStartPoint().getY()) / agentGenerator.Y_distance; j++) {
                    int is_able_flag = 0;
                    Point2D point = new Point2D(agentGenerator.Area.getBounds().getStartPoint().getX()+i*agentGenerator.X_distance,agentGenerator.Area.getBounds().getStartPoint().getY()+j*agentGenerator.Y_distance);
                    if(agentGenerator.Area.contains(point)){
                        new_agent = agentGenerator.decorator.createAgent(point, agentGenerator.velocity.clone(), agentGenerator.shape);
                        EntityPool all_blocks = scene.getStaticEntities();
                        for (InteractiveEntity entity : all_blocks) {
                            if ((entity instanceof Blockable) && new_agent.getShape().distanceTo(entity.getShape()) < 0){
                                is_able_flag = 1;
                                break;
                            }
                        }
                        if(is_able_flag == 1) continue;
                        new_agent.setModel(agentGenerator.model.clone());
                        scene.addAgent(new_agent);
                        new_agent.setScene(scene);
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