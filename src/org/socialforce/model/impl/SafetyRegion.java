package org.socialforce.model.impl;

import org.socialforce.geom.PhysicalEntity;
import org.socialforce.model.*;
import org.socialforce.scene.Scene;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 定义Gate类，其继承于父类Entity， 并实现了接口InteractiveEntity 的方法
 *  * Created by Ledenel on 2016/8/14.
 */
public class SafetyRegion extends Entity implements Influential{
    LinkedBlockingQueue<Integer> agentEscaping = new LinkedBlockingQueue<>();
    int agentEscapeThisStep = 0;
    public SafetyRegion(PhysicalEntity physicalEntity) {
        super(physicalEntity);
    }

    /**
     * 当前this所影响的实体
     * 例如，墙会影响agent(反9作用，反推)
     * @param target 被影响的实体
     * @see Agent
     * @see Model
     */
    public void affect(Agent target) {
            Agent agent = (Agent) target;
            //agent.push(model.interactionForce(this,agent));
            if(physicalEntity.contains(agent.getPhysicalEntity().getReferencePoint())) {
                //agent exited.
                if(scene != null) {
                    scene.onAgentEscape(agent); // add valid scene.
                    agentEscapeThisStep ++;
                }
            }

    }

    /*
     * get the listener of the application
     * @return

    public ApplicationListener getListener() {
        return listener;
    }

    /*
     * set the listener of the application
     * @param listener

    public void setListener(ApplicationListener listener) {
        this.listener = listener;
    }

    ApplicationListener listener;
*/
    /**
     * 获取实体的质量。
     * 通常质量位于形状的参考点上（或者是位于质心上）
     * TODO POSITIVE_INFINITY(返回正无穷？)
     * @return mass.
     */
    @Override
    public double getMass() {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public SafetyRegion clone() {
        return new SafetyRegion(physicalEntity.clone());
    }

    @Override
    public PhysicalEntity getView() {
        return this.getPhysicalEntity();
    }

    public int getLastSecondEscapedAgents(){
        return agentEscaping.stream().mapToInt(num -> num).sum();
    }

    @Override
    public boolean onAdded(Scene scene) {
        return true;
    }

    @Override
    public void onStep(Scene scene) {
        agentEscaping.offer(agentEscapeThisStep);
        if(agentEscaping.size() > 1/model.getTimePerStep()){
            agentEscaping.poll();
        }
        agentEscapeThisStep = 0;
    }
}
