package org.socialforce.model.impl;

import org.socialforce.geom.Force;
import org.socialforce.geom.Point;
import org.socialforce.geom.Vector;
import org.socialforce.geom.impl.Force2D;
import org.socialforce.model.ForceRegulation;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.model.Model;

/**
 * Created by Whatever on 2017/3/2.
 */
public class GravityRegulation extends TypeMatchRegulation<Star_Planet,Star_Planet>{

    public GravityRegulation(Class<Star_Planet> star_planetClass, Class<Star_Planet> star_planetClass2, Model model) {
        super(star_planetClass, star_planetClass2, model);
    }

    protected double G_constant = 0.1;
    /**
     * 获取源实体和目标实体之间的作用力。
     *
     * @param interactiveEntity
     * @param interactiveEntity2
     * @return force
     */
    @Override
    public Force getForce(Star_Planet interactiveEntity, Star_Planet interactiveEntity2) {
        Point point1 = interactiveEntity.getShape().getReferencePoint();
        Point point2 = interactiveEntity2.getShape().getReferencePoint();
        double distance = point1.distanceTo(point2);
        Vector direction = point1.directionTo(point2);
        double f = G_constant*interactiveEntity.getMass()*interactiveEntity2.getMass()/(distance*distance);
        direction.scale(f);
        double[] values = new double[2];
        direction.get(values);
        return new Force2D(-values[0],-values[1]);
    }
}
