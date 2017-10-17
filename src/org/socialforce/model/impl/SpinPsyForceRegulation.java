package org.socialforce.model.impl;

import org.socialforce.geom.Affection;
import org.socialforce.geom.PhysicalEntity;
import org.socialforce.geom.RotatablePhysicalEntity;
import org.socialforce.geom.impl.*;
import org.socialforce.model.*;

/**
 * Created by Whatever on 2017/6/27.
 */
public class SpinPsyForceRegulation extends PsychologicalForceRegulation{

    public SpinPsyForceRegulation(Class<Blockable> agentClass, Class<Agent> agentClass2, Model model) {
        super(agentClass, agentClass2, model);
    }
    /**
     * 判断源实体和目标实体之间是否有作用力。
     *
     * @param source
     * @param target
     * @return true 当源实体和目标实体之间是有作用力时返回真。
     */
    @Override
    public boolean hasForce(InteractiveEntity source, InteractiveEntity target) {
        return super.hasForce(source,target);
    }

    /**
     * 获取源实体和目标实体之间的作用力。
     *
     * @param source
     * @param target
     * @return force
     */
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
