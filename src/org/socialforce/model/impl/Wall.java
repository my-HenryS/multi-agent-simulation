package org.socialforce.model.impl;

import org.socialforce.geom.Moment;
import org.socialforce.geom.Shape;
import org.socialforce.model.*;

/**
 * Created by Ledenel on 2016/8/14.
 */
public class Wall extends Entity implements Blockable, Influential {
    @Override
    public Shape getView() {
        return (this.getShape().clone()).expandBy(3);
    }

    /**
     * 当前this所影响的实体
     * 例如，墙会影响agent(反作用，反推)
     * @param target 被影响的实体
     * @see Agent
     * @see Model
     */

    public void affect(Agent target) {
        target.push(model.interactionForce(this,target));
        Moment temp = model.interactionMoment(this,target);
        target.rotate(model.interactionMoment(this,target));
    }

    public Wall(Shape shape) {
        super(shape);
    }

    /**
     * 获取实体的质量。
     * 通常质量位于形状的参考点上（或者是位于质心上）
     * @return the mass.
     */
    @Override
    public double getMass() {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public Wall standardclone() {
        return new Wall(shape.clone());
    }

    @Override
    public Shape blockSize() {
        return this.getShape().clone();
    }
}
