package org.socialforce.model.impl;

import org.socialforce.geom.Affection;
import org.socialforce.geom.Shape;
import org.socialforce.geom.impl.Ellipse2D;
import org.socialforce.geom.impl.Force2D;
import org.socialforce.geom.impl.Moment2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.model.Agent;
import org.socialforce.model.Blockable;
import org.socialforce.model.ForceRegulation;
import org.socialforce.model.InteractiveEntity;

/**
 * Created by Whatever on 2017/6/27.
 */
public class SpinPsyForceRegulation implements ForceRegulation {
    public SpinPsyForceRegulation(SimpleSocialForceModel model){
        p = new PsychologicalForceRegulation(Blockable.class, Agent.class,model);
    }
    private PsychologicalForceRegulation p;
    /**
     * 判断源实体和目标实体之间是否有作用力。
     *
     * @param source
     * @param target
     * @return true 当源实体和目标实体之间是有作用力时返回真。
     */
    @Override
    public boolean hasForce(InteractiveEntity source, InteractiveEntity target) {
        return p.hasForce(source,target) && ((Agent)target).getShape().distanceTo(source.getShape()) >=0  && target.getShape() instanceof Ellipse2D;
    }

    /**
     * 获取源实体和目标实体之间的作用力。
     *
     * @param interactiveEntity
     * @param interactiveEntity2
     * @return force
     */
    @Override
    public Affection getForce(InteractiveEntity interactiveEntity, InteractiveEntity interactiveEntity2) {
        Ellipse2D e2= (Ellipse2D) interactiveEntity2.getShape();
        Shape e1 = interactiveEntity.getShape();
        double[][] temp = e2.closePoint(e1);
        Point2D p2 = new Point2D(temp[1][0],temp[1][1]);
        Force2D f = new Force2D(0,0);
        f.add(p.getForce((Blockable) interactiveEntity,(Agent) interactiveEntity2));
        Moment2D moment2D = (Moment2D) f.CalculateMoment(p2,e2.getReferencePoint());
        System.out.println(moment2D.getM());
        return moment2D;
    }
}
