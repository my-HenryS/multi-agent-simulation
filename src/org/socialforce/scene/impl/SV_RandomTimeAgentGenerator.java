package org.socialforce.scene.impl;

import org.socialforce.container.EntityPool;
import org.socialforce.geom.DistanceShape;
import org.socialforce.geom.Shape;
import org.socialforce.geom.Velocity;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.geom.impl.Semicircle2D;
import org.socialforce.model.Agent;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.model.impl.SafetyRegion;
import org.socialforce.scene.Scene;
import org.socialforce.scene.SceneValue;

import java.util.Random;

/**
 * Created by sunjh1999 on 2017/1/21.
 */
public class SV_RandomTimeAgentGenerator extends SV_RandomAgentGenerator implements SceneValue<SV_RandomAgentGenerator.AgentGenerator> {
    public SV_RandomTimeAgentGenerator(int agent_num, Shape Area, DistanceShape template, Velocity velocity) {
        super(agent_num, Area, template, velocity);
        agentGenerator.setNum(agent_num);
    }

    @Override
    public void apply(Scene scene) {
        Agent new_agent;
        Random rand = new Random();
        int cur_num = 0;
        int gen_num = (int)P_rand(agentGenerator.agent_num);
        int is_able_flag = 0;
        int times = 0;
        if (agentGenerator.Area instanceof Box2D || agentGenerator.Area instanceof Semicircle2D || agentGenerator.Area instanceof Circle2D){
            while (cur_num < gen_num) {
                times += 1;
                double rand_x = agentGenerator.Area.getBounds().getStartPoint().getX() + rand.nextDouble() * (agentGenerator.Area.getBounds().getEndPoint().getX() - agentGenerator.Area.getBounds().getStartPoint().getX());
                double rand_y = agentGenerator.Area.getBounds().getStartPoint().getY() + rand.nextDouble() * (agentGenerator.Area.getBounds().getEndPoint().getY() - agentGenerator.Area.getBounds().getStartPoint().getY());
                Point2D point = new Point2D(rand_x, rand_y);
                Iterable<Agent> agents = scene.getAllAgents();
                new_agent = agentGenerator.decorator.createAgent(point, agentGenerator.velocity.clone(), agentGenerator.shape);
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
                    new_agent.setModel(agentGenerator.model.clone());
                    scene.addAgent(new_agent);
                    new_agent.setScene(scene);
                    cur_num += 1;
                }}
        }

        else throw new IllegalArgumentException("暂未实现非二维的生成区块");
    }

    public double P_rand(double Lamda){ // 泊松分布
        double x=0,b=1,c=Math.exp(-Lamda),u;
        do {
            u=Math.random();
            b *=u;
            if(b>=c)
                x++;
        }while(b>=c);
        return x;
    }
}
