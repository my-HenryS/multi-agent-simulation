package org.socialforce.model.impl;

import org.socialforce.geom.Shape;
import org.socialforce.model.*;

/**
 * 定义Gate类，其继承于父类Entity， 并实现了接口InteractiveEntity 的方法
 *  * Created by Ledenel on 2016/8/14.
 */
public class SafetyRegion extends Entity implements Influential {
    public SafetyRegion(Shape shape) {
        super(shape);
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
            if(shape.contains(agent.getShape().getReferencePoint())) {
                //agent exited.
                if(scene != null) {
                    scene.onAgentEscape(agent); // add valid scene.
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
    public SafetyRegion standardclone() {
        return new SafetyRegion(shape.clone());
    }

    @Override
    public Shape getView() {
        return this.getShape();
    }

}
