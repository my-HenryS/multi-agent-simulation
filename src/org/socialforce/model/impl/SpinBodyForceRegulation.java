package org.socialforce.model.impl;

import org.socialforce.geom.Affection;
import org.socialforce.geom.Force;
import org.socialforce.geom.PhysicalEntity;
import org.socialforce.geom.RotatablePhysicalEntity;
import org.socialforce.geom.impl.*;
import org.socialforce.model.*;

/**
 * 同时包含力和力矩作用的BodyForce
 * 关于"谁受到力矩作用"定义在此
 * Created by Administrator on 2017/6/26 0026.
 */
public class SpinBodyForceRegulation extends BodyForce{
    public SpinBodyForceRegulation(Class<Blockable> blockableClass, Class<Agent> agentClass, Model model) {
        super(blockableClass, agentClass, model);
    }

    @Override
    public boolean hasForce(InteractiveEntity source, InteractiveEntity target) {
        return super.hasForce(source,target);
    }

    @Override
    public Affection getForce(Blockable source, Agent target) {
        Force2D force2D = (Force2D) super.getForce(source,target);
        if(target instanceof RotatablePhysicalEntity) {
            RotatablePhysicalEntity e2 = (RotatablePhysicalEntity) target.getPhysicalEntity();
            PhysicalEntity e1 = source.getPhysicalEntity();
            //double[][] temp = e2.closePoint(e1);
            //Point2D p2 = new Point2D(temp[1][0],temp[1][1]);
            Point2D p2 = (Point2D) e2.forcePoint(e1);
            Moment2D moment2D = (Moment2D) force2D.CalculateMoment(p2, e2.getReferencePoint());
            return new Affection2D(moment2D, force2D);
        }
        else{
            return force2D;
        }
    }

}
