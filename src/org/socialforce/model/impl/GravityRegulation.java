package org.socialforce.model.impl;

import org.socialforce.geom.Force;
import org.socialforce.geom.Point;
import org.socialforce.geom.Vector;
import org.socialforce.geom.impl.Force2D;
import org.socialforce.model.ForceRegulation;
import org.socialforce.model.InteractiveEntity;

/**
 * Created by Whatever on 2017/3/2.
 */
public class GravityRegulation implements ForceRegulation {
    /**
     * 判断源实体和目标实体之间是否有作用力。
     *
     * @param source
     * @param target
     * @return true 当源实体和目标实体之间是有作用力时返回真。
     */
    @Override
    public boolean hasForce(InteractiveEntity source, InteractiveEntity target) {
        if(source instanceof Star_Planet && target instanceof Star_Planet){
            return true;
        }
        return false;
    }

    protected double G_constant = 0.001;
    /**
     * 获取源实体和目标实体之间的作用力。
     *
     * @param interactiveEntity
     * @param interactiveEntity2
     * @return force
     */
    @Override
    public Force getForce(InteractiveEntity interactiveEntity, InteractiveEntity interactiveEntity2) {
        Point point1 = interactiveEntity.getShape().getReferencePoint();
        Point point2 = interactiveEntity2.getShape().getReferencePoint();
        double distance = point1.distanceTo(point2);
        Vector direction = point1.directionTo(point2);
        double f = G_constant*interactiveEntity.getMass()*interactiveEntity2.getMass()/(distance*distance);
        direction.scale(f);
        double[] values = new double[2];
        direction.get(values);
        System.out.println(values[0]+","+values[1]);
        return new Force2D(-values[0],-values[1]);
    }
}
